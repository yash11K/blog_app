package com.mountblue.blogapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(name = "post_published_at", columnDefinition = "datetime", updatable = false)
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private Date publishedAt;
    @Column(name = "post_created_at", columnDefinition = "datetime", updatable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;
    @Column(name = "post_updated_at", columnDefinition = "datetime")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date updatedAt;
    @Column(name = "post_is_published")
    private boolean isPublished;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE},fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonManagedReference
    private Set<Tag> tags;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "user_id", updatable = false)
    @JsonManagedReference
    private User author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    Set<Comment> comments;
}