package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.CommentDao;
import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentManager implements CommentService{
    private final CommentDao commentService;

    public CommentManager(CommentDao commentService) {
        this.commentService = commentService;
    }
    @Override
    public void saveComment(Comment comment){
        commentService.save(comment);
    }

    @Override
    public List<Comment> findAllComments() {
        return commentService.findAll();
    }

    @Override
    public Optional<Comment> findCommentById(int id) {
        return commentService.findById(id);
    }

    @Override
    public void deleteComment(Comment comment){
        commentService.delete(comment);
    }

    @Override
    public List<Comment> findAllCommentsOfPost(Post post) {
        return commentService.findAllByPost(post);
    }

    @Override
    public int getPostIdFromCommentId(int id) {
        Optional<Comment> maybeComment = findCommentById(id);
        int postId = 0;
        if(maybeComment.isPresent()){
            Comment comment = maybeComment.get();
            postId = comment.getPost().getId();
        }
        return postId;
    }
}
