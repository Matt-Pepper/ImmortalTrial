package com.immortaltrial.game.user.controller;

import com.immortaltrial.game.user.dto.UserDto;
import com.immortaltrial.game.user.entity.User;
import com.immortaltrial.game.user.service.UserService;
import com.immortaltrial.game.web.error.RoleNotFound;
import com.immortaltrial.game.web.error.UserAlreadyExistException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    public final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/check-username")
    @ResponseBody
    public String checkUsername(@RequestParam String username) {
        if (userService.usernameExists(username)) {
            return "That username is already in use.";
        }
        return "";
    }

    @GetMapping("/check-email")
    @ResponseBody
    public String checkEmail(@RequestParam String email) {
        if (userService.emailExists(email)) {
            return "That email is already in use.";
        }
        return "";
    }

    @GetMapping
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(
            @ModelAttribute("user") @Valid UserDto userDto,
            HttpServletRequest request,
            Errors errors,
            Model model) {
        if (errors.hasErrors()) {
            return "registration";
        }
        try {
            User registered = userService.registerNewUserAccount(userDto);
            return "redirect:/login";
        } catch (UserAlreadyExistException | RoleNotFound uaeEx) {
            model.addAttribute("message", uaeEx.getMessage());
            return "registration";
        }
    }
}
