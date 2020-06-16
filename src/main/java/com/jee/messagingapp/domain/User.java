package com.jee.messagingapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity()
@Table(name = "users")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private long id;

    @NotEmpty(message = "Email is required.")
    @Email(message = "Wrong email format.")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String email;

    @NotEmpty(message = "Username is required.")
    @Pattern(regexp = "^[A-Za-z0-9_-]{3,15}$", message = "Username must be between 3-15 characters and contain letters, numbers, _, -")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String username;

//    @NotEmpty(message = "Password is required.")
//    @Size(min = 8, max = 32, message = "Password must have at least 8 characters.")
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String password;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Role role;

    @OneToMany(cascade = CascadeType.ALL)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private List<Post> posts = new ArrayList<Post>();

    public void setRole(Role role) {
        this.role = role;
    }

    public void addPost(Post post) {
        if(posts.contains(post)) {
            return;
        }
        if(post != null) {
            posts.add(post);
            post.setUser(this);
        }
    }

    public void removePost(Post post) {
        if (!posts.contains(post))
            return;
        if (post != null) {
            posts.remove(post);
            post.setUser(null);
        }
    }




}
