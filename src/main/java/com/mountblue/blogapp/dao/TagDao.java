package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {
    Boolean existsByName(String name);
    Optional<Tag> findByName(String name);

    List<Tag> findAll();

    List<Tag> findTagByNameContains(String tagPatter);

    Set<Tag> findTagByNameIn(List<String> tagNames);

    Set<Tag> findTagByIdInOrderByName(Set<Integer> tagIds);
}
