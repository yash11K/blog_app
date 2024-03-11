package com.mountblue.blogapp.model;

import jakarta.persistence.*;

import java.util.Date;

//@Entity(name = "Post")
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int id;
    @Column(name = "post_content")
    private String postContent;
    @Column(name = "post_excerpt")
    private String postExcerpt;
    @Column(name = "post_author")
    private String postAuthor;
    @Column(name = "post_published_at")
    private Date postPublishedAt;
    @Column(name = "post_created_at")
    private Date created_at;
    @Column(name = "post_updated_at")
    private Date updated_at;
    @Column(name = "post__is_published")
    private boolean isPublished;

}
