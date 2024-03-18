package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.CommentDao;
import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentManager implements CommentService{
    private final CommentDao commentDao;

    public CommentManager(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
    @Override
    public void saveComment(Comment comment){
        commentDao.save(comment);
    }

    @Override
    public List<Comment> findAllComments() {
        return commentDao.findAll();
    }

    @Override
    public Optional<Comment> findCommentById(int id) {
        return commentDao.findById(id);
    }

    @Override
    public void deleteComment(Comment comment){
        commentDao.delete(comment);
    }

    @Override
    public List<Comment> findAllCommentsOfPost(Post post) {
        return commentDao.findAllByPost(post);
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
