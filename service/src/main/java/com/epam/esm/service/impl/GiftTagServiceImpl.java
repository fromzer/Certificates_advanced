package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.GiftTagService;
import com.epam.esm.utils.converter.GiftTagConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GiftTagServiceImpl implements GiftTagService {
    private static final Logger logger = LoggerFactory.getLogger(GiftTagServiceImpl.class);
    private final TagDAO tagDAO;

    @Autowired
    public GiftTagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional
    public Long create(GiftTag entity) throws CreateResourceException {
        try {
            return tagDAO.create(GiftTagConverter.convertToPersistenceLayerEntity(entity));
        } catch (CreateEntityException e) {
            logger.error("Failed to create tag", e);
            throw new CreateResourceException("Failed to create tag", e);
        }
    }

    @Override
    public GiftTag findById(Long id) throws ResourceNotFoundException {
        try {
            TagDTO byId = tagDAO.findById(id);
            if (byId == null) {
                throw new ResourceNotFoundException("Tag is not found");
            }
            return GiftTagConverter.convertToServiceLayerEntity(byId);
        } catch (EntityRetrievalException e) {
            logger.error("Failed to create tag", e);
            throw new ResourceNotFoundException("Failed to create tag", e);
        }
    }

    @Override
    public GiftTag findByName(String name) throws ResourceNotFoundException {
        try {
            return GiftTagConverter.convertToServiceLayerEntity(tagDAO.findByName(name));
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find tag", e);
            throw new ResourceNotFoundException("Failed to find tag", e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws DeleteResourceException {
        try {
            if (findById(id) != null) {
                tagDAO.delete(id);
            } else {
                throw new ResourceNotFoundException();
            }
        } catch (DeleteEntityException e) {
            logger.error("Failed to delete tag", e);
            throw new DeleteResourceException("Failed to delete tag", e);
        }
    }

    @Override
    public List<GiftTag> findAll() throws ResourceNotFoundException {
        List<TagDTO> tags = tagDAO.findAll();
        List<GiftTag> giftTagList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tags)) {
            for (TagDTO tag : tags) {
                giftTagList.add(GiftTagConverter.convertToServiceLayerEntity(tag));
            }
        }
        return giftTagList;
    }
}
