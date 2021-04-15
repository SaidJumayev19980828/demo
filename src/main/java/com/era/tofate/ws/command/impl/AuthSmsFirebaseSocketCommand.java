package com.era.tofate.ws.command.impl;

import com.era.tofate.entities.user.User;
import com.era.tofate.entities.userrole.UserRole;
import com.era.tofate.enums.AuthType;
import com.era.tofate.enums.Role;
import com.era.tofate.payload.firebase.UsersFirebaseRequest;
import com.era.tofate.payload.firebase.UsersFirebaseResponse;
import com.era.tofate.payload.socket.GeneralSocketRequest;
import com.era.tofate.payload.socket.GeneralSocketResponse;
import com.era.tofate.payload.socket.authsmsfirebase.AuthSmsFirebaseSocketRequest;
import com.era.tofate.payload.socket.authsmsfirebase.AuthSmsFirebaseSocketResponse;
import com.era.tofate.retrofit.RetrofitFactory;
import com.era.tofate.retrofit.firebase.FirebaseRetrofitService;
import com.era.tofate.service.user.UserService;
import com.era.tofate.service.userrole.UserRoleService;
import com.era.tofate.ws.command.GeneralSocketCommand;
import com.era.tofate.ws.enums.ActionType;
import com.era.tofate.ws.exception.AuthSocketException;
import com.era.tofate.ws.sessionpool.SocketSessionPool;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Date;

import static com.era.tofate.retrofit.RetrofitType.FIREBASE;

@Component
@Qualifier(value = "authsmsfirebase")
public class AuthSmsFirebaseSocketCommand implements GeneralSocketCommand {

    @Value("${firebase.web.api.key}")
    private String apiKey;

    @Value("${app.auth.tokenSecret}")
    private String tokenSecret;

    @Value("${app.auth.tokenExpirationMsec}")
    private String tokenExpirationMsec;

    private final ObjectMapper mapper = new ObjectMapper();

    private final SocketSessionPool socketSessionPool;

    private final RetrofitFactory<FirebaseRetrofitService> retrofitFactory;

    private final UserService userService;

    private final UserRoleService userRoleService;

    @Autowired
    public AuthSmsFirebaseSocketCommand(SocketSessionPool socketSessionPool,
                                        RetrofitFactory<FirebaseRetrofitService> retrofitFactory,
                                        UserService userService,
                                        UserRoleService userRoleService) {
        this.socketSessionPool = socketSessionPool;
        this.retrofitFactory = retrofitFactory;
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.AUTHSMSFIREBASE;
    }

    @Override
    public GeneralSocketResponse execute(WebSocketSession session, TextMessage message) {
        try {
            GeneralSocketRequest<AuthSmsFirebaseSocketRequest> request = mapper.readValue(message.getPayload(),
                    new TypeReference<GeneralSocketRequest<AuthSmsFirebaseSocketRequest>>() {
                    });

            AuthSmsFirebaseSocketRequest authSocketRequest = request.getObject();

            FirebaseRetrofitService retrofitService = retrofitFactory.create(FIREBASE);

            retrofitService.getRetrofitApi().getUser("application/json", apiKey,
                    new UsersFirebaseRequest(authSocketRequest)).enqueue(new Callback<UsersFirebaseResponse>() {
                @Override
                public void onResponse(Call<UsersFirebaseResponse> call, Response<UsersFirebaseResponse> response) {
                    // The token is not valid/an error occurred
                    if (response.code() == 400) {
                        sendMessage(session, new GeneralSocketResponse<>(getActionType(),
                                new AuthSmsFirebaseSocketResponse("ERROR", "BAD_REQUEST")));
                        socketSessionPool.closeSession(session, CloseStatus.NORMAL);
                    }
                    // The token is valid
                    else if (response.code() == 200) {
                        UsersFirebaseResponse usersFirebaseResponse = response.body();
                        assert usersFirebaseResponse != null;
                        String phone = usersFirebaseResponse.getUsers()[0].getPhoneNumber();

                        // The phone numbers of the token and the one specified in the socket are the same
                        if (phone.equals(authSocketRequest.getPhoneNumber())) {
                            // The user is in the database - authorization
                            if (userService.findByLogin(phone).isPresent()) {
                                User oldUser = userService.findByLogin(phone).get();

                                String authToken = getAuthToken(oldUser);
                                boolean isProfileFilled = true;
                                if (oldUser.getFirstName() == null || oldUser.getLastName() == null) {
                                    isProfileFilled = false;
                                }

                                sendMessage(session, new GeneralSocketResponse<>(getActionType(),
                                        new AuthSmsFirebaseSocketResponse("OK", "AUTH", authToken, isProfileFilled)));
                            }
                            // The user is not in the database - registration
                            else {
                                User newUser = new User();
                                newUser.setLogin(phone);
                                UserRole userRole = new UserRole();
                                userRole.setUser(newUser);
                                userRole.setRole(Role.USER);
                                newUser.setAuthType(AuthType.SMS);
                                newUser.setEnabled(true);
                                newUser.setAge(0L);

                                User userResult = userService.save(newUser);
                                userRoleService.save(userRole);

                                String authToken = getAuthToken(userResult);

                                sendMessage(session, new GeneralSocketResponse<>(getActionType(),
                                        new AuthSmsFirebaseSocketResponse("OK", "REG", authToken, false)));
                            }
                        }
                        // The phone numbers of the token and the one specified in the socket do not match
                        else {
                            sendMessage(session, new GeneralSocketResponse<>(getActionType(),
                                    new AuthSmsFirebaseSocketResponse("ERROR", "BAD_REQUEST")));
                            socketSessionPool.closeSession(session, CloseStatus.NORMAL);
                        }
                    }
                }

                @Override
                public void onFailure(Call<UsersFirebaseResponse> call, Throwable throwable) {
                    sendMessage(session, new GeneralSocketResponse<>(getActionType(),
                            new AuthSmsFirebaseSocketResponse("ERROR", "BAD_REQUEST")));
                    socketSessionPool.closeSession(session, CloseStatus.NORMAL);
                }
            });
            return new GeneralSocketResponse<>(getActionType(),
                    new AuthSmsFirebaseSocketResponse("WAIT"));
        } catch (Exception ex) {
            throw new AuthSocketException("AUTHORIZATION_ERROR", ex);
        }
    }

    private String getAuthToken(User user) {
        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + Long.parseLong(tokenExpirationMsec)))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    private void sendMessage(WebSocketSession session, Object object) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(mapper.writeValueAsString(object)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
