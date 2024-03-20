package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.dao.UserDao;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchManager implements SearchService{
    private final TagDao tagDao;
    private final PostDao postDao;
    private final UserDao userDao;

    @Autowired
    public SearchManager(TagDao tagDao, PostDao postDao, UserDao userDao) {
        this.tagDao = tagDao;
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @Override
    public List<Integer> processSearchQuery(String rawQuery) {
        List<String> splitQuery = List.of(rawQuery.split(" "));
        Set<Post> queryMatchingPosts = new HashSet<>();
        for(String query : splitQuery){
            query = query.trim();
            List<Post> matchingPosts = postDao.findPostsByTitleContainingIgnoreCase(query);
            List<Tag> matchingTags  = tagDao.findTagByNameContains(query);
            List<User> matchingUsers =  userDao.findUserByNameLike(query);

            matchingPosts.addAll(postDao.findPostsByContentContaining(query));
            for(Tag matchingTag: matchingTags){
                Collection<Post> postsEachTag = matchingTag.getPosts();
                queryMatchingPosts.addAll(postsEachTag);
            }
            for(Post matchingPost : matchingPosts){
                queryMatchingPosts.addAll(matchingPosts);
            }
            for(User matchingUser : matchingUsers){
                Set<Post> postsEachUser = matchingUser.getPosts();
                queryMatchingPosts.addAll(postsEachUser);
            }
        }
        List<Integer> postIdsQueryPosts = new ArrayList<>();
        for(Post post : queryMatchingPosts){
            postIdsQueryPosts.add(post.getId());
        }
        return postIdsQueryPosts;
    }
}
