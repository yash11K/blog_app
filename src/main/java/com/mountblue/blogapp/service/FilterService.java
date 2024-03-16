package com.mountblue.blogapp.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FilterService {
    List<Integer> findPostIdByTagNames(List<String> tagNames);
}
