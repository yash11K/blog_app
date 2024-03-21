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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.*;
import java.util.logging.Filter;

@Controller
public class BlogHomeController extends AbstractBlogControl{
    private final ServiceFactory serviceFactory;
    private PostService postService;
    private FilterService filterService;
    private SearchService searchService;
    private UserService userService;
    @Autowired
    public BlogHomeController(ServiceFactory serviceFactory){
        this.serviceFactory=serviceFactory;
    }
    @ModelAttribute
    public void initService(){
        postService = serviceFactory.getPostService();
        filterService = serviceFactory.getFilterService();
        searchService = serviceFactory.getSearchService();
        userService = serviceFactory.getUserService();
    }

    @GetMapping("/home")
    public String showHomePage(Model model,
                               @RequestParam(value = "orderBy", required = false)String orderBy,
                               @RequestParam(value = "rawQuery", required = false)String rawQuery,
                               @RequestParam(value = "tagQuery", required = false)String tagQuery,
                               @RequestParam(value = "userQuery", required = false)String userQuery,
                               @RequestParam(value = "from", required = false)String startDate,
                               @RequestParam(value = "to", required = false)String endDate,
                               @RequestParam(value = "page", required = false, defaultValue = "0")Integer page,
                               RedirectAttributes redirectAttributes) throws ParseException {

        boolean processRawQuery = false;
        boolean processTagQuery = false;
        boolean processUserQuery = false;
        boolean processDateQuery = false;
        int pageSize = 8;

        Set<Integer> postIdsCollector = new HashSet<>();

        if (orderBy==null){
//            redirectAttributes.addAttribute("orderBy", "dateDesc");
//            return "redirect:/home";
            orderBy = "dateDesc";
        }

        rawQuery = queryNullifier(rawQuery);
        tagQuery = queryNullifier(tagQuery);
        userQuery = queryNullifier(userQuery);
        startDate = queryNullifier(startDate);
        endDate = queryNullifier(endDate);
        if (rawQuery != null) {
            processRawQuery = postIdsCollector.addAll(searchService.processSearchQuery(rawQuery));
            model.addAttribute("rawQuery", rawQuery);
        }
        if(tagQuery != null){
            model.addAttribute("tagQuery", tagQuery);
            Collection<Integer> tagQueryResults = filterService.findPostIdByTagNames(tagQuery);
            processTagQuery = processRawQuery ?
                    postIdsCollector.retainAll(tagQueryResults) : postIdsCollector.addAll(tagQueryResults);
        }
        if(userQuery!= null){
            model.addAttribute("userQuery", userQuery);
            Collection<Integer> userQueryResults = filterService.findPostIdByAuthorNames(userQuery);
            processUserQuery = processRawQuery || processTagQuery ?
                    postIdsCollector.retainAll(userQueryResults) : postIdsCollector.addAll(userQueryResults);
        }

        if(startDate != null || endDate != null){
            model.addAttribute("from", startDate);
            model.addAttribute("to", endDate);
            Collection<Integer> dateQueryResults = filterService.findPostIdByStartEndDate(startDate, endDate);
            processDateQuery = processRawQuery || processTagQuery || processUserQuery ?
                    postIdsCollector.retainAll(dateQueryResults) : postIdsCollector.addAll(dateQueryResults);
        }
        if(!processRawQuery && !processTagQuery && !processDateQuery && !processUserQuery){
            boolean b = postIdsCollector.addAll(postService.findIdByPublished(true));
        }

        Collection<Tag> relativeTags = filterService.findTagsByPostIds(postIdsCollector);
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Post> publishedPosts = postService.findPostsBySortType(orderBy, postIdsCollector, true, pageable);
        model.addAttribute("publishedPosts", publishedPosts);
        model.addAttribute("allUsers", userService.findAllUsers());
        model.addAttribute("allTags", relativeTags);
        model.addAttribute("sortType", orderBy);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", publishedPosts.getTotalPages());
        return "home";
    }

    @GetMapping("/page")
    public String homeUriCleaner(@RequestParam(value = "orderBy", required = false)String orderBy,
                                 @RequestParam(value = "rawQuery", required = false)String rawQuery,
                                 @RequestParam(value = "tagQuery", required = false)String tagQuery,
                                 @RequestParam(value = "userQuery", required = false)String userQuery,
                                 @RequestParam(value = "from", required = false)String startDate,
                                 @RequestParam(value = "to", required = false)String endDate,
                                 @RequestParam(value = "page", required = false, defaultValue = "0")Integer page,
                                 RedirectAttributes redirectAttributes){
        addRedirectAttribute("rawQuery", rawQuery, redirectAttributes);
        addRedirectAttribute("userQuery",userQuery, redirectAttributes);
        addRedirectAttribute("tagQuery", tagQuery, redirectAttributes);
        addRedirectAttribute("from", startDate, redirectAttributes);
        addRedirectAttribute("to", endDate, redirectAttributes);
        addRedirectAttribute("page", page.toString(), redirectAttributes);
        redirectAttributes.addAttribute("orderBy", orderBy);
        return "redirect:/home";
    }

    public String queryNullifier(String query){
        if(query==null || query.isEmpty()){
            return null;
        }
        else return query;
    }
}
