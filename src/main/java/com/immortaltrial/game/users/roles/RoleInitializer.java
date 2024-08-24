package com.immortaltrial.game.users.roles;

import jakarta.annotation.PostConstruct;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {
    private final RoleRepository roleRepository;

    @Autowired
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
