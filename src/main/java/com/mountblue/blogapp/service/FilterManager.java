package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilterManager  implements FilterService{
    PostDao postService;
    TagDao tagService;

    public FilterManager(PostDao postService, TagDao tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @Override
    public List<Integer> findPostIdByTagNames(List<String> tagNames){
        List<Integer> postIds = new ArrayList<>();
        List<Tag> tags  = tagService.findTagByNameIn(tagNames);
        List<Post> posts = postService.findPostsByTagsIn(tags);
        for(Post post : posts){
            postIds.add(post.getId());
        }
        return postIds;
    }

    @Override
    public List<Integer> findPostIdByAuthorNames(List<String> authorNames) {
        List<Integer> postIds = new ArrayList<>();
        List<User> users = userService.findUserByNameIn(authorNames);
        List<Post> posts = postService.findPostsByAuthorIn(users);
        for(Post post : posts){
            postIds.add(post.getId());
        }
        return postIds;
    }

}
