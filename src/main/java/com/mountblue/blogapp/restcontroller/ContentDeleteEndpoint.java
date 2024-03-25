package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.exception.IdNotFoundException;
import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/writewise/api/delete")
public class ContentDeleteEndpoint {
    private final ServiceFactory serviceFactory;
    private final PostAssembler postAssembler;
    private PostService postService;
    private CommentService commentService;
    private PostTagService postTagService;

    @Autowired
    public ContentDeleteEndpoint(ServiceFactory serviceFactory, PostAssembler postAssembler) {
        this.serviceFactory = serviceFactory;
        this.postAssembler = postAssembler;
    }

    @PostConstruct
    void initService(){
        this.postService = serviceFactory.getPostService();
        this.postTagService = serviceFactory.getPostTagService();
        this.commentService = serviceFactory.getCommentService();
    }

    @DeleteMapping("/post/{postId}")
    public String deletePost(@PathVariable int postId){
        if(postService.findPostById(postId).isPresent()){
            postTagService.deletePostTagRelationByPostId(postId);
            postService.deletePostById(postId);
            return "successfully deleted post id : " + postId;
        }
        else throw new IdNotFoundException(Integer.toString(postId));
    }

    @DeleteMapping("/comment/{commentId}")
    public EntityModel<Post> deleteComment(@PathVariable int commentId){
        Optional<Comment> maybeRelevantComment =  commentService.findCommentById(commentId);
        if(maybeRelevantComment.isPresent()){
            int requestPostId = commentService.getPostIdFromCommentId(commentId);
            Optional<Post> maybeRelevantPost = postService.findPostById(requestPostId);
            commentService.deleteComment(maybeRelevantComment.get());
            return postAssembler.toModel(maybeRelevantPost.get());
        }
        else throw new IdNotFoundException(Integer.toString(commentId));
    }
}
