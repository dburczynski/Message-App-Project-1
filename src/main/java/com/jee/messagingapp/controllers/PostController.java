package com.jee.messagingapp.controllers;

import com.jee.messagingapp.domain.Post;
import com.jee.messagingapp.domain.User;
import com.jee.messagingapp.service.PostRepository;
import com.jee.messagingapp.service.SecurityService;
import com.jee.messagingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Calendar;

@Controller
public class PostController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post")
    public String post(Model model) {
        model.addAttribute("post",new Post());

        return "post";
    }

    @PostMapping("/post")
    public String processPost(@Valid Post post, Errors errors) {
        if(errors.hasErrors())
            return "post";
        post.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            User user = userService.findByUsername(auth.getName());
            user.addPost(post);
            userService.saveWithoutEncrypt(user);
            return "redirect:/";
        }
        return "redirect:/login";
    }
}