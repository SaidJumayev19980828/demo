package com.era.tofate.service.user;

import com.era.tofate.entities.user.User;
import com.era.tofate.enums.Role;
import com.era.tofate.payload.auth.UserToken;
import com.era.tofate.repository.user.UserRepository;
import com.era.tofate.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProviderService;

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }
    @Override
    public UserToken auth(String login, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    login, password));

            User currentUser = repository.findByLogin(login).get();

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProviderService.createToken(authentication);
            Role role = currentUser.getRoles().stream().findFirst().get().getRole();

            UserToken userToken = new UserToken(role, token, "Bearer");
            return userToken;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public User save(User object) {
        return repository.save(object);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }
}
