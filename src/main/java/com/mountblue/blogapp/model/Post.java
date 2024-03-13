package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
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
    @Column(name = "post_author")
    private String author;
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

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    public Post(String content,
                String excerpt,
                String author,
                Date publishedAt,
                Date created_at,
                Date updated_at,
                boolean isPublished,
                Set<Tag> tags) {
        this.content = content;
        this.excerpt = excerpt;
        this.author = author;
        this.publishedAt = publishedAt;
        this.createdAt = created_at;
        this.updatedAt = updated_at;
        this.isPublished = isPublished;
        this.tags = tags;
    }
}
