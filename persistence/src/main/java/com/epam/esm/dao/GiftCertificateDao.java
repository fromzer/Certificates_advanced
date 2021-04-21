package com.epam.esm.dao;

import com.epam.esm.dto.Pageable;
import com.epam.esm.dto.SearchAndSortParams;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

public interface GiftCertificateDao<T extends Persistable<? extends Serializable>> {
    Long create(T entity);

    T update(T entity);

    T findById(Long id);

    List<T> findAll(Pageable pageable);

    List<T> findEntitiesByParams(SearchAndSortParams params, Pageable pageable);

    void delete(T entity);
}
