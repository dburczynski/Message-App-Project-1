package com.jee.messagingapp.service;

import com.jee.messagingapp.domain.Role;
import com.jee.messagingapp.domain.User;
import com.jee.messagingapp.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role unconfirmedUserRole = roleRepository.findByName(UserType.UNCONFIRMEDUSER.toString());
        user.setRole(unconfirmedUserRole);
        userRepository.save(user);
    }

    @Override
    public void saveWithEncrypt(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void saveWithoutEncrypt(User user) {
        userRepository.save(user);
    }
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void confirm(User user) {

        Role unconfirmedUserRole = roleRepository.findByName(UserType.UNCONFIRMEDUSER.toString());
        Role userRole = roleRepository.findByName(UserType.USER.toString());
        user.setRole(userRole);
        userRepository.save(user);
    }

    @Override
    public void updateToAdmin(User user) {
        Role unconfirmedUserRole = roleRepository.findByName(UserType.UNCONFIRMEDUSER.toString());
        Role userRole = roleRepository.findByName(UserType.USER.toString());
        Role adminRole = roleRepository.findByName(UserType.ADMIN.toString());
        user.setRole(adminRole);

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void sendRegestrationMail(String email, String username) throws Exception {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail,true);
            helper.setTo(email);
            helper.setReplyTo("dburczynski97@gmail.com");
            helper.setFrom("dburczynski97@gmail.com");
            helper.setSubject("Account Confirmation");
            helper.setText("To confirm your account please click the following link: localhost:8080/register?username="+username,true);

        }
        catch (MessagingException e){
            e.printStackTrace();
        }
        javaMailSender.send(mail);

    }

}
