package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@Controller
public class CommentOperationController extends AbstractBlogControl{
    private final ServiceFactory serviceFactory;
    private PostService postService;
    private CommentService commentService;
    private UserService userService;
    @Autowired
    public CommentOperationController(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @ModelAttribute
    public void initService(){
        this.postService = serviceFactory.getPostService();
        this.commentService = serviceFactory.getCommentService();
        this.userService = serviceFactory.getUserService();
    }

    @PostMapping("/blog/newComment")
    public String postComment(@RequestParam("postId") Integer postId, @ModelAttribute("comments")Comment comment, Principal principal) throws ParseException {
        comment.setAuthor(userService.findUserByUserName(principal.getName()).get());
        comment.setCreated_at(postService.setDateToday());
        comment.setPost(postService.findPostById(postId).get());
        commentService.saveComment(comment);
        String redirectURI = "/blog?postId=" + postId;
        return "redirect:" + redirectURI;
    }

    @GetMapping("/blog/deleteComment")
    public String deleteComment(@RequestParam("commentIdToDelete")Integer commentId){
        int postId = commentService.getPostIdFromCommentId(commentId);
        commentService.deleteComment(commentService.findCommentById(commentId).get());
        String redirectURI = "/blog?postId=" + postId;
        return "redirect:" + redirectURI;
    }

    @GetMapping("/blog/updateComment")
    public String updateComment(@RequestParam("commentIdToUpdate")Integer commentId,
                                Model model){
        int postId = commentService.getPostIdFromCommentId(commentId);
        Optional<Comment> maybeComment = commentService.findCommentById(commentId);
        maybeComment.ifPresent(comment -> model.addAttribute("commentIdToUpdate", commentId));
        Post post = postService.findPostById(postId).get();
        addModelAttributeOfFullBlog(model, post, commentService.findAllCommentsOfPost(post));
        return "fullBlog";
    }

    @PostMapping("/blog/updateCommentSubmit")
    public String updatedComment(@RequestParam("updatedComment")String updatedComment, @RequestParam("commentIdToUpdate")Integer commentId){
        int postId = commentService.getPostIdFromCommentId(commentId);
        Optional<Comment> maybeCommentToUpdate = commentService.findCommentById(commentId);
        if(maybeCommentToUpdate.isPresent()){
            Comment commentToUpdate = maybeCommentToUpdate.get();
            commentToUpdate.setComment(updatedComment);
            commentToUpdate.setUpdated_at(new Date());
            commentService.saveComment(commentToUpdate);
        }
        String redirectURI = "/blog?postId=" + postId;
        return "redirect:" + redirectURI;
    }
}
