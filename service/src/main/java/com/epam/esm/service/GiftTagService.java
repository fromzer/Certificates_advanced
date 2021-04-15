package com.epam.esm.service;


import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;

/**
 * Base interface for Tags
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface GiftTagService extends GiftService<GiftTag> {

    /**
     * Find entity by name
     *
     * @param name the tag name
     * @return entity
     * @throws ResourceNotFoundException if fail to retrieve data from DB
     */
    GiftTag findByName(String name) throws ResourceNotFoundException;
}
