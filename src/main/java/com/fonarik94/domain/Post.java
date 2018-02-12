package com.fonarik94.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Post {
    @NonNull
    private int id;
    @NonNull
    private String header;
    @NonNull
    private String text;
    @NotNull
    private boolean isPublished;
    private LocalDateTime creationDate;
    private LocalDateTime publicationDateTime;
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public String getPublicationDateAsString() {
        if (isPublished) {
            return dateTimeFormat.format(publicationDateTime);
        } else {
            return "Not published";
        }
    }

}
