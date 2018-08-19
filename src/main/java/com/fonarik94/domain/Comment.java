package com.fonarik94.domain;

import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    @NonNull
    private String author;
    private LocalDateTime publicationDate = LocalDateTime.now();
    @NonNull
    private String text;
    private Comment answer;
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public String getPublicationDateAsString(){
        return dateTimeFormat.format(publicationDate).toString();
    }
}
