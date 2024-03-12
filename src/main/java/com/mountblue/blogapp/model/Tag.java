package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tag")
@Table(name = "tags")
public class Tag {
    @Id
    @Column(name = "tag_id")
    private int id;
    @Column(name = "tag_name")
    private String name;
    @Column(name = "tag_created_at")
    private Date createdAt;
    @Column(name = "tag_updated_at")
    private Date updatedAt;
    @ManyToMany(mappedBy = "tags",
                cascade = {CascadeType.ALL})
    private List<Post> posts;

    public Tag(String name, Date createdAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt=createdAt;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
