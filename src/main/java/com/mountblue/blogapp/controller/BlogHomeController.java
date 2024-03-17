package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.*;

@Controller
public class BlogHomeController extends AbstractBlogControl{
    @Autowired
    public BlogHomeController(PostService postService,
                              TagService tagService,
                              PostTagService postTagService,
                              CommentService commentService,
                              UserService userService,
                              SearchService searchService,
                              FilterService filterService) {
        super(postService,
                tagService,
                postTagService,
                commentService,
                userService,
                searchService,
                filterService);
    }

    @GetMapping("/home")
    public String showHomePage(Model model,
                               @RequestParam(value = "orderBy", required = false)String orderBy,
                               @RequestParam(value = "rawQuery", required = false)String rawQuery,
                               @RequestParam(value = "tagQuery", required = false)String tagQuery,
                               @RequestParam(value = "from", required = false)String startDate,
                               @RequestParam(value = "to", required = false)String endDate,
                               RedirectAttributes redirectAttributes) throws ParseException {
        List<Post> publishedPosts;
        List<Integer> postIdsQueryPosts;
        List<String> tagQueryCollection = new ArrayList<>();
        if (orderBy==null){
            redirectAttributes.addAttribute("orderBy", "dateDesc");
            return "redirect:/home";
        }
        //to make empty request params as null
        rawQuery = (rawQuery != null && !rawQuery.isEmpty()) ? rawQuery : null;
        tagQuery = (tagQuery != null && !tagQuery.isEmpty()) ? tagQuery : null;
        startDate = (startDate!=null && !startDate.isEmpty()? startDate : null);
        endDate = (endDate!=null && !endDate.isEmpty()? endDate : null);

        if(tagQuery !=null){
            postIdsQueryPosts = filterService.findPostIdByTagNames(tagQuery);
            postIdsQueryPosts.addAll(filterService.findPostIdByAuthorNames(tagQueryCollection));
            model.addAttribute("tagQuery", tagQuery);
            publishedPosts = postService.findPostsBySortType(orderBy, postIdsQueryPosts, true);
        }
        if(startDate!=null || endDate!=null){
            System.out.println("date");
            postIdsQueryPosts = filterService.findPostIdByStartEndDate(startDate, endDate);
            publishedPosts = postService.findPostsBySortType(orderBy, postIdsQueryPosts, true);
        }
        else if (rawQuery != null){
            System.out.println("search");
            postIdsQueryPosts = searchService.processSearchQuery(rawQuery);
            model.addAttribute("query", rawQuery);
            publishedPosts = postService.findPostsBySortType(orderBy, postIdsQueryPosts, true);
        }
        else {
            System.out.println("else");
            postIdsQueryPosts = postService.findIdByPublished(true);
            publishedPosts = postService.findPostsBySortType(orderBy, postIdsQueryPosts, true);
        }
        model.addAttribute("publishedPosts", publishedPosts);
        model.addAttribute("tagQueryCollection", tagQueryCollection);
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("allTags", tagService.findALlTags());
        model.addAttribute("sortType", orderBy);
        return "home";
    }
}
