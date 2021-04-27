package com.epam.esm.dao;

import com.epam.esm.dao.criteria.PredicateConstructor;
import com.epam.esm.dao.criteria.QueryConstructor;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.SearchAndSortParams;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GiftDAO<T extends Persistable<? extends Serializable>> extends Serializable {
    Long create(T entity);

    T update(T entity);

    T findById(Long id);

    T createNativeQuery(String query, String param, String value);

    T createNativeQuery(String query, Map<String, String> params);

    List<T> findAll(Pageable pageable);

    List<T> findEntitiesByParams(SearchAndSortParams params, Pageable pageable,
                                 PredicateConstructor predicateConstructor, QueryConstructor queryConstructor);

    void delete(T entity);
}
