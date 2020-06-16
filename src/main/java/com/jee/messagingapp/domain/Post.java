package com.jee.messagingapp.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Calendar;

@Data
@Entity
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @NotEmpty(message = "Post must not be empty.")
    @Size(max = 250, message = "Post must be under 250 characters.")
    private String message;

    private Date createDate;

}
