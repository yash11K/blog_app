package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.CommentDto;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.PostDto;
import com.mountblue.blogapp.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

@RestController
@RequestMapping("/writewise/api/new")
public class ContentCreationEndpoint {
    private final ServiceFactory serviceFactory;
    private final PostAssembler postAssembler;
    private PostService postService;
    private UserService userService;
    private TagService tagService;
    private CommentService commentService;

    private final String blogActionPublish = "publish";

    @Autowired
    public ContentCreationEndpoint(ServiceFactory serviceFactory, PostAssembler postAssembler) {
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

    @PostMapping("/post/{blogStatusAction}")
    public EntityModel<Post> createNewPost(@RequestBody PostDto newPostDto,
                                           @PathVariable String blogStatusAction,
                                           Principal principal) throws ParseException {
        Post newPost = new Post();
        newPost.setAuthor(userService.findUserByUserName(principal.getName()).get());
        newPost.setTitle(newPostDto.getTitle());
        newPost.setContent(newPostDto.getContent());
        newPost.setCreatedAt(postService.setDateToday());
        newPost.setPublishedAt(postService.setDateToday());
        tagService.saveTagFromTagString(newPostDto.getTagStr(), newPost);
        newPost.setPublished(blogStatusAction.equals(blogActionPublish));
        newPost.setExcerpt(PostService.createExcerpt(newPostDto.getContent()));
        postService.savePost(newPost);
        return postAssembler.toModel(newPost);
    }

    @PostMapping("/{postId}/comment")
    public EntityModel<Post> CreateCommentOnPost(@PathVariable int postId,
                                                 @RequestBody CommentDto commentDto,
                                                 Principal principal) throws ParseException {
        Post relevantPost = postService.findPostById(postId).get();
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setCreated_at(postService.setDateToday());
        comment.setAuthor(userService.findUserByUserName(principal.getName()).get());
        comment.setPost(relevantPost);
        commentService.saveComment(comment);
        return postAssembler.toModel(relevantPost);
    }
}

