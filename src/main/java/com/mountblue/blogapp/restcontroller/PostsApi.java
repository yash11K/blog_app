package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.PostAssembler;
import com.mountblue.blogapp.service.PostService;
import com.mountblue.blogapp.service.SearchService;
import com.mountblue.blogapp.service.ServiceFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/writewise/api")
public class PostsApi {
    private final ServiceFactory serviceFactory;
    private final PostAssembler postAssembler;
    private PostService postService;
    private SearchService searchService;
    @Autowired
    public PostsApi(ServiceFactory serviceFactory, PostAssembler postAssembler) {
        this.serviceFactory = serviceFactory;
        this.postAssembler = postAssembler;
    }

    @PostConstruct
    public void initService(){
        this.postService = serviceFactory.getPostService();
        this.searchService = serviceFactory.getSearchService();
    }

    @GetMapping("/allposts")
    public CollectionModel<EntityModel<Post>> fetchAllPosts(){
        List<EntityModel<Post>> posts = postService.findAllPosts()
                .stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(PostsApi.class).fetchAllPosts()).withSelfRel());
    }

    @GetMapping("/post/{postId}")
    public EntityModel<Post> fetchPost(@PathVariable int postId){
        Post post = postService.findPostById(postId);
        return postAssembler.toModel(post);
    }

    @GetMapping("/allposts/{isPublished}")
    public CollectionModel<EntityModel<Post>> fetchAllPostsByIsPublished(@PathVariable("isPublished")boolean isPublished){
        List<EntityModel<Post>> posts = postService.findPostsByPublished(isPublished).
                stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return  CollectionModel.of(posts, linkTo(methodOn(PostsApi.class).fetchAllPostsByIsPublished(isPublished)).withSelfRel());
    }
}
