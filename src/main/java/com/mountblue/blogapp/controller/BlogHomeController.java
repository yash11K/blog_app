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
        boolean processRawQuery = false;
        boolean processTagQuery = false;
        List<Post> publishedPosts;
        List<Integer> postIdsQueryPosts = new ArrayList<>();
        List<String> tagQueryCollection = new ArrayList<>();
        Set<Integer> postIdsCollector = new HashSet<>();
        if (orderBy==null){
            redirectAttributes.addAttribute("orderBy", "dateDesc");
            return "redirect:/home";
        }
        //to make empty request params as null
        rawQuery = (rawQuery != null && !rawQuery.isEmpty()) ? rawQuery : null;
        tagQuery = (tagQuery != null && !tagQuery.isEmpty()) ? tagQuery : null;
        startDate = (startDate!=null && !startDate.isEmpty()? startDate : null);
        endDate = (endDate!=null && !endDate.isEmpty()? endDate : null);

        if (rawQuery != null) {
            System.out.println("search");
            processRawQuery = postIdsCollector.addAll(searchService.processSearchQuery(rawQuery));
            model.addAttribute("rawQuery", rawQuery);
        }
        if(tagQuery !=null){
            System.out.println("tag Query");
            model.addAttribute("tagQuery", tagQuery);
            if(processRawQuery){
                List<Integer> tagQueryResults = filterService.findPostIdByTagNames(tagQuery);
                for(int postId : tagQueryResults){
                    if(!postIdsCollector.contains(postId)){
                        postIdsCollector.remove(postId);
                    }
                }
            }
            else {
                processTagQuery = postIdsCollector.addAll(filterService.findPostIdByTagNames(tagQuery));
            }
        }
//        if(startDate!=null || endDate!=null){
//            System.out.println("date");
//            postIdsQueryPosts = filterService.findPostIdByStartEndDate(startDate, endDate);
//            publishedPosts = postService.findPostsBySortType(orderBy, postIdsQueryPosts, true);
//        }
        if(!processRawQuery && !processTagQuery){
            System.out.println("else");
            boolean b = postIdsCollector.addAll(postService.findIdByPublished(true));
        }
        boolean b = postIdsQueryPosts.addAll(postIdsCollector);
        publishedPosts = postService.findPostsBySortType(orderBy, postIdsQueryPosts, true);
        model.addAttribute("publishedPosts", publishedPosts);
        model.addAttribute("tagQueryCollection", tagQueryCollection);
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("allTags", tagService.findALlTags());
        model.addAttribute("sortType", orderBy);
        return "home";
    }
}
