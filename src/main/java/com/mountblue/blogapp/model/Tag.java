package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity(name = "tag")
@Table(name = "tags")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private int id;
    @Column(name = "tag_name", unique = true)
    private String name;
    @Column(name = "tag_created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "tag_updated_at")
    @UpdateTimestamp //fordev
    private Date updatedAt;
    @ManyToMany(mappedBy = "tags",
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    private Set<Post> posts;

    public Tag(String name) {
        this.name = name;
    }
}
