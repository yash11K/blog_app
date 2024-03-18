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
    private final TagDao tagDao;
    @Autowired
    public TagManager(TagDao tagDao) {
        this.tagDao = tagDao;
    }
    @Override
    public void saveTag(Tag tag){
        tagDao.save(tag);
    }
    @Override
    public Tag findTagByName(String name){
        return tagDao.findByName(name);
    }
    @Override
    public List<Tag> findAllTags() {
        return tagDao.findAll();
    }

    @Override
    public Boolean tagExistsByName(String name) {
        return tagDao.existsByName(name);
    }

    @Override
    public List<Tag> findTagByNamePattern(String tagPattern){
        return tagDao.findTagByNameContains(tagPattern);
    }

    @Override
    public List<Tag> findALlTags() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> findTagByTagNames(List<String> tagNames) {
        return tagDao.findTagByNameIn(tagNames);
    }

    @Override
    public List<Tag> findTagsById(List<Integer> tagIds){
        return tagDao.findTagByIdIn(tagIds);
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

    @Override
    public StringBuilder getTagNamesAsString(Set<Tag> tags){
        StringBuilder tagsStr = new StringBuilder();
        for(Tag tag : tags){
            tagsStr.append(tag.getName()).append(',');
        }
        return tagsStr;
    }
}
