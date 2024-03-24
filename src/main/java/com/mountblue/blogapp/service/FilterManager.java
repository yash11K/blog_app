package com.mountblue.blogapp.service;

import com.mountblue.blogapp.dao.PostDao;
import com.mountblue.blogapp.dao.PostTagDao;
import com.mountblue.blogapp.dao.TagDao;
import com.mountblue.blogapp.dao.UserDao;
import com.mountblue.blogapp.model.Post;
import com.mountblue.blogapp.model.Tag;
import com.mountblue.blogapp.model.User;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FilterManager  implements FilterService {
    PostDao postDao;
    TagDao tagDao;
    UserDao userDao;
    PostTagDao postTagDao;

    public FilterManager(PostDao postDao, TagDao tagDao, UserDao userDao, PostTagDao postTagDao) {
        this.postDao = postDao;
        this.tagDao = tagDao;
        this.userDao = userDao;
        this.postTagDao = postTagDao;
    }

    @Override
    public Set<Integer> findPostIdByTagNames(String tagNames) {
        Set<Integer> postIds = new HashSet<>();
        Set<Tag> tags = tagDao.findTagByNameIn(List.of(tagNames.split(",")));
        List<Post> posts = postDao.findPostsByTagsIn(tags);
        for (Post post : posts) {
            postIds.add(post.getId());
        }
        return postIds;
    }

    @Override
    public List<Integer> findPostIdByAuthorNames(String authorNames) {
        List<Integer> postIds = new ArrayList<>();
        List<User> users = userDao.findUserByNameIn(List.of(authorNames.split(",")));
        List<Post> posts = postDao.findPostsByAuthorIn(users);
        for (Post post : posts) {
            postIds.add(post.getId());
        }
        return postIds;
    }

    @Override
    public List<Integer> findPostIdByStartEndDate(String startDate, String endDate) throws ParseException {
        DateFormat stringToDate = new SimpleDateFormat("yyyy-MM-dd");
        Date from = stringToDate.parse(startDate);
        Date end = stringToDate.parse(endDate);
        return postDao.findPostIdsByPublishedAtBetween(from, end);
    }

    @Override
    public Set<Tag> findTagsByPostIds(Collection<Integer> postIds) {
        return tagDao.findTagByIdInOrderByName(postTagDao.findTagIdsByPostIds(postIds));
    }

    @Override
    public Collection<Integer> findPostIdFromCustomFilterQuery(Collection<Integer> postIdCollector, String tagFilterQuery,
                                                        String authorFilterQuery,
                                                        String fromDateFilterQuery,
                                                        String toDateFilterQuery) throws ParseException {
        if(tagFilterQuery!=null){
            postIdCollector.retainAll(findPostIdByTagNames(tagFilterQuery));
        }
        if(authorFilterQuery!=null){
            postIdCollector.retainAll(findPostIdByAuthorNames(authorFilterQuery));
        }
        if(fromDateFilterQuery!=null || toDateFilterQuery!=null){
            postIdCollector.retainAll(findPostIdByStartEndDate(fromDateFilterQuery, toDateFilterQuery));
        }
        return postIdCollector;
    }
}