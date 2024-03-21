package com.mountblue.blogapp.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    List<Integer> processSearchQuery(String query);
}
