package com.jee.messagingapp.service;

import com.jee.messagingapp.domain.Post;
import com.jee.messagingapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAll();

    List<Post> findByUser(User user);

    <S extends Post>S save(S post);

    void delete(Post post);

    void deletePostsByUserUsername(String username);

}
