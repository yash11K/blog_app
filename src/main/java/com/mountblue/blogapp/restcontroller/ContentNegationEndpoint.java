package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.CommentDto;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.PostDto;
import com.mountblue.blogapp.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/writewise/api/update")
public class ContentNegationEndpoint{
    private final ServiceFactory serviceFactory;
    private final PostAssembler postAssembler;
    private PostService postService;
    private UserService userService;
    private TagService tagService;
    private CommentService commentService;

    private final String blogActionPublish = "publish";

    @Autowired
    public ContentNegationEndpoint(ServiceFactory serviceFactory, PostAssembler postAssembler) {
        this.serviceFactory = serviceFactory;
        this.postAssembler = postAssembler;
    }

    @PostConstruct
    public void initService(){
        this.postService = serviceFactory.getPostService();
        this.userService = serviceFactory.getUserService();
        this.tagService = serviceFactory.getTagService();
        this.commentService = serviceFactory.getCommentService();
    }

    @PostMapping("/comment/{commentId}")
    public EntityModel<Post> updateComment(@PathVariable int commentId, @RequestBody CommentDto commentDto) throws Exception {
        if(commentService.findCommentById(commentId).isPresent()){
            Post relevantPost = postService.findPostById(commentService.getPostIdFromCommentId(commentId)).get();
            Comment comment = commentService.findCommentById(commentId).get();
            comment.setComment(commentDto.getComment());
            comment.setUpdated_at(postService.setDateToday());
            commentService.saveComment(comment);
            return EntityModel.of(relevantPost);
        } else throw new Exception();
    }

    @PostMapping("/post/{postId}")
    public EntityModel<Post> updatePost(@PathVariable int postId, @RequestBody PostDto postDto){
//        if(postService.findPostById())
        return null;
    }


}
