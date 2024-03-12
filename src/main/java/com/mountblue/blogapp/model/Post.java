package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Post")
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
    @Column(name = "post_is_published")
    private boolean isPublished;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
    public Post(String postContent, String postExcerpt, String postAuthor, Date postPublishedAt, Date created_at, Date updated_at, boolean isPublished, Set<Tag> tags) {
        this.postContent = postContent;
        this.postExcerpt = postExcerpt;
        this.postAuthor = postAuthor;
        this.postPublishedAt = postPublishedAt;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.isPublished = isPublished;
    }

}
