package com.mountblue.blogapp.controller;
import com.mountblue.blogapp.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showHomePageWithOrderedPost(@RequestParam("orderBy")String orderBy, @RequestParam(value = "rawQuery", required = false)String rawQuery, RedirectAttributes redirectAttributes){
        if(rawQuery!=null) {
            redirectAttributes.addAttribute("rawQuery",rawQuery);
        }
        redirectAttributes.addAttribute("orderBy",orderBy);

        return "redirect:/home";
    }

    @GetMapping("/search")
    public String processSearchQuery(@RequestParam("query")String rawQuery, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("rawQuery",rawQuery);
        redirectAttributes.addAttribute("orderBy", "dateDesc");
        return "redirect:/home";
    }
}
