package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostManager implements PostService{
    private final PostDao postDao;
    @Autowired
    public PostManager(PostDao postDao) {
        this.postDao = postDao;
    }
    public void savePost(Post post){
        postDao.save(post);
    }

    @Override
    public List<Post> findPostsByPublished(Boolean isPublished) {
        return postDao.findPostByIsPublished(isPublished);
    }

    @Override
    public Post findPostById(int postId) {
        Optional<Post> isPost= postDao.findById(postId);
        return isPost.orElseGet(Post::new);
    }

    @Override
    public void deletePostById(int postId) {
        postDao.deleteById(postId);
    }
    static String createExcerpt(String content){
        int excerptLength = 80;
        return content.substring(0,excerptLength) + "...";
    }

    @Override
    public Page<Post> findOrderedCustomPostsByPublished(List<Integer> postIds, boolean isPublished, boolean order, Pageable pageable) {
        if(order){
            return postDao.findPostByIdInAndIsPublishedOrderByPublishedAtAsc(postIds ,isPublished, pageable);
        }
        else return postDao.findPostByIdInAndIsPublishedOrderByPublishedAtDesc(postIds ,isPublished, pageable);
    }

    @Override
    public Page<Post> findOrderedCustomPostsByTitle(List<Integer> postIds, boolean isPublished,boolean order, Pageable pageable) {
        if(order){
            return postDao.findPostByIdInAndIsPublishedOrderByTitleAsc(postIds, isPublished, pageable);
        }
        else return postDao.findPostByIdInAndIsPublishedOrderByTitleDesc(postIds, isPublished, pageable);
    }

    @Override
    public List<Integer> findIdByPublished(boolean isPublished) {
        return postDao.findAllIdsByIsPublished(true);
    }

    @Override
    public List<Post> findPostByTitlePattern(String titlePattern) {
        return postDao.findPostsByTitleContainingIgnoreCase(titlePattern);
    }

    @Override
    public List<Post> findPostByContentPattern(String contentPattern) {
        return postDao.findPostsByContentContaining(contentPattern);
    }

    @Override
    public Page<Post> findPostsBySortType(String sortType, List<Integer> postIds, boolean isPublished, Pageable pageable){
        return switch (sortType) {
            default -> findOrderedCustomPostsByPublished(postIds, isPublished, false, pageable) ;
            case "dateAsc" -> {
                yield findOrderedCustomPostsByPublished(postIds, isPublished, true, pageable);
            }
            case "dateDesc" -> {
                yield findOrderedCustomPostsByPublished(postIds, isPublished, false, pageable);
            }
            case "nameAsc" -> {
                yield findOrderedCustomPostsByTitle(postIds, isPublished, true, pageable);
            }
            case "nameDesc" -> {
                yield findOrderedCustomPostsByTitle(postIds, isPublished, false, pageable);
            }
        };
    }

    @Override
    public Date setDateToday() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date today = new Date();
        return  formatter.parse(formatter.format(today));
    }
}
