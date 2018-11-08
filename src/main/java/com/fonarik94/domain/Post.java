package com.fonarik94.domain;

import lombok.*;
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
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NonNull
    private String header;
    @NonNull
    private String text;
    @NotNull
    private boolean published;
    private LocalDateTime creationDate;
    private LocalDateTime publicationDate;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addAllComments(List<Comment> comments){
        this.comments.addAll(comments);
    }

    public String getPublicationDateAsString() {
        if (published) {
            return dateTimeFormat.format(publicationDate);
        } else {
            return "Not published";
        }
    }

}
