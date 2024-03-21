package com.mountblue.blogapp.controller;
import com.mountblue.blogapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FilterOperationController extends AbstractBlogControl{
    @GetMapping("/orderBy")
    public String showHomePageWithOrderedPost(@RequestParam("orderBy")String orderBy,
                                              @RequestParam(value = "rawQuery", required = false)String rawQuery,
                                              @RequestParam(value = "tagQuery", required = false)String tagQuery,
                                              @RequestParam(value = "userQuery", required = false)String userQuery,
                                              @RequestParam(value = "from", required = false)String startDate,
                                              @RequestParam(value = "to", required = false)String endDate,
                                              RedirectAttributes redirectAttributes){
        addRedirectAttribute("rawQuery", rawQuery, redirectAttributes);
        addRedirectAttribute("from", startDate, redirectAttributes);
        addRedirectAttribute("to", endDate, redirectAttributes);
        addRedirectAttribute("tagQuery", tagQuery, redirectAttributes);
        addRedirectAttribute("userQuery",userQuery, redirectAttributes);
        redirectAttributes.addAttribute("orderBy",orderBy);

        return "redirect:/home";
    }

    @GetMapping("/search")
    public String processSearchQuery(@RequestParam("rawQuery")String rawQuery,
                                     RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("rawQuery",rawQuery);
        redirectAttributes.addAttribute("orderBy", "dateDesc");
        return "redirect:/home";
    }

    @GetMapping("/filter")
    public String processFilterQuery(@RequestParam(value = "tagQuery", required = false)String tagQuery,
                                     @RequestParam(value = "userQuery", required = false)String userQuery,
                                     @RequestParam(value = "from", required = false)String startDate,
                                     @RequestParam(value = "to", required = false)String endDate,
                                     @RequestParam(value = "rawQuery", required = false)String rawQuery,
                                     RedirectAttributes redirectAttributes){
        addRedirectAttribute("rawQuery", rawQuery, redirectAttributes);
        addRedirectAttribute("userQuery",userQuery, redirectAttributes);
        addRedirectAttribute("tagQuery", tagQuery, redirectAttributes);
        addRedirectAttribute("from", startDate, redirectAttributes);
        addRedirectAttribute("to", endDate, redirectAttributes);
        redirectAttributes.addAttribute("orderBy", "dateDesc");
        return "redirect:/home";
    }
}
