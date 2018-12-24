package com.fonarik94.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity(name = "Post")
@Table(name = "posts")
@Cache(region ="posts", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NonNull
    private String header;
    @NonNull
    @Column(columnDefinition = "TEXT")
    private String text;
    @NotNull
    private boolean published;
    private LocalDateTime creationDate;
    private LocalDateTime publicationDate;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    @Cache(region = "comments", usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Comment> comments = new ArrayList<>();
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public String getPublicationDateAsString() {
        if (published) {
            return dateTimeFormat.format(publicationDate);
        } else {
            return "Not published";
        }
    }

}
