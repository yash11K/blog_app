package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagManager implements TagService{
    private final TagDao tagService;
    @Autowired
    public TagManager(TagDao tagService) {
        this.tagService = tagService;
    }
    public void saveTag(Tag tag){
        tagService.save(tag);
    }

    public Tag findTagByName(String name){
        return tagService.findByName(name);
    }
    @Override
    public List<Tag> findAllTags() {
        return tagService.findAll();
    }
}
