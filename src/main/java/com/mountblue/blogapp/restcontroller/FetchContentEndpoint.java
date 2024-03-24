package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/writewise/api/post")
public class FetchContentEndpoint {
    private final ServiceFactory serviceFactory;
    private final PostAssembler postAssembler;
    private PostService postService;
    private SearchService searchService;
    private FilterService filterService;
    private UserService userService;
    private TagService tagService;

    private final String blogActionPublish = "publish";

    @Autowired
    public FetchContentEndpoint(ServiceFactory serviceFactory, PostAssembler postAssembler) {
        this.serviceFactory = serviceFactory;
        this.postAssembler = postAssembler;
    }

    @PostConstruct
    public void initService(){
        this.postService = serviceFactory.getPostService();
        this.searchService = serviceFactory.getSearchService();
        this.filterService = serviceFactory.getFilterService();
        this.userService = serviceFactory.getUserService();
        this.tagService = serviceFactory.getTagService();
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<Post>> fetchAllPosts(){
        List<EntityModel<Post>> posts = postService.findAllPosts()
                .stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class).fetchAllPosts()).withSelfRel());
    }

    @GetMapping("/{postId}")
    public EntityModel<Post> fetchPost(@PathVariable int postId){
        Post post = postService.findPostById(postId).get();
        return postAssembler.toModel(post);
    }

    @GetMapping("/all/{isPublished}")
    public CollectionModel<EntityModel<Post>> fetchAllPostsByIsPublished(@PathVariable("isPublished")boolean isPublished){
        List<EntityModel<Post>> posts = postService.findPostsByPublished(isPublished).
                stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return  CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsByIsPublished(isPublished)).withSelfRel());
    }
    @GetMapping("/all/order/{sort}")
    public CollectionModel<EntityModel<Post>> fetchAllPostsBySortType(@PathVariable String sort){
        List<EntityModel<Post>> posts = postService.findPostsBySortType
                        (sort,postService.findAllIds())
                .stream()
                .map(
                        postAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsBySortType(sort)).withSelfRel());
    }

    @GetMapping("/all/search")
    public CollectionModel<EntityModel<Post>> fetchAllPostsBySearch(@RequestParam String rawQuery,
                                                                    @RequestParam(value = "sortType", defaultValue = "dateDesc")String sortType){
        List<EntityModel<Post>> posts = postService.findPostsBySortType( sortType, searchService.processSearchQuery(rawQuery))
                .stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsBySearch(rawQuery, sortType)).withSelfRel());
    }

    @GetMapping("/all/filter/tags")
    public CollectionModel<EntityModel<Post>> fetchAllPostsByTagFilter(@RequestParam String tagFilterQuery,
                                                                       @RequestParam(defaultValue = "dateDesc") String sortType){
        List<EntityModel<Post>> posts = postService.findPostsBySortType(sortType, filterService.findPostIdByTagNames(tagFilterQuery))
                .stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsByTagFilter(tagFilterQuery, sortType)).withSelfRel());
    }

    @GetMapping("/all/filter/author")
    public CollectionModel<EntityModel<Post>> fetchAllPostsByAuthorFilter(@RequestParam String authorFilterQuery,
                                                                          @RequestParam(defaultValue = "dateDesc") String sortType){
        List<EntityModel<Post>> posts = postService.findPostsBySortType(sortType, filterService.findPostIdByAuthorNames(authorFilterQuery))
                .stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsByAuthorFilter(authorFilterQuery, sortType)).withSelfRel());
    }
    @GetMapping("/all/filter/dates")
    public CollectionModel<EntityModel<Post>> fetchAllPostsByAuthorFilter(@RequestParam String fromDate,
                                                                          @RequestParam String toDate,
                                                                          @RequestParam(defaultValue = "dateDesc") String sortType){
        try{
            List<EntityModel<Post>> posts = postService.findPostsBySortType(sortType, filterService.findPostIdByStartEndDate(fromDate, toDate))
                    .stream()
                    .map(
                            postAssembler::toModel
                    ).collect(Collectors.toList());
            return CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsByAuthorFilter(fromDate, toDate, sortType)).withSelfRel());
        }catch (ParseException dateParseException){
            System.out.println("Create Custom Exception with message -  invalid date");
        }
        return null;
    }

    @GetMapping("/all/query")
    public CollectionModel<EntityModel<Post>> fetchAllPostsByQuery(
            @RequestParam(required = false) String rawQuery,
            @RequestParam(required = false) String tagFilterQuery,
            @RequestParam(required = false) String authorFilterQuery,
            @RequestParam(required = false) String fromDateFilterQuery,
            @RequestParam(required = false) String toDateFilterQuery,
            @RequestParam(defaultValue = "dateDesc") String sortType,
            @RequestParam(defaultValue = "true")Boolean isPublished

    ) throws ParseException {
        Collection<Integer> postIdCollector = postService.findAllIds();
        postIdCollector.retainAll(filterService.findPostIdFromCustomFilterQuery(postIdCollector,
                tagFilterQuery,
                authorFilterQuery,
                fromDateFilterQuery,
                toDateFilterQuery));
        postIdCollector.retainAll(searchService.processSearchQuery(rawQuery));
        List<EntityModel<Post>> posts = postService.findPostsBySortType(sortType,postIdCollector,isPublished)
                .stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class)
                .fetchAllPostsByQuery(rawQuery, tagFilterQuery, authorFilterQuery, fromDateFilterQuery, toDateFilterQuery, sortType, isPublished
        )).withSelfRel());
    }

}
