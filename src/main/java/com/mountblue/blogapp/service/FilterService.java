package com.mountblue.blogapp.service;

import com.mountblue.blogapp.model.Tag;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public interface FilterService {
    Set<Integer> findPostIdByTagNames(String tagNames);

    List<Integer> findPostIdByAuthorNames(String authorNames);

    List<Integer> findPostIdByStartEndDate(String startDate, String endDate) throws ParseException;

    Set<Tag> findTagsByPostIds(Collection<Integer> postIds);

    Collection<Integer> findPostIdFromCustomFilterQuery(Collection<Integer> postIdCollector, String tagFilterQuery,
                                                 String authorFilterQuery,
                                                 String fromDateFilterQuery,
                                                 String toDateFilterQuery) throws ParseException;
}
