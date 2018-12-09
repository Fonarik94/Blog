package com.fonarik94.repo;

import com.fonarik94.domain.Post;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query("from Post as post where post.id > 1 and post.published=true order by post.publicationDate desc ")
    List<Post> findPublished();
    @Query("from Post as post order by post.id desc ")
    List<Post> findAll();
}