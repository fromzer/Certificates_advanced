package com.epam.esm.service;

import com.epam.esm.dto.Pageable;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;

import java.io.Serializable;
import java.util.List;

/**
 * Base interface for application services
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface GiftService<T> extends Serializable {
    /**
     * Create entity
     *
     * @param entity an entity of business model
     * @return entity id
     * @throws CreateResourceException if error is occurred during SQL command execution
     */
    Long create(T entity) throws CreateResourceException;

    /**
     * Find entity
     *
     * @return entity
     * @throws ResourceNotFoundException if fail to retrieve data from DB
     */
    T findById(Long id) throws ResourceNotFoundException;

    /**
     * Delete entity
     *
     * @throws DeleteResourceException if error is occurred during SQL command execution
     */
    void delete(Long id) throws DeleteResourceException, ResourceNotFoundException;

    /**
     * Find all entities
     *
     * @return List of entities
     * @throws ResourceNotFoundException if fail to retrieve data from DB
     */
    List<T> findAll(Pageable pageable) throws ResourceNotFoundException;
}
