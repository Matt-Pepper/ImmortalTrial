package com.immortaltrial.game.role;

import com.immortaltrial.game.role.entity.ERole;
import com.immortaltrial.game.role.entity.Role;
import com.immortaltrial.game.role.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {
    private final RoleRepository roleRepository;
    private final JdbcTemplate jdbcTemplate;

    public RoleInitializer(RoleRepository roleRepository, JdbcTemplate jdbcTemplate) {
        this.roleRepository = roleRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initRoles() {
        if (roleRepository.count() != ERole.values().length) {
            updateRolesConstraint();

            for (ERole eRole : ERole.values()) {
                Optional<Role> existingRole = roleRepository.findByName(eRole);
                if (existingRole.isEmpty()) {
                    Role role = new Role(eRole);
                    roleRepository.save(role);
                }
            }
        }
    }

    private void updateRolesConstraint() {
        String roleNames =
                Arrays.stream(ERole.values()).map(Enum::name).collect(Collectors.joining("', '"));

        String sql =
                "ALTER TABLE roles DROP CONSTRAINT IF EXISTS roles_name_check;"
                        + "ALTER TABLE roles ADD CONSTRAINT roles_name_check "
                        + "CHECK (name IN ('"
                        + roleNames
                        + "'))";

        jdbcTemplate.execute(sql);
    }
}
