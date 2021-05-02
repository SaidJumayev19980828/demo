package com.era.tofate.service.user;

import com.era.tofate.entities.user.User;
import com.era.tofate.enums.Role;
import com.era.tofate.enums.RoleDto;
import com.era.tofate.payload.auth.UserToken;
import com.era.tofate.repository.user.UserRepository;
import com.era.tofate.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final JdbcTemplate jdbcTemplate;

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
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    login, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProviderService.createToken(authentication);
            UserToken userToken = new UserToken(token, "Bearer");
            return userToken;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Map<String,Object>> findAllByRolesContaining(RoleDto role, int page, int pageSize) {
        return jdbcTemplate.queryForList("select * from users u inner join user_role ur on u.id = ur.user_id " +
                "where ur.role = ? limit ?, ?",
                role.getRoleName(),
                (page - 1) * pageSize,
                pageSize);
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
