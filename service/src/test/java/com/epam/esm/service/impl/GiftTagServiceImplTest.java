package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftTagServiceImplTest {
    @Mock
    TagDAOImpl tagDAO;
    GiftTagServiceImpl giftTagService;
    TagDTO correctTag;
    GiftTag correctGiftTag;

    @BeforeEach
    void createTag() {
        giftTagService = new GiftTagServiceImpl(tagDAO);
        correctTag = new TagDTO(1L, "name");
        correctGiftTag = new GiftTag(1L, "name");
    }

    @Test
    void shouldCreateTag() throws CreateEntityException, CreateResourceException {
        when(tagDAO.create(correctTag)).thenReturn(1l);
        assertEquals(correctTag.getId(), giftTagService.create(correctGiftTag));
    }

    @Test
    void shouldNotCreateTag() throws CreateEntityException, CreateResourceException {
        GiftTag actual = new GiftTag();
        when(tagDAO.create(any())).thenThrow(CreateEntityException.class);
        assertThrows(CreateResourceException.class, () -> giftTagService.create(actual));
    }

    @Test
    void shouldFindTagById() throws EntityRetrievalException, ResourceNotFoundException {
        when(tagDAO.findById(anyLong())).thenReturn(correctTag);
        GiftTag actual = giftTagService.findById(1L);
        assertEquals(correctGiftTag, actual);
    }

    @Test
    void shouldNotFindTagById() throws EntityRetrievalException, ResourceNotFoundException {
        when(tagDAO.findById(anyLong())).thenReturn(correctTag);
        GiftTag actual = giftTagService.findById(1L);
        GiftTag ex = new GiftTag(1l, "exception");
        assertNotEquals(ex, actual);
    }

    @Test
    void shouldFindTagByName() throws EntityRetrievalException, ResourceNotFoundException {
        when(tagDAO.findByName(anyString())).thenReturn(correctTag);
        GiftTag actual = giftTagService.findByName("name");
        assertEquals(correctGiftTag, actual);
    }

    @Test
    void shouldNotFindTagByName() throws EntityRetrievalException, ResourceNotFoundException {
        when(tagDAO.findByName(anyString())).thenThrow(EntityRetrievalException.class);
        assertThrows(ResourceNotFoundException.class, () -> giftTagService.findByName("name"));
    }

    @Test
    void shouldDeleteTagReturnException() throws DeleteEntityException {
        when(tagDAO.findById(correctGiftTag.getId())).thenThrow(EntityRetrievalException.class);
        assertThrows(ResourceNotFoundException.class, () -> giftTagService.delete(correctGiftTag.getId()));
    }

    @Test
    void shouldDeleteTag() throws DeleteEntityException {
        when(tagDAO.findById(anyLong())).thenReturn(correctTag);
        giftTagService.delete(correctGiftTag.getId());
        verify(tagDAO, times(1)).delete(correctGiftTag.getId());
    }

    @Test
    void shouldFindAllTags() throws EntityRetrievalException, ResourceNotFoundException {
        List<TagDTO> tagDTOList = new ArrayList<>();
        tagDTOList.add(correctTag);
        when(tagDAO.findAll()).thenReturn(tagDTOList);
        assertEquals(1, giftTagService.findAll().size());
    }

    @Test
    void shouldNotFindAllTags() throws EntityRetrievalException, ResourceNotFoundException {
        when(tagDAO.findAll()).thenReturn(null);
        ArrayList<GiftTag> excepted = new ArrayList<>();
        List<GiftTag> actual = giftTagService.findAll();
        assertEquals(excepted, actual);
    }
}