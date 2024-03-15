package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchOperationController extends AbstractBlogControl{
    public SearchOperationController(PostService postService, TagService tagService, PostTagService postTagService, CommentService commentService, UserService userService) {
        super(postService, tagService, postTagService, commentService, userService);
    }

    @GetMapping("/search")
    public String processSearchQuery(@RequestParam("query")String rawQuery, Model model){
        List<String> splitQuery = List.of(rawQuery.split(" "));
        Set<Post> queryMatchingPosts = new HashSet<>();
        for(String query : splitQuery){
            query = query.trim();
            List<Tag> matchingTags  = tagService.findTagByNamePattern(query);
            for(Tag matchingTag: matchingTags){
                Set<Post> postsEachTag = matchingTag.getPosts();
                queryMatchingPosts.addAll(postsEachTag);
            }

//            List<Post> publishedPosts;
//            publishedPosts = postService.getPostsBySortType(orderBy, true);
//            model.addAttribute("sortType", orderBy);
//            model.addAttribute("publishedPosts", publishedPosts);
//            return "home";
        }
        model.addAttribute("publishedPosts", queryMatchingPosts);
        model.addAttribute("sortType", "default");
        return "redirect:/home";
    }
}
