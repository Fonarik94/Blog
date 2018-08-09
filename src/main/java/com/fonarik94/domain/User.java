package com.fonarik94.domain;

import lombok.*;
import java.time.LocalDateTime;
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class User {
    @NonNull
    private String name;
    @NonNull
    private String mail;
    @NonNull
    private String password;
    private LocalDateTime registrationDate;

}
