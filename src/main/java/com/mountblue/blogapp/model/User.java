package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
//@NoArgsConstructor
@ToString
public class User{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email")
    private String email;
    @Column(name = "user_password")
    private String password;

    @OneToMany(mappedBy="author")
    private Set<Post> posts;

    public User(){
        this.id=21;
        this.email="yash@gmail.com";
        this.name = "yash";
        this.password="0000";
    }
}
