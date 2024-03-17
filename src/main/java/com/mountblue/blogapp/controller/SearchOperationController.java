package com.mountblue.blogapp.controller;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class SearchOperationController extends AbstractBlogControl{
    public SearchOperationController(PostService postService,
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

    @GetMapping("/orderBy")
    public String showHomePageWithOrderedPost(@RequestParam("orderBy")String orderBy,
                                              @RequestParam(value = "rawQuery", required = false)String rawQuery,
                                              @RequestParam(value = "tagQuery", required = false)String tagQuery,
                                              @RequestParam(value = "startDate", required = false)String startDate,
                                              @RequestParam(value = "endDate", required = false)String endDate,
                                              RedirectAttributes redirectAttributes){
        if(rawQuery!=null && !rawQuery.isEmpty()){
            redirectAttributes.addAttribute("rawQuery",rawQuery);
        }
        if(tagQuery !=null && !tagQuery.isEmpty()){
            redirectAttributes.addAttribute("tagQuery", tagQuery);
        }
        if(startDate!=null && !startDate.isEmpty()){
            redirectAttributes.addAttribute("from", startDate);
        }
        if(endDate!=null && !endDate.isEmpty()){
            redirectAttributes.addAttribute("to", endDate);
        }
        redirectAttributes.addAttribute("orderBy",orderBy);

        return "redirect:/home";
    }

    @GetMapping("/search")
    public String processSearchQuery(@RequestParam("query")String rawQuery,
                                     RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("rawQuery",rawQuery);
        redirectAttributes.addAttribute("orderBy", "dateDesc");
        return "redirect:/home";
    }

    @GetMapping("/filter")
    public String processFilterQuery(@RequestParam(value = "tagQuery", required = false)String tagQuery,
                                     @RequestParam(value = "startDate", required = false)String startDate,
                                     @RequestParam(value = "endDate", required = false)String endDate,
                                     RedirectAttributes redirectAttributes){

        if(startDate!=null && !startDate.isEmpty()){
            redirectAttributes.addAttribute("from", startDate);
        }
        if(endDate!=null && !endDate.isEmpty()){
            redirectAttributes.addAttribute("to", endDate);
        }

        if(tagQuery != null && !tagQuery.isEmpty()){
            redirectAttributes.addAttribute("tagQuery", tagQuery);
        }
        redirectAttributes.addAttribute("orderBy", "dateDesc");
        return "redirect:/home";
    }
}
