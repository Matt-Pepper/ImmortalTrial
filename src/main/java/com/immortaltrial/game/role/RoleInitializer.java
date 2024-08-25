package com.immortaltrial.game.role;

import com.immortaltrial.game.role.entity.ERole;
import com.immortaltrial.game.role.entity.Role;
import com.immortaltrial.game.role.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {
    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {

        if (roleRepository.count() != ERole.values().length) {
            for (ERole eRole : ERole.values()) {
                Optional<Role> existingRole = roleRepository.findByName(eRole);
                if (existingRole.isEmpty()) {
                    Role role = new Role(eRole);
                    roleRepository.save(role);
                }
            }
        }
    }
}
