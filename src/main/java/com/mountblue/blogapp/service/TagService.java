package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TagService{
    void saveTag(Tag tag);
    Tag findTagByName(String name);
    List<Tag> findAllTags();
    Boolean tagExistsByName(String name);
}
