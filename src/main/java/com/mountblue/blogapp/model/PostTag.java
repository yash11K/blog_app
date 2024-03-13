package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "post_tags")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostTag {

    @EmbeddedId
    private PostTagId postTagId;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAT;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PostTagId implements Serializable{
        @Column(name = "post_id")
        private int postId;
        @Column(name = "tag_id")
        private int tagId;
        public PostTagId(int post_id, int tag_id) {
            this.postId = post_id;
            this.tagId = tag_id;
        }
    }
}
