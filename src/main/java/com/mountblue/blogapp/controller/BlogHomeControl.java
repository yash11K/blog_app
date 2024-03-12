package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class BlogHomeControl {
    @Value("${Tag.Topics}")
    List<String> TagTopics;
    @GetMapping("/")
    public String showHome(Model model){
        model.addAttribute("tagTopics",TagTopics);
        model.addAttribute("tagAllot", new Tag());
        model.addAttribute("newPost", new Post());
        return "home";
    }
}
