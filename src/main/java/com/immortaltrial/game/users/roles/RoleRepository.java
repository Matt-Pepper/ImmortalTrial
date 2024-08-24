package com.immortaltrial.game.users.roles;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    @Override
    void delete(@NonNull Role role);
}
