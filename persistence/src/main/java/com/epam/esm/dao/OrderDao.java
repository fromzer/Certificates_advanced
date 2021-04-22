package com.epam.esm.dao;

import com.epam.esm.model.Pageable;
import com.epam.esm.model.SearchOrderByUserIdParams;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

public interface OrderDao<T extends Persistable<? extends Serializable>> {
    Long create(T entity);

    T findById(Long id);

    T findByUserIdAndOrderId(Long orderId, Long userId);

    List<T> findAll(Pageable pageable);

    void delete(T entity);

    List<T> findOrdersByUserId(SearchOrderByUserIdParams params, Pageable pageable);
}
