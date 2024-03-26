package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.exception.IdNotFoundException;
import com.mountblue.blogapp.exception.NotAuthorisedException;
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

import java.security.Principal;
import java.util.Objects;
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
    public String deletePost(@PathVariable int postId, Principal principal){
        Optional<Post> maybePost = postService.findPostById(postId);
        if(maybePost.isPresent()){
            if(Objects.equals(principal.getName(), maybePost.get().getAuthor().getUsername())){
                postTagService.deletePostTagRelationByPostId(postId);
                postService.deletePostById(postId);
                return "successfully deleted post id : " + postId;
            }
            else throw new NotAuthorisedException("not your post to delete!");
        }
        else throw new IdNotFoundException(Integer.toString(postId));
    }

    @DeleteMapping("/comment/{commentId}")
    public EntityModel<Post> deleteComment(@PathVariable int commentId, Principal principal){
        Optional<Comment> maybeRelevantComment =  commentService.findCommentById(commentId);
        if(maybeRelevantComment.isPresent()){
            if(Objects.equals(principal.getName(), maybeRelevantComment.get().getAuthor().getName())){
                int requestPostId = commentService.getPostIdFromCommentId(commentId);
                Optional<Post> maybeRelevantPost = postService.findPostById(requestPostId);
                commentService.deleteComment(maybeRelevantComment.get());
                return postAssembler.toModel(maybeRelevantPost.get());
            }
            else throw new  NotAuthorisedException("not your comment to delete");
        }
        else throw new IdNotFoundException(Integer.toString(commentId));
    }
}
