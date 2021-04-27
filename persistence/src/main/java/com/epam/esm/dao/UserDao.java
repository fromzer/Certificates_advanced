package com.epam.esm.dao;

import com.epam.esm.entity.User;
import com.epam.esm.model.Pageable;

import java.util.List;

public interface UserDao {

    User findById(Long id);

    List<User> findAll(Pageable pageable);
}
