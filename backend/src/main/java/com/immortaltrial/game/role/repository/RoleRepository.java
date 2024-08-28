package com.immortaltrial.game.role.repository;

import com.immortaltrial.game.role.entity.ERole;
import com.immortaltrial.game.role.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    @Override
    void delete(@NonNull Role role);
}
