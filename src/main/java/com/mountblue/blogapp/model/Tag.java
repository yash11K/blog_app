package com.mountblue.blogapp.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

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
    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public Tag(){

    }
    public Tag(String name, Date createdAt) {
        this.name = name;
        this.createdAt = createdAt;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
