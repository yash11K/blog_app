package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService{
    void saveTag(Tag tag);
    Tag findTagByName(String name);
    List<Tag> findAllTags();
}
