package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagManager {
    private final TagService tagService;
    @Autowired
    public TagManager(TagService tagService) {
        this.tagService = tagService;
    }
    public void saveTag(Tag tag){
        tagService.save(tag);
    }

    public Tag findTagByName(String name){
        return tagService.findByName(name);
    }
}
