package com.epam.esm.dao;

import com.epam.esm.dto.Pageable;
import com.epam.esm.dto.SearchAndSortParams;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

public interface OrderDao<T extends Persistable<? extends Serializable>> {
    Long create(T entity);

    T findById(Long id);

    List<T> findAll(Pageable pageable);

    void delete(T entity);

    List<T> findOrdersByParams(SearchAndSortParams params, Pageable pageable);
}
