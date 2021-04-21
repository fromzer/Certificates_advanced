package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.Pageable;
import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao<Order> {

    private final GiftDaoBean<Order> giftDao;

    @Autowired
    public OrderDaoImpl(GiftDaoBean giftDao) {
        this.giftDao = giftDao;
        giftDao.setClazz(Order.class);
    }

    @Override
    public Long create(Order entity) {
        return giftDao.create(entity);
    }

    @Override
    public Order findById(Long id) {
        return giftDao.findById(id);
    }

    @Override
    public List<Order> findAll(Pageable pageable) {
        return giftDao.findAll(pageable);
    }

    @Override
    public void delete(Order entity) {
        giftDao.delete(entity);
    }

    @Override
    public List<Order> findOrdersByParams(SearchAndSortParams params, Pageable pageable) {
        return null;
    }
}
