package com.immortaltrial.game.users;

import com.immortaltrial.game.users.roles.ERole;
import com.immortaltrial.game.users.roles.Role;
import com.immortaltrial.game.users.roles.RoleRepository;
import com.immortaltrial.game.web.error.RoleNotFound;
import com.immortaltrial.game.web.error.UserAlreadyExistException;
import java.util.Collections;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) {
        if (usernameExists(userDto.getUsername())) {
            throw new UserAlreadyExistException("That username is already in use.");
        }

        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is already an account with that email address.");
        }
        final User user = new User();

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        try {
            Role role =
                    roleRepository
                            .findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RoleNotFound("Error: Role is not found."));

            user.setRoles(Collections.singleton(role));
        } catch (RoleNotFound rnfEx) {
            LOGGER.error(rnfEx.getMessage(), rnfEx);
            throw new RoleNotFound("Unable to register.");
        }

        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public Optional<User> findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
