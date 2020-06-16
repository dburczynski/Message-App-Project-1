package com.jee.messagingapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jee.messagingapp.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "role")
@Data
@NoArgsConstructor
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private long id;

    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private String name;


//    @OneToMany(cascade = CascadeType.ALL)
//    @Getter(AccessLevel.PUBLIC)
//    @Setter(AccessLevel.PUBLIC)
//    private List<User> users;




}
