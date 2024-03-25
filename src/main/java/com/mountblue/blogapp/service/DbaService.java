package com.mountblue.blogapp.service;

import com.mountblue.blogapp.exception.IdNotFoundException;
import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.CommentDto;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.PostDto;

import java.security.Principal;
import java.text.ParseException;

public interface DbaService {
    Post updatePost(int postId, PostDto postDto) throws IdNotFoundException, ParseException;

    Comment udpateComment(int commentId, CommentDto commentDto) throws IdNotFoundException, ParseException;

    Post savePostFromDto(PostDto newPostDto, String blogStatusAction, Principal principal) throws ParseException;

    Post saveCommentFromDto(CommentDto commentDto, int postId, Principal principal) throws ParseException;
}
