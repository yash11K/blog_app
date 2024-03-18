package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Tag;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

@Service
public interface FilterService {
    Set<Integer> findPostIdByTagNames(String tagNames);

    List<Integer> findPostIdByAuthorNames(String authorNames);

    List<Integer> findPostIdByStartEndDate(String startDate, String endDate) throws ParseException;

    List<Tag> findTagsByPostIds(List<Integer> postIds);
}
