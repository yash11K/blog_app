package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {

    void saveComment(Comment comment);

    List<Comment> findAllComments();

    Optional<Comment> findCommentById(int id);

    void deleteComment(Comment comment);

    List<Comment> findAllCommentsOfPost(Post post);

    int getPostIdFromCommentId(int id);
}
