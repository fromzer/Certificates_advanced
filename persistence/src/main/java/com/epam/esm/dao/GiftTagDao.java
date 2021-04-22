package com.epam.esm.dao;

import com.epam.esm.model.Pageable;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

public interface GiftTagDao<T extends Persistable<? extends Serializable>> {
    Long create(T entity);

    T findById(Long id);

    T findByName(String name);

    T findMostPopularUserTag(Long userId);

    List<T> findAll(Pageable pageable);

    void delete(T entity);
}
