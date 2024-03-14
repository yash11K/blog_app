package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity(name = "Post")
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {

    @Column(name = "post_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "post_title")
//    @Min(value = 3, message = "should be more than 3 characters")
//    @Max(value = 124, message = "Should be less than 125 characters")
    private String title;
    @Column(name = "post_content", columnDefinition = "TEXT")
    @Lob
//    @Min(value = 30, message = "Should Be more than 30 characters")
    private String content;
    @Column(name = "post_excerpt")
    private String excerpt;
    @Column(name = "post_published_at")
    @CreationTimestamp //fordev
    private Date publishedAt;
    @Column(name = "post_created_at", updatable = false)
    @CreationTimestamp //fordev
    private Date createdAt;
    @Column(name = "post_updated_at")
    @UpdateTimestamp
    private Date updatedAt;
    @Column(name = "post_is_published")
    private boolean isPublished;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "user_id", updatable = false)
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    Set<Comment> comments;
}
