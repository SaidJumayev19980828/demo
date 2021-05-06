package com.era.tofate.controller.auth;

import com.era.tofate.ToFateApplication;
import com.era.tofate.payload.auth.AuthRequest;
import com.era.tofate.payload.auth.UserToken;
import com.era.tofate.security.TokenProvider;
import com.era.tofate.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = ToFateApplication.class)
public class AuthControllerTest {
    @Mock
    private UserService service;
    @InjectMocks
    private AuthController authController;
    @Autowired
    private TokenProvider tokenProviderService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Test
    void authTest(){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                "user", "user"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProviderService.createToken(authentication);
        UserToken userToken = new UserToken(token,"bearer");
        AuthRequest request = new AuthRequest("user","user");
        when(service.auth(request.getLogin(),request.getPassword())).thenReturn(userToken);
        assertEquals(service.auth(request.getLogin(),request.getPassword()),userToken);
    }
}
