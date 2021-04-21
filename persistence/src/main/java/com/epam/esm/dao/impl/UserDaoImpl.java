package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.Pageable;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao<User> {
    private static final String SQL_SELECT_FIND_FIRST_NAME = "SELECT id, login, first_name, last_name FROM user WHERE first_name = :name";
    private static final String NAME = "name";

    private final GiftDaoBean<User> giftDao;

    @Autowired
    public UserDaoImpl(GiftDaoBean giftDao) {
        this.giftDao = giftDao;
        giftDao.setClazz(User.class);
    }

    @Override
    public User findById(Long id) {
        return giftDao.findById(id);
    }

    @Override
    public User findByName(String name) {
        return giftDao.createNativeQuery(SQL_SELECT_FIND_FIRST_NAME, NAME, name);
    }

    @Override
    public List<User> findAll(Pageable pageable) {
        return giftDao.findAll(pageable);
    }
}
