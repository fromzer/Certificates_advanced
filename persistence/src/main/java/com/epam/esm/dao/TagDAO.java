package com.epam.esm.dao;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.EntityRetrievalException;

/**
 * Base interface class for TagDAO
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface TagDAO extends Dao<TagDTO> {

    /**
     * Find entry in table
     *
     * @param name the tag name
     * @return entry
     * @throws EntityRetrievalException if fail to retrieve data from DB
     */
    TagDTO findByName(String name) throws EntityRetrievalException;
}
