package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.SearchOrderByUserIdParams;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.List;

public interface OrderDao {
    Long create(Order entity);

    Order findById(Long id);

    Order findByUserIdAndOrderId(Long orderId, Long userId);

    List<Order> findAll(Pageable pageable);

    void delete(Order entity);

    List<Order> findOrdersByUserId(SearchOrderByUserIdParams params, Pageable pageable);
}
