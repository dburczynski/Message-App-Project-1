package com.jee.messagingapp.controllers;

import com.jee.messagingapp.domain.Post;
import com.jee.messagingapp.domain.User;
import com.jee.messagingapp.service.CsvUtil;
import com.jee.messagingapp.service.PostRepository;
import com.jee.messagingapp.service.SecurityService;
import com.jee.messagingapp.service.UserService;
import io.micrometer.core.ipc.http.HttpSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@Valid User user, Errors errors) throws Exception{
        if(errors.hasErrors()) {
            return "registration";
        }
        userService.save(user);
        userService.sendRegestrationMail(user.getEmail(), user.getUsername());

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("register")
    public String register(@RequestParam(name="username", required = true) String username, Model model) {
        userService.confirm(userService.findByUsername(username));
        model.addAttribute("username", username);
        return "confirm";
    }

    @GetMapping("/")
    public String home(Model model) {

        List<Post> posts = postRepository.findAll();
        Collections.reverse(posts);
        model.addAttribute("posts", posts);
        return "home";

    }

    @GetMapping("/search")
    public String search(Model model) {
        model.addAttribute("user", new User());
        return "search";
    }
    @PostMapping("/search")
    public String processSearch(User user, Model model) {
        User userInDb = userService.findByUsername(user.getUsername());
        List<Post> posts = postRepository.findByUser(userInDb);
        model.addAttribute("posts", posts);
        return "userPage";
    }

    @GetMapping("/show-users")
    public String showUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }

    @GetMapping("/delete")
    public String deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userService.delete(userService.findByUsername(auth.getName()));
        return "redirect:/logout";
    }

    @GetMapping("/edit")
    public String editPassword(Model model) {
        model.addAttribute("user",new User());
        return "edit-password";
    }

    @PostMapping("/edit")
    public String processEdit(User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User realUser = userService.findByUsername(auth.getName());
        realUser.setPassword(user.getPassword());
        userService.saveWithEncrypt(realUser);
        return "redirect:/logout";
    }

    @GetMapping("/downloads")
    public String downloads() {
        return "downloads";
    }

    @GetMapping("/downloads/users.csv")
    public void downloadUserCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=Users.csv");
        CsvUtil.userCsv(response.getWriter(), userService.findAll());
    }

    @GetMapping("/downloads/posts.csv")
    public void downloadPostCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=Posts.csv");
        CsvUtil.postCsv(response.getWriter(), postRepository.findAll());
    }

//
//    @GetMapping("/downloads/posts.json")

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
}
