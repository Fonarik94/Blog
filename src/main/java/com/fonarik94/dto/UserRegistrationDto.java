package com.fonarik94.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDto {
    @Size(min = 3, max = 16, message = "Имя должно быть от 3 до 16 символов")
    private String username;
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Не валидный email")
    private String email;
    @Size(min = 8, max = 16, message = "Пароль должен быть от 8 до 16 символов")
    private String password;
    @Size(min = 8, max = 16, message = "Подтверждеие пароля должно быть от 8 до 16 символов")
    private String confirmPassword;
}
