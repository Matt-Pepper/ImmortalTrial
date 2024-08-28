package com.immortaltrial.game.user.dto;

import com.immortaltrial.game.validation.password.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@PasswordMatches
public class UserDto {
    @NotNull @NotEmpty private String username;

    @NotNull @NotEmpty @Email private String email;

    @NotNull @NotEmpty private String password;
    private String matchingPassword;
}
