package com.mountblue.blogapp.dao;

import com.mountblue.blogapp.model.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagDao extends JpaRepository<Tag, Integer> {
    Boolean existsByName(String name);
    Tag findByName(String name);

    List<Tag> findAll();

    List<Tag> findTagByNameContains(String tagPatter);

    List<Tag> findTagByNameIn(List<String> tagNames);

    List<Tag> findTagByIdIn(List<Integer> tagIds);
}
