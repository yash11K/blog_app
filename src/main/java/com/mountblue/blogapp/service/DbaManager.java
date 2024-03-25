package com.mountblue.blogapp.service;

import com.mountblue.blogapp.exception.IdNotFoundException;
import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.CommentDto;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.PostDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.ParseException;

@Service
public class DbaManager implements DbaService{
    private final ServiceFactory serviceFactory;
    private PostService postService;
    private TagService tagService;
    private CommentService commentService;
    private UserService userService;

    @Autowired
    public DbaManager(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @PostConstruct
    void initService(){
        this.postService = serviceFactory.getPostService();
        this.tagService = serviceFactory.getTagService();
        this.tagService = serviceFactory.getTagService();
        this.commentService = serviceFactory.getCommentService();
        this.userService = serviceFactory.getUserService();
    }

    @Override
    public Post updatePost(int postId, PostDto postDto) throws IdNotFoundException, ParseException {
        if(postService.findPostById(postId).isPresent()){
            Post relevantPost = postService.findPostById(postId).get();
            tagService.saveTagFromTagString(postDto.getTagStr(), relevantPost);
            relevantPost.setTitle(postDto.getTitle());
            relevantPost.setUpdatedAt(postService.setDateToday());
            relevantPost.setContent(postDto.getContent());
            postService.savePost(relevantPost);
            return relevantPost;
        }
        else throw new IdNotFoundException(Integer.toString(postId));
    }

    @Override
    public Comment udpateComment(int commentId, CommentDto commentDto) throws IdNotFoundException, ParseException {
        if(commentService.findCommentById(commentId).isPresent()){
            Comment comment = commentService.findCommentById(commentId).get();
            comment.setComment(commentDto.getComment());
            comment.setUpdated_at(postService.setDateToday());
            commentService.saveComment(comment);
            return comment;
        }
        else throw new IdNotFoundException(Integer.toString(commentId));
    }



    @Override
    public Post savePostFromDto(PostDto newPostDto ,String blogStatusAction, Principal principal) throws ParseException {
        final String blogActionPublish = "publish";
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
        return newPost;
    }

    @Override
    public Post saveCommentFromDto(CommentDto commentDto, int postId, Principal principal) throws ParseException {
        Post relevantPost = postService.findPostById(postId).get();
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setCreated_at(postService.setDateToday());
        comment.setAuthor(userService.findUserByUserName(principal.getName()).get());
        comment.setPost(relevantPost);
        commentService.saveComment(comment);
        return relevantPost;
    }



}
