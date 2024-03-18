package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                               @RequestParam(value = "page", required = false, defaultValue = "0")Integer page,
                               RedirectAttributes redirectAttributes) throws ParseException {
        boolean processRawQuery = false;
        boolean processTagQuery = false;
        boolean processDateQuery = false;
        int pageSize = 6;

        Page<Post> publishedPosts;
        List<Integer> postIdsQueryPosts = new ArrayList<>();
        Set<Integer> postIdsCollector = new HashSet<>();
        if (orderBy==null){
            redirectAttributes.addAttribute("orderBy", "dateDesc");
            return "redirect:/home";
        }

        rawQuery = (rawQuery != null && !rawQuery.isEmpty()) ? rawQuery : null;
        tagQuery = (tagQuery != null && !tagQuery.isEmpty()) ? tagQuery : null;
        startDate = (startDate!=null && !startDate.isEmpty()? startDate : null);
        endDate = (endDate!=null && !endDate.isEmpty()? endDate : null);
        if (rawQuery != null) {
            processRawQuery = postIdsCollector.addAll(searchService.processSearchQuery(rawQuery));
            model.addAttribute("rawQuery", rawQuery);
        }
        if(tagQuery !=null){
            model.addAttribute("tagQuery", tagQuery);
            if(processRawQuery){
                Set<Integer> tagQueryResults = filterService.findPostIdByTagNames(tagQuery);
                postIdsCollector.retainAll(tagQueryResults);
            } else {
                processTagQuery = postIdsCollector.addAll(filterService.findPostIdByTagNames(tagQuery));
            }
        }
        if(startDate!=null || endDate!=null){
            model.addAttribute("from", startDate);
            model.addAttribute("to", endDate);
            if(processRawQuery || processTagQuery){
                List<Integer> dateQueryResults = filterService.findPostIdByStartEndDate(startDate, endDate);
                postIdsCollector.retainAll(dateQueryResults);
            }
            else {
                processDateQuery = postIdsCollector.addAll(filterService.findPostIdByStartEndDate(startDate, endDate));
            }
        }
        if(!processRawQuery && !processTagQuery && !processDateQuery){
            boolean b = postIdsCollector.addAll(postService.findIdByPublished(true));
        }
        boolean b = postIdsQueryPosts.addAll(postIdsCollector);
        List<Tag> relativeTags = filterService.findTagsByPostIds(postIdsQueryPosts);
        Pageable pageable = PageRequest.of(page, pageSize);
        publishedPosts = postService.findPostsBySortType(orderBy, postIdsQueryPosts, true, pageable);
        model.addAttribute("publishedPosts", publishedPosts);
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("allTags", relativeTags);
        model.addAttribute("sortType", orderBy);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", publishedPosts.getTotalPages());
        return "home";
    }
}
