package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.exception.IdNotFoundException;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
        Optional<Post> maybePost = postService.findPostById(postId);
        if(maybePost.isPresent()){
            return postAssembler.toModel(maybePost.get());
        }
        else throw new IdNotFoundException(Integer.toString(postId));
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

    @GetMapping("/all/paged/{isPublished}")
    public PagedModel<EntityModel<Post>> fetchAllPostsByIsPublished(@PathVariable("isPublished") boolean isPublished, Pageable pageable) {
        Page<Post> postPage = postService.findPostsBySortType("default", postService.findAllIds(), isPublished, pageable);
        List<EntityModel<Post>> posts = postPage.getContent().stream()
                .map(postAssembler::toModel)
                .collect(Collectors.toList());
        return PagedModel.of(posts,
                postAssembler.getPageMetaData(postPage),
                linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsByIsPublished(isPublished, pageable)).withSelfRel());
    }

    @GetMapping("/all/order/{sort}")
    public PagedModel<EntityModel<Post>> fetchAllPostsBySortType(@PathVariable String sort, Pageable pageable){
        Page<Post> postPage = postService.findPostsBySortType(sort,postService.findAllIds(), pageable);
        List<EntityModel<Post>> posts = postPage.getContent()
                .stream()
                .map(
                        postAssembler::toModel).collect(Collectors.toList());
        return PagedModel.of(posts,
                postAssembler.getPageMetaData(postPage),
                linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsBySortType(sort, pageable)).withSelfRel());
    }

    @GetMapping("/all/search")
    public PagedModel<EntityModel<Post>> fetchAllPostsBySearch(@RequestParam String rawQuery,
                                                               @RequestParam(value = "sortType", defaultValue = "dateDesc")String sortType,
                                                               Pageable pageable){
        Page<Post> postPage = postService.findPostsBySortType( sortType, searchService.processSearchQuery(rawQuery), pageable);
        List<EntityModel<Post>> posts = postPage.getContent()
                .stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return PagedModel.of(posts,
                postAssembler.getPageMetaData(postPage),
                linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsBySearch(rawQuery, sortType, pageable)).withSelfRel());
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
                                                                          @RequestParam(defaultValue = "dateDesc") String sortType) throws ParseException {
        List<EntityModel<Post>> posts = postService.findPostsBySortType(sortType, filterService.findPostIdByStartEndDate(fromDate, toDate))
                    .stream()
                    .map(
                            postAssembler::toModel
                    ).collect(Collectors.toList());
        return CollectionModel.of(posts, linkTo(methodOn(FetchContentEndpoint.class).fetchAllPostsByAuthorFilter(fromDate, toDate, sortType)).withSelfRel());
    }

    @GetMapping("/all/query")
    public PagedModel<EntityModel<Post>> fetchAllPostsByQuery(
            @RequestParam(required = false) String rawQuery,
            @RequestParam(required = false) String tagFilterQuery,
            @RequestParam(required = false) String authorFilterQuery,
            @RequestParam(required = false) String fromDateFilterQuery,
            @RequestParam(required = false) String toDateFilterQuery,
            @RequestParam(defaultValue = "dateDesc") String sortType,
            @RequestParam(defaultValue = "true")Boolean isPublished,
            Pageable pageable

    ) throws ParseException {
        Collection<Integer> postIdCollector = postService.findAllIds();
        postIdCollector.retainAll(filterService.findPostIdFromCustomFilterQuery(postIdCollector,
                tagFilterQuery,
                authorFilterQuery,
                fromDateFilterQuery,
                toDateFilterQuery));
        postIdCollector.retainAll(searchService.processSearchQuery(rawQuery));
        Page<Post> postpage = postService.findPostsBySortType(sortType,postIdCollector,isPublished, pageable);
        List<EntityModel<Post>> posts = postpage.getContent()
                .stream()
                .map(
                        postAssembler::toModel
                ).collect(Collectors.toList());
        return PagedModel.of(posts,
                postAssembler.getPageMetaData(postpage)
                ,linkTo(methodOn(FetchContentEndpoint.class)
                .fetchAllPostsByQuery(rawQuery, tagFilterQuery, authorFilterQuery, fromDateFilterQuery, toDateFilterQuery, sortType, isPublished, pageable
        )).withSelfRel());
    }
}
