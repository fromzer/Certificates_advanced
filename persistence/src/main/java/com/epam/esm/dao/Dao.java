package com.epam.esm.dao;

import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;

import java.util.List;


/**
 * Base interface class for DAO
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface Dao<T> {
    /**
     * Create entry in DB
     *
     * @param entity an entity of business model
     * @return entry id
     * @throws CreateEntityException if error is occurred during SQL command execution
     */
    Long create(T entity) throws CreateEntityException;

    /**
     * Find entry in table
     *
     * @return entry
     * @throws EntityRetrievalException if fail to retrieve data from DB
     */
    T findById(Long id) throws EntityRetrievalException;

    /**
     * Delete entry in table
     *
     * @throws DeleteEntityException if error is occurred during SQL command execution
     */
    void delete(Long id) throws DeleteEntityException;

    /**
     * Find all entries in table
     *
     * @return List of entities
     * @throws EntityRetrievalException if fail to retrieve data from DB
     */
    List<T> findAll() throws EntityRetrievalException;
}
