package com.mountblue.blogapp.controller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;

import static com.mountblue.blogapp.service.PostService.createExcerpt;

@Controller
@RequestMapping("/home")
public class BlogOperationController extends AbstractBlogControl{
    private final ServiceFactory serviceFactory;
    private PostService postService;
    private TagService tagService;
    private PostTagService postTagService;

    @Autowired
    public BlogOperationController(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @ModelAttribute
    public void initService(){
        postService = serviceFactory.getPostService();
        tagService = serviceFactory.getTagService();
        postTagService = serviceFactory.getPostTagService();
    }

    @GetMapping("/update")
    String showUpdatePostPage(@RequestParam("postId")Integer updatePostId, Model model){

        Post updatePost = postService.findPostById(updatePostId).get();
        Set<Tag> tags = updatePost.getTags();
        model.addAttribute("updatePost",updatePost);
        model.addAttribute("tagsStr", tagService.getTagNamesAsString(tags));
        return "updateBlogPost";
    }

    @PostMapping("/submit-updates")
    String submitPostUpdates(@ModelAttribute(name = "updatePost")Post updatedPost,
                             @RequestParam("tagsStr")String tagsStr){
        PostService postService = serviceFactory.getPostService();
        PostTagService postTagService = serviceFactory.getPostTagService();
        TagService tagService = serviceFactory.getTagService();

        postTagService.deletePostTagRelationByPostId(updatedPost.getId());
        tagService.saveTagFromTagString(tagsStr, updatedPost);
        updatedPost.setExcerpt(createExcerpt(updatedPost.getContent()));
        updatedPost.setUpdatedAt(new Date());
        updatedPost.setPublished(true);
        postService.savePost(updatedPost);
        return "redirect:/home";
    }

    @GetMapping("/delete")
    String deletePost(@ModelAttribute(name = "updatePost")Post updatedPost, @RequestParam("postId")Integer postId){
        postTagService.deletePostTagRelationByPostId(postId);
        postService.deletePostById(postId);
        return "redirect:/home";
    }

    @GetMapping("/archive")
    String archivePost(@ModelAttribute(name = "archivePost")Post archivePost, @RequestParam("postId")Integer postId){
        Post post = postService.findPostById(postId).get();
        post.setPublished(false);
        postService.savePost(post);
        return "redirect:/home";
    }
}
