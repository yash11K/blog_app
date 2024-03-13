package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagManager implements TagService{
    private final TagDao tagService;
    @Autowired
    public TagManager(TagDao tagService) {
        this.tagService = tagService;
    }
    @Override
    public void saveTag(Tag tag){
        tagService.save(tag);
    }
    @Override
    public Tag findTagByName(String name){
        return tagService.findByName(name);
    }
    @Override
    public List<Tag> findAllTags() {
        return tagService.findAll();
    }

    @Override
    public Boolean tagExistsByName(String name) {
        return tagService.existsByName(name);
    }
}
