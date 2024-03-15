package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<Tag> findTagByNamePattern(String tagPattern){
        return tagService.findTagByNameContains(tagPattern);
    }

    @Override
    public void findTagSetFromTagString(String tagsStr, Post post) {
        int postId = post.getId();
        Set<Tag> postTags = new HashSet<>();
        List<String> tagStr = Arrays.stream(tagsStr.split(",")).toList();
        for (String PostTagName : tagStr) {
            PostTagName = PostTagName.trim();
            PostTagName = PostTagName.toUpperCase();
            Boolean tagExists = tagExistsByName(PostTagName);
            if (!tagExists) {
                Tag newPostTag = new Tag();
                Set<Post> emptyPost = new HashSet<>();
                newPostTag.setName(PostTagName);
                newPostTag.setPosts(emptyPost);
                saveTag(newPostTag);
            }
            Tag tag = findTagByName(PostTagName);
            tag.getPosts().add(post);
            postTags.add(tag);
        }
        post.setTags(postTags);
    }
}
