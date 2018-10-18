package com.fonarik94.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Post {
    private int id;
    @NonNull
    private String header;
    @NonNull
    private String text;
    @NotNull
    private boolean isPublished;
    private LocalDateTime creationDate;
    private LocalDateTime publicationDateTime;
    private List<Comment> commentList = new ArrayList<>();
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");


    public void addComment(Comment comment){
        commentList.add(comment);
    }

    public void addAllComments(List<Comment> comments){
        commentList.addAll(comments);
    }

    public String getPublicationDateAsString() {
        if (isPublished) {
            return dateTimeFormat.format(publicationDateTime);
        } else {
            return "Not published";
        }
    }

}
