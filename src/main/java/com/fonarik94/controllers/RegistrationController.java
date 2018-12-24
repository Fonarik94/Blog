package com.fonarik94.controllers;

import com.fonarik94.dto.UserRegistrationDto;
import com.fonarik94.domain.Roles;
import com.fonarik94.domain.User;
import com.fonarik94.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
public class RegistrationController {
    private final UserRepo userRepo;


    @PostMapping("/register")
    public String addUser(@Valid @ModelAttribute UserRegistrationDto userDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttr){
        boolean passwordEquals = false;
        if(!userDto.getPassword().isEmpty() && !userDto.getConfirmPassword().isEmpty()) {
            passwordEquals = userDto.getPassword().equals(userDto.getConfirmPassword());
            redirectAttr.addFlashAttribute("passwordConfirmError", "Пароли не совпадают");
        }
        if(bindingResult.hasErrors() || !passwordEquals){
            redirectAttr.addFlashAttribute("userDto", userDto);
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.userDto", bindingResult);
            return "redirect:/register";
        }else {
            if(userRepo.findAll().isEmpty()){
                User user = User.builder()
                        .username(userDto.getUsername())
                        .password(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                        .email(userDto.getEmail())
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .authority(Roles.ADMIN)
                        .build();
                userRepo.save(user);
            }
            return "redirect:/";
        }
    }

    @GetMapping("/register")
    public String register(Model model){
        if(!model.containsAttribute("userDto")) {
            model.addAttribute("userDto", new UserRegistrationDto());
        }
        return "register";
    }
    @Autowired
    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}
