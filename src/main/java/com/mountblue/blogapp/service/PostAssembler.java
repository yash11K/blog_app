package com.mountblue.blogapp.service;


import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.restcontroller.PostsApi;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {
    @Override
    public EntityModel<Post> toModel(Post post) {
        return EntityModel.of(post,
                linkTo(methodOn(PostsApi.class).fetchPost(post.getId())).withSelfRel(),
                linkTo(methodOn(PostsApi.class).fetchAllPosts()).withRel("posts"),
                linkTo(methodOn(PostsApi.class).fetchAllPostsByIsPublished(post.isPublished())).withRel("postsByPublishStatus"));
    }
}
