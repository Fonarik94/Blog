package com.fonarik94.domain;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    private LocalDateTime registrationDate;
    private String name;
    private String mail;
}
