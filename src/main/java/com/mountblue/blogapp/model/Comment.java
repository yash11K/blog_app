package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "comment")
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
    @Column(name = "comment_content", columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "comment_author_id", referencedColumnName = "user_id", updatable = false)
    private User author;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "comment_post_id", referencedColumnName = "post_id", updatable = false)
    private Post post;
}
