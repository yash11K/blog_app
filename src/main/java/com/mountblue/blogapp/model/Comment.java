package com.mountblue.blogapp.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(name = "comment_author_name")
    private String authorName;
    @Column(name = "comment_created_at")
    private Date created_at;
    @Column(name = "comment_updated_at")
    private Date updated_at;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_author_id", referencedColumnName = "user_id", updatable = false)
    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_post_id", referencedColumnName = "post_id", updatable = false)
    private Post post;
}
