package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.dao.UserDao;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchManager implements SearchService{
    private final TagDao tagService;
    private final PostDao postService;
    private final UserDao userService;

    @Autowired
    public SearchManager(TagDao tagService, PostDao postService, UserDao userService) {
        this.tagService = tagService;
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public List<Integer> processSearchQuery(String rawQuery) {
        List<String> splitQuery = List.of(rawQuery.split(" "));
        Set<Post> queryMatchingPosts = new HashSet<>();
        for(String query : splitQuery){
            query = query.trim();
            List<Post> matchingPosts = postService.findPostsByTitleContainingIgnoreCase(query);
            List<Tag> matchingTags  = tagService.findTagByNameContains(query);
            for(Tag matchingTag: matchingTags){
                Set<Post> postsEachTag = matchingTag.getPosts();
                queryMatchingPosts.addAll(postsEachTag);
            }
            for(Post matchingPost : matchingPosts){
                queryMatchingPosts.addAll(matchingPosts);
            }
        }
        List<Integer> postIdsQueryPosts = new ArrayList<>();
        for(Post post : queryMatchingPosts){
            postIdsQueryPosts.add(post.getId());
        }
        return postIdsQueryPosts;
    }
}
