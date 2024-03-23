package com.mountblue.blogapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Collection;
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
    @Column(name = "tag_created_at", columnDefinition = "datetime" ,updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "tag_updated_at",columnDefinition = "datetime")
    private Date updatedAt;
    @ManyToMany(mappedBy = "tags",
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonBackReference
    private Collection<Post> posts;
}
