package com.fonarik94.repo;

import com.fonarik94.domain.Comment;
import com.fonarik94.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findByPublishedTrueOrderByPublicationDateTimeDesc();
}
