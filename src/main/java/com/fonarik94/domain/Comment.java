package com.fonarik94.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
@NoArgsConstructor
@Entity(name = "Comment")
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public String getPublicationDateAsString(){
        return dateTimeFormat.format(publicationDate);
    }
}
