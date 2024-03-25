package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.exception.IdNotFoundException;
import com.mountblue.blogapp.model.*;
import com.mountblue.blogapp.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/writewise/api/update")
public class ContentNegationEndpoint{
    private final ServiceFactory serviceFactory;
    private final PostAssembler postAssembler;
    private final DbaService dbaService;
    private PostService postService;
    private CommentService commentService;


    @Autowired
    public ContentNegationEndpoint(ServiceFactory serviceFactory, PostAssembler postAssembler, DbaService dbaService) {
        this.serviceFactory = serviceFactory;
        this.postAssembler = postAssembler;
        this.dbaService = dbaService;
    }

    @PostConstruct
    public void initService(){
        this.postService = serviceFactory.getPostService();
        this.commentService = serviceFactory.getCommentService();
    }

    @PostMapping("/comment/{commentId}")
    public EntityModel<Post> updateComment(@PathVariable int commentId, @RequestBody CommentDto commentDto) throws Exception {
        int requestPostId = commentService.getPostIdFromCommentId(commentId);
        Optional<Post> maybeRelevantPost = postService.findPostById(requestPostId);
        if(maybeRelevantPost.isPresent()){
            if(commentService.findCommentById(commentId).isPresent()){
                dbaService.udpateComment(commentId, commentDto);
                return EntityModel.of(maybeRelevantPost.get());
            }
            else throw new IdNotFoundException(Integer.toString(commentId));
        }
        else throw new IdNotFoundException(Integer.toString(requestPostId));

    }

    @PostMapping("/post/{postId}")
    public EntityModel<Post> updatePost(@PathVariable int postId, @RequestBody PostDto postDto) throws IdNotFoundException, ParseException{
        if(postService.findPostById(postId).isPresent()){
            return postAssembler.toModel(dbaService.updatePost(postId, postDto));
        }
        else throw new IdNotFoundException(Integer.toString(postId));
    }
}
