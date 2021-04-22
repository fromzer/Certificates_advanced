package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftTagDAOImpl;
import com.epam.esm.model.Pageable;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.GiftTagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class GiftTagServiceImpl implements GiftTagService {
    private final GiftTagDAOImpl tagDAO;
    private final ModelMapper modelMapper;

    public GiftTagServiceImpl(GiftTagDAOImpl tagDAO, ModelMapper modelMapper) {
        this.tagDAO = tagDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Long create(GiftTag entity) throws CreateResourceException {
        try {
            return tagDAO.create(modelMapper.map(entity, Tag.class));
        } catch (CreateEntityException e) {
            log.error("Failed to create tag", e);
            throw new CreateResourceException("Failed to create tag", e);
        }
    }

    @Override
    public GiftTag findById(Long id) throws ResourceNotFoundException {
        try {
            Tag byId = tagDAO.findById(id);
            if (byId == null) {
                throw new ResourceNotFoundException("Tag is not found");
            }
            return modelMapper.map(byId, GiftTag.class);
        } catch (EntityRetrievalException e) {
            log.error("Failed to create tag", e);
            throw new ResourceNotFoundException("Failed to create tag", e);
        }
    }

    @Override
    public GiftTag findByName(String name) throws ResourceNotFoundException {
        try {
            return modelMapper.map(tagDAO.findByName(name), GiftTag.class);
        } catch (EntityRetrievalException e) {
            log.error("Failed to find tag", e);
            throw new ResourceNotFoundException("Failed to find tag", e);
        }
    }

    @Override
    public GiftTag findMostPopularUserTag(Long userId) throws ResourceNotFoundException {
        Tag mostPopularUserTag = tagDAO.findMostPopularUserTag(userId);
        GiftTag popularGiftTag = null;
        if (mostPopularUserTag != null) {
            popularGiftTag = modelMapper.map(mostPopularUserTag, GiftTag.class);
        }
        return popularGiftTag;
    }

    @Override
    public void delete(Long id) throws DeleteResourceException {
        try {
            Tag byId = tagDAO.findById(id);
            if (byId != null) {
                tagDAO.delete(byId);
            } else {
                throw new ResourceNotFoundException();
            }
        } catch (DeleteEntityException e) {
            log.error("Failed to delete tag", e);
            throw new DeleteResourceException("Failed to delete tag", e);
        }
    }

    @Override
    public List<GiftTag> findAll(Pageable pageable) throws ResourceNotFoundException {
        List<Tag> allTags = (pageable.getPage() <= 0 || pageable.getSize() <= 0) ?
                null : tagDAO.findAll(pageable);
        List<GiftTag> convertedAllTags = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(allTags)) {
            convertedAllTags = allTags.stream()
                    .map(tag -> modelMapper.map(tag, GiftTag.class))
                    .collect(Collectors.toList());
        }
        return convertedAllTags;
    }
}
