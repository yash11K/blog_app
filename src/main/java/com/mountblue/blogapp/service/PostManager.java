package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostManager implements PostService{
    private final PostDao postDao;
    private final TagService tagService;

    @Autowired
    public PostManager(PostDao postDao, TagService tagService) {
        this.postDao = postDao;
        this.tagService = tagService;
    }

    public void savePost(Post post){
        postDao.save(post);
    }

    @Override
    public List<Post> findPostsByPublished(Boolean isPublished) {
        return postDao.findPostByIsPublished(isPublished);
    }

    @Override
    public Optional<Post> findPostById(int postId) {
        Optional<Post> isPost= postDao.findById(postId);
        return isPost;
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
    public Page<Post> findOrderedCustomPostsByPublishedAt(Collection<Integer> postIds, boolean isPublished, boolean order, Pageable pageable) {
        if(order){
            return postDao.findPostByIdInAndIsPublishedOrderByPublishedAtAsc(postIds ,isPublished, pageable);
        }
        else return postDao.findPostByIdInAndIsPublishedOrderByPublishedAtDesc(postIds ,isPublished, pageable);
    }
    @Override
    public Page<Post> findOrderedCustomPostsByPublishedAt(Collection<Integer> postIds, boolean order, Pageable pageable) {
        if(order){
            return postDao.findPostByIdInOrderByPublishedAtAsc(postIds ,  pageable);
        }
        else return postDao.findPostByIdInOrderByPublishedAtDesc(postIds , pageable);
    }

    @Override
    public List<Post> findOrderedCustomPostsByPublishedAt(Collection<Integer> postIds, boolean isPublished, boolean order) {
        if(order){
            return postDao.findPostByIdInAndIsPublishedOrderByPublishedAtAsc(postIds ,isPublished);
        }
        else return postDao.findPostByIdInAndIsPublishedOrderByPublishedAtDesc(postIds ,isPublished);
    }

    @Override
    public List<Post> findOrderedCustomPostsByPublishedAt(Collection<Integer> postIds, boolean order) {
        if(order){
            return postDao.findPostsByIdInOrderByPublishedAtAsc(postIds);
        }
        else return postDao.findPostsByIdInOrderByPublishedAtDesc(postIds);
    }


    @Override
    public Page<Post> findOrderedCustomPostsByTitle(Collection<Integer> postIds, boolean isPublished,boolean order, Pageable pageable) {
        if(order){
            return postDao.findPostByIdInAndIsPublishedOrderByTitleAsc(postIds, isPublished, pageable);
        }
        else return postDao.findPostByIdInAndIsPublishedOrderByTitleDesc(postIds, isPublished, pageable);
    }

    @Override
    public List<Post> findOrderedCustomPostsByTitle(Collection<Integer> postIds, boolean isPublished,boolean order) {
        if(order){
            return postDao.findPostByIdInAndIsPublishedOrderByTitleAsc(postIds, isPublished);
        }
        else return postDao.findPostByIdInAndIsPublishedOrderByTitleDesc(postIds, isPublished);
    }

    @Override
    public Page<Post> findOrderedCustomPostsByTitle(Collection<Integer> postIds, Pageable pageable, boolean order) {
        if(order){
            return postDao.findPostByIdInOrderByTitleAsc(postIds, pageable);
        }
        else return postDao.findPostByIdInOrderByTitleDesc(postIds, pageable);
    }

    @Override
    public List<Post> findOrderedCustomPostsByTitle(Collection<Integer> postIds, boolean order) {
        if(order){
            return postDao.findPostsByIdInOrderByTitleAsc(postIds);
        }
        else return postDao.findPostsByIdInOrderByTitleDesc(postIds);
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
    public Page<Post> findPostsBySortType(String sortType, Collection<Integer> postIds, boolean isPublished, Pageable pageable){
        return switch (sortType) {
            default -> findOrderedCustomPostsByPublishedAt(postIds, isPublished, false, pageable) ;
            case "dateAsc" -> {
                yield findOrderedCustomPostsByPublishedAt(postIds, isPublished, true, pageable);
            }
            case "dateDesc" -> {
                yield findOrderedCustomPostsByPublishedAt(postIds, isPublished, false, pageable);
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
    public Page<Post> findPostsBySortType(String sortType, Collection<Integer> postIds, Pageable pageable){
        return switch (sortType) {
            default -> findOrderedCustomPostsByPublishedAt(postIds, false, pageable) ;
            case "dateAsc" -> {
                yield findOrderedCustomPostsByPublishedAt(postIds,true, pageable);
            }
            case "dateDesc" -> {
                yield findOrderedCustomPostsByPublishedAt(postIds,false, pageable);
            }
            case "nameAsc" -> {
                yield findOrderedCustomPostsByTitle(postIds, pageable, true);
            }
            case "nameDesc" -> {
                yield findOrderedCustomPostsByTitle(postIds, pageable, false);
            }
        };
    }


    @Override
    public List<Post> findPostsBySortType(String sortType, Collection<Integer> postIds, boolean isPublished){
        return switch (sortType) {
            default -> findOrderedCustomPostsByPublishedAt(postIds, isPublished, false) ;
            case "dateAsc" -> {
                yield findOrderedCustomPostsByPublishedAt(postIds, isPublished, true);
            }
            case "dateDesc" -> {
                yield findOrderedCustomPostsByPublishedAt(postIds, isPublished, false);
            }
            case "nameAsc" -> {
                yield findOrderedCustomPostsByTitle(postIds, isPublished, true);
            }
            case "nameDesc" -> {
                yield findOrderedCustomPostsByTitle(postIds, isPublished, false);
            }
        };
    }
    @Override
    public List<Post> findPostsBySortType(String sortType, Collection<Integer> postIds){
        return switch (sortType) {
            default -> findOrderedCustomPostsByPublishedAt(postIds, false) ;
            case "dateAsc" -> {
                yield findOrderedCustomPostsByPublishedAt(postIds, true);
            }
            case "dateDesc" -> {
                yield findOrderedCustomPostsByPublishedAt(postIds, false);
            }
            case "nameAsc" -> {
                yield findOrderedCustomPostsByTitle(postIds, true);
            }
            case "nameDesc" -> {
                yield findOrderedCustomPostsByTitle(postIds, false);
            }
        };
    }


    @Override
    public List<Post> findPostsCreatedByAuthorByPublished(Boolean isPublished, User author) {
        return postDao.findPostsByAuthorAndIsPublished(author, isPublished);
    }

    @Override
    public Date setDateToday() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date today = new Date();
        return  formatter.parse(formatter.format(today));
    }

    @Override
    public List<Post> findPostsOrderBy(String orderBy) {
        return postDao.findPostsOrderBy(orderBy);
    }

    @Override
    public Collection<Integer> findAllIds(){
        return postDao.findAllIds();
    }

    @Override
    public List<Post> findAllPosts(){
        return postDao.findAll();
    }

}
