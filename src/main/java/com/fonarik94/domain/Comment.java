package com.fonarik94.domain;

import lombok.*;
import org.hibernate.validator.constraints.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    @NonNull
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @NotBlank(message = "Укажите имя")
    private String author;
    private LocalDateTime publicationDate = LocalDateTime.now();
    @NonNull
    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    @NotBlank(message = "Коментарий не может быть пустым")
    private String text;
    private Comment answer;
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public String getPublicationDateAsString(){
        return dateTimeFormat.format(publicationDate);
    }
}
