package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.model.CommentDto;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.PostDto;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@RestController
@RequestMapping("/writewise/api/new")
public class ContentCreationEndpoint {
    private final PostAssembler postAssembler;
    private final DbaService dbaService;

    @Autowired
    public ContentCreationEndpoint(PostAssembler postAssembler, DbaService dbaService) {
        this.postAssembler = postAssembler;
        this.dbaService = dbaService;
    }

    @PostMapping("/post/{blogStatusAction}")
    public EntityModel<Post> createNewPost(@RequestBody PostDto newPostDto,
                                           @PathVariable String blogStatusAction,
                                           Principal principal) throws ParseException {
        return postAssembler.toModel(dbaService.savePostFromDto(newPostDto, blogStatusAction, principal));
    }

    @PostMapping("/{postId}/comment")
    public EntityModel<Post> CreateCommentOnPost(@PathVariable int postId,
                                                 @RequestBody CommentDto commentDto,
                                                 Principal principal) throws ParseException {
        return postAssembler.toModel(dbaService.saveCommentFromDto(commentDto, postId, principal));
    }
}

