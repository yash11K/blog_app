package com.mountblue.blogapp.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public interface FilterService {
    List<Integer> findPostIdByTagNames(String tagNames);
    List<Integer> findPostIdByAuthorNames(List<String> authorNames);
}
