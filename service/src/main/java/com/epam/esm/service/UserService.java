package com.epam.esm.service;

import com.epam.esm.model.Pageable;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.UserGift;

import java.util.List;

/**
 * Base interface for User Service
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface UserService {
    /**
     * Get user by id
     *
     * @param id user id
     * @return user
     * @throws ResourceNotFoundException if fail to retrieve data from DB
     */
    UserGift findById(Long id) throws ResourceNotFoundException;

    /**
     * Get all users
     *
     * @param pageable
     * @return list of users
     * @throws ResourceNotFoundException if fail to retrieve data from DB
     */
    List<UserGift> findAll(Pageable pageable) throws ResourceNotFoundException;
}
