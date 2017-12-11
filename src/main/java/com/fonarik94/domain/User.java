package com.fonarik94.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
@Data
@RequiredArgsConstructor
public class User {
    private LocalDateTime registrationDate;
    private String name;
    private String mail;
    private String password;

    public User(String name, String mail, String password){
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.registrationDate = LocalDateTime.now();
    }
}
