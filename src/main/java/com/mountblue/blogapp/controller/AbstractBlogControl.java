package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Comment;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
public abstract class AbstractBlogControl {
    static void addModelAttributeOfFullBlog(Model model, Post post, List<Comment> allCommentsOfPost) {
        Set<Tag> tags = post.getTags();
        Comment comment = new Comment();
        model.addAttribute("post", post);
        model.addAttribute("postedComments", allCommentsOfPost);
        model.addAttribute("postTags", tags);
        model.addAttribute("comments", comment);
    }

    static void addRedirectAttribute(String name, String attribute, RedirectAttributes redirectAttributes){
        if(attribute!=null && !attribute.isEmpty()){
            redirectAttributes.addAttribute(name, attribute);
        }
    }
}