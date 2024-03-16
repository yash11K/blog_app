package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService{

    List<Tag> findALlTags();
    void saveTag(Tag tag);

    Tag findTagByName(String name);

    List<Tag> findAllTags();

    Boolean tagExistsByName(String name);

    List<Tag> findTagByNamePattern(String tagPatter);

    void findTagSetFromTagString(String tagsStr, Post post);

    List<Tag> findTagByTagNames(List<String> tagNames);
}
