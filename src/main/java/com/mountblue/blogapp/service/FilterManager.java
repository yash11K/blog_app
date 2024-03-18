package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.dao.PostTagDao;
import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.dao.UserDao;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.model.User;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FilterManager  implements FilterService{
    PostDao postService;
    TagDao tagService;
    UserDao userService;
    PostTagDao postTagService;

    public FilterManager(PostDao postService, TagDao tagService, UserDao userService, PostTagDao postTagService) {
        this.postService = postService;
        this.tagService = tagService;
        this.userService = userService;
        this.postTagService = postTagService;
    }

    @Override
    public Set<Integer> findPostIdByTagNames(String tagNames){
        Set<Integer> postIds = new HashSet<>();
        List<Tag> tags  = tagService.findTagByNameIn(List.of(tagNames.split(",")));
        List<Post> posts = postService.findPostsByTagsIn(tags);
        for(Post post : posts){
            postIds.add(post.getId());
        }
        return postIds;
    }

    @Override
    public List<Integer> findPostIdByAuthorNames(String authorNames) {
        List<Integer> postIds = new ArrayList<>();
        List<User> users = userService.findUserByNameIn(List.of(authorNames.split(",")));
        List<Post> posts = postService.findPostsByAuthorIn(users);
        for(Post post : posts){
            postIds.add(post.getId());
        }
        return postIds;
    }

    @Override
    public List<Integer> findPostIdByStartEndDate(String startDate, String endDate) throws ParseException {
        DateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd");
        Date from = stringToDate.parse(startDate);
        Date end = stringToDate.parse(endDate);
        return postService.findPostIdsByPublishedAtBetween(from, end);
    }

    @Override
    public List<Tag> findTagsByPostIds(List<Integer> postIds) {
        return tagService.findTagByIdIn(postTagService.findTagIdsByPostIds(postIds));
    }
}
