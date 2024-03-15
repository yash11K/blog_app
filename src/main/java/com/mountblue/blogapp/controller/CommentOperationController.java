package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@Controller
public class CommentOperationController extends AbstractBlogControl{
    public CommentOperationController(PostService postService,
                                      TagService tagService,
                                      PostTagService postTagService,
                                      CommentService commentService,
                                      UserService userService) {
        super(postService, tagService, postTagService, commentService, userService);
    }

    @PostMapping("/blog/newComment")
    public String postComment(@RequestParam("postId") Integer postId, @ModelAttribute("comments")Comment comment){
        User testUser = userService.findUserById(103);
        comment.setAuthor(testUser);
        comment.setCreated_at(new Date());
        comment.setUpdated_at(new Date());
        comment.setPost(postService.findPostById(postId));
        commentService.saveComment(comment);
        String redirectURI = "/blog?postId=" + postId;
        return "redirect:" + redirectURI;
    }

    @GetMapping("/blog/deleteComment")
    public String deleteComment(@RequestParam("commentIdToDelete")Integer commentId){
        int postId = commentService.getPostIdFromCommentId(commentId);
        String redirectURI = "/blog?postId=" + Integer.toString(postId);
        return "redirect:" + redirectURI;
    }

    @GetMapping("/blog/updateComment")
    public String updateComment(@RequestParam("commentIdToUpdate")Integer commentId,
                                Model model){
        int postId = commentService.getPostIdFromCommentId(commentId);
        Optional<Comment> maybeComment = commentService.findCommentById(commentId);
        maybeComment.ifPresent(comment -> model.addAttribute("commentIdToUpdate", commentId));
        Post post = postService.findPostById(postId);
        addFullBlogDetailsToModel(model, post, commentService.findAllCommentsOfPost(post));
        return "fullBlog";
    }

    @PostMapping("/blog/updateCommentSubmit")
    public String updatedComment(@RequestParam("updatedComment")String updatedComment, @RequestParam("commentIdToUpdate")Integer commentId){
        int postId = commentService.getPostIdFromCommentId(commentId);
        Optional<Comment> maybeCommentToUpdate = commentService.findCommentById(commentId);
        if(maybeCommentToUpdate.isPresent()){
            Comment commentToUpdate = maybeCommentToUpdate.get();
            commentToUpdate.setComment(updatedComment);
            commentService.saveComment(commentToUpdate);
        }
        String redirectURI = "/blog?postId=" + Integer.toString(postId);
        return "redirect:" + redirectURI;
    }

}
