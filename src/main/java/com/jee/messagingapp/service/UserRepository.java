package com.jee.messagingapp.service;

import com.jee.messagingapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id > 0")
    List<User> findAllUsers();

    User findById(long id);

    User findByUsername(String username);

    User findByEmail(String email);

    <S extends User>S save(S user);

    void delete(User user);

}
