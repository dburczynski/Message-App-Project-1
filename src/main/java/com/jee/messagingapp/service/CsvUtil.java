package com.jee.messagingapp.service;

import com.jee.messagingapp.domain.Post;
import com.jee.messagingapp.domain.User;

import java.io.PrintWriter;
import java.util.List;

public class CsvUtil {
    public static void userCsv(PrintWriter writer, List<User> users) {
        writer.write("username, email, role\n");
        for(User user: users) {
            writer.write(user.getUsername()+","+user.getEmail()+","+user.getRole().getName()+"\n");
        }
    }

    public static void postCsv(PrintWriter writer, List<Post> posts) {
        writer.write("username, message, date\n");
        for(Post post: posts) {
            writer.write(post.getUser().getUsername()+","+post.getMessage()+","+post.getCreateDate().toString()+"\n");
        }
    }
}
