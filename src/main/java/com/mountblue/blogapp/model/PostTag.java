package com.mountblue.blogapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "post_tags")
public class PostTags {

    @EmbeddedId
    private PostTagId postTagId;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAT;
    @Getter
    @Setter
    @Embeddable
    private static class PostTagId implements Serializable{
        @Column(name = "post_id")
        private int postId;
        @Column(name = "tag_id")
        private int tagId;
        public PostTagId(int post_id, int tag_id) {
            this.postId = post_id;
            this.tagId = tag_id;
        }
        public PostTagId() {
        }
    }
}
