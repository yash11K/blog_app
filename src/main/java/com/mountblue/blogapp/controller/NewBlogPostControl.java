package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.User;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;

import static com.mountblue.blogapp.service.PostService.createExcerpt;

@Controller
@RequestMapping("/home")
public class NewBlogPostControl extends AbstractBlogControl{
    private final ServiceFactory serviceFactory;
    private PostService postService;
    private TagService tagService;
    private UserService userService;
    private final String blogActionPublish = "Publish";
    @Autowired
    public NewBlogPostControl(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @ModelAttribute
    public void initService(){
        this.postService = serviceFactory.getPostService();
        this.tagService = serviceFactory.getTagService();
        this.userService = serviceFactory.getUserService();
    }

    @GetMapping("/blog-new")
    public String showHome(Model model){
        String tags = "";
        model.addAttribute("tags", tags);
        model.addAttribute("newPost", new Post());
        model.addAttribute("isTagMade", false);
        return "newBlogPost";
    }

    @PostMapping("/blog-publish")
    public String publishBlog(@ModelAttribute("newPost") Post newPost,
                              @RequestParam(value = "newPostTagNames")String newPostTagNamesStr,
                              @RequestParam("blogAction")String blogAction, Principal principal,
                              BindingResult bindingResult) throws ParseException {
        if(bindingResult.hasErrors()){
            return "newBlogPost";
        }
        User author = userService.findUserByUserName(principal.getName()).get();
        newPost.setAuthor(author);
        newPost.setCreatedAt(postService.setDateToday());
        newPost.setPublishedAt(postService.setDateToday());
        tagService.saveTagFromTagString(newPostTagNamesStr, newPost);
        newPost.setPublished(blogAction.equals(blogActionPublish));
        newPost.setExcerpt(createExcerpt(newPost.getContent()));
        postService.savePost(newPost);
        return "redirect:/home/blog-new";
    }
}
