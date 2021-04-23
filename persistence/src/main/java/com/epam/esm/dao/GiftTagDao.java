package com.epam.esm.dao;

import com.epam.esm.entity.Tag;
import com.epam.esm.model.Pageable;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

public interface GiftTagDao {
    Long create(Tag entity);

    Tag findById(Long id);

    Tag findByName(String name);

    Tag findMostPopularUserTag(Long userId);

    List<Tag> findAll(Pageable pageable);

    void delete(Tag entity);
}
