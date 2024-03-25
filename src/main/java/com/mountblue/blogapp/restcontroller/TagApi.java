package com.mountblue.blogapp.restcontroller;

import com.mountblue.blogapp.service.ServiceFactory;
import com.mountblue.blogapp.service.TagService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("writewise/api")
public class TagApi {
    private final ServiceFactory serviceFactory;
    private TagService tagService;

    @Autowired
    public TagApi(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @PostConstruct
    public void initService(){
        this.tagService = serviceFactory.getTagService();
    }


}
