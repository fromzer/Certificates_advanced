package com.epam.esm.dao;

import com.epam.esm.dao.criteria.PredicateConstructor;
import com.epam.esm.dao.criteria.QueryConstructor;
import com.epam.esm.dto.Pageable;
import com.epam.esm.dto.SearchAndSortParams;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

public interface GiftDAO<T extends Persistable<? extends Serializable>> extends Serializable {
    Long create(T entity);

    T update(T entity);

    T findById(Long id);

    T createNativeQuery(String query, String param, String value);

    List<T> findAll(Pageable pageable);

    List<T> findEntityByParams(SearchAndSortParams params, Pageable pageable,
                               PredicateConstructor predicateConstructor, QueryConstructor queryConstructor);

    void delete(T entity);
}
