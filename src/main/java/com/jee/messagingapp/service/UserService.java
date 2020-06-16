package com.jee.messagingapp.service;

import com.jee.messagingapp.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    void save(User user);

    void saveWithEncrypt(User user);

    void saveWithoutEncrypt(User user);

    void confirm(User user);

    void updateToAdmin(User user);

    User findByUsername(String username);

    void sendRegestrationMail(String email, String username) throws Exception;

    void delete(User user);
}
