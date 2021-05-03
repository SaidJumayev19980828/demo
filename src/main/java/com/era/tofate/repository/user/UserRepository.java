package com.era.tofate.repository.user;

import com.era.tofate.entities.user.User;
import com.era.tofate.entities.virt.Virt;
import com.era.tofate.enums.Role;
import com.era.tofate.enums.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Page<User> findAll(Pageable pageable);
    Page<User> findAllByRolesContaining(Role Role, Pageable pageable);
    List<User> findAllByRolesContaining(Role role);

}
