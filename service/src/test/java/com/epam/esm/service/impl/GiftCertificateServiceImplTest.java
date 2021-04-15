package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateEntityException;
import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftTag;
import com.epam.esm.model.SearchAndSortGiftCertificateOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private CertificateDAOImpl certificateDAO;
    GiftCertificateServiceImpl giftCertificateService;

    private CertificateDTO correctCertificate;
    private GiftCertificate correctGiftCertificate;

    @BeforeEach
    void createCertificate() {
        giftCertificateService = new GiftCertificateServiceImpl(certificateDAO);
        correctCertificate = CertificateDTO.builder()
                .id(1l)
                .name("New certificate name")
                .description("New certificate description")
                .price(BigDecimal.valueOf(12.13))
                .duration(1)
                .build();
        correctGiftCertificate = GiftCertificate.builder()
                .id(1l)
                .name("New certificate name")
                .description("New certificate description")
                .price(BigDecimal.valueOf(12.13))
                .duration(1)
                .build();
    }

    @Test
    void shouldUpdateOnlyName() throws UpdateEntityException, UpdateResourceException, EntityRetrievalException {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1l)
                .name("new NAME")
                .build();
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("New certificate description")
                .price(BigDecimal.valueOf(12.13))
                .duration(1)
                .build();
        when(certificateDAO.findById(1L)).thenReturn(certificateDTO);
        when(certificateDAO.update(any())).thenReturn(certificateDTO);
        GiftCertificate actual = giftCertificateService.update(giftCertificate, giftCertificate.getId());
        assertEquals(certificateDTO.getName(), actual.getName());
    }

    @Test
    void shouldUpdateNameAndDescription() throws EntityRetrievalException, UpdateEntityException, UpdateResourceException {
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .build();
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .price(BigDecimal.valueOf(12.13))
                .duration(1)
                .build();
        when(certificateDAO.findById(1L)).thenReturn(certificateDTO);
        when(certificateDAO.update(any())).thenReturn(certificateDTO);
        GiftCertificate actual = giftCertificateService.update(giftCertificate, giftCertificate.getId());
        assertEquals(certificateDTO.getDescription(), actual.getDescription());
    }

    @Test
    void shouldNotUpdateCertificate() throws EntityRetrievalException, UpdateEntityException, UpdateResourceException {
        GiftCertificate actual = GiftCertificate.builder()
                .name("new NAME")
                .description("car")
                .build();
        //when(certificateDAO.update(any())).thenThrow(UpdateEntityException.class);
        assertThrows(ResourceNotFoundException.class, () -> giftCertificateService.update(actual, 77l));
    }

    @Test
    void shouldUpdateAllFieldsAndCreateTag() throws EntityRetrievalException, UpdateEntityException, UpdateResourceException {
        TagDTO tagDTO = TagDTO.builder()
                .id(1l)
                .name("moto")
                .build();
        GiftTag giftTag = GiftTag.builder()
                .id(1l)
                .name("moto")
                .build();
        Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
        tagDTOSet.add(tagDTO);
        Set<GiftTag> giftTags = new LinkedHashSet<>();
        giftTags.add(giftTag);
        CertificateDTO certificate = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .price(BigDecimal.valueOf(11.00))
                .duration(7)
                .tags(tagDTOSet)
                .build();
        GiftCertificate giftCertificate = GiftCertificate.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .price(BigDecimal.valueOf(11.00))
                .duration(7)
                .tags(giftTags)
                .build();
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(1l)
                .name("new NAME")
                .description("car")
                .price(BigDecimal.valueOf(11.00))
                .duration(7)
                .tags(tagDTOSet)
                .build();
        when(certificateDAO.findById(1L)).thenReturn(certificateDTO);
        when(certificateDAO.update(certificate)).thenReturn(certificateDTO);
        GiftCertificate update = giftCertificateService.update(giftCertificate, giftCertificate.getId());
        boolean contains = update.getTags().contains(giftTag);
        assertEquals(certificateDTO.getDescription(), update.getDescription());
        assertEquals(certificateDTO.getName(), update.getName());
        assertEquals(certificateDTO.getPrice(), update.getPrice());
        assertEquals(certificateDTO.getDuration(), update.getDuration());
        assertEquals(contains, true);
    }

    @Test
    void shouldCreateCertificate() throws CreateEntityException, CreateResourceException {
        when(certificateDAO.create(any())).thenReturn(1l);
        assertEquals(correctCertificate.getId(), giftCertificateService.create(correctGiftCertificate));
    }

    @Test
    void shouldNotCreateCertificate() throws CreateEntityException, CreateResourceException {
        when(certificateDAO.create(any())).thenThrow(CreateEntityException.class);
        assertThrows(CreateResourceException.class, () -> giftCertificateService.create(correctGiftCertificate));
    }

    @Test
    void ShouldFindCertificateById() throws ResourceNotFoundException, EntityRetrievalException {
        when(certificateDAO.findById(anyLong())).thenReturn(correctCertificate);
        GiftCertificate actual = giftCertificateService.findById(1L);
        assertEquals(correctGiftCertificate.getId(), actual.getId());
    }

    @Test
    void ShouldNotFindCertificateById() throws ResourceNotFoundException, EntityRetrievalException {
        when(certificateDAO.findById(anyLong())).thenReturn(correctCertificate);
        GiftCertificate actual = giftCertificateService.findById(1L);
        GiftCertificate ex = GiftCertificate.builder()
                .id(1l)
                .name("New name")
                .description("New certificate description")
                .price(BigDecimal.valueOf(12.00))
                .duration(1)
                .build();
        assertNotEquals(ex, actual);
    }

    @Test
    void shouldDeleteCertificateConvertResourceException() throws DeleteEntityException {
        assertThrows(DeleteResourceException.class, () -> giftCertificateService.delete(correctGiftCertificate.getId()));
    }

    @Test
    void shouldDeleteCertificate() throws DeleteEntityException {
        when(certificateDAO.findById(anyLong())).thenReturn(correctCertificate);
        giftCertificateService.delete(correctGiftCertificate.getId());
        verify(certificateDAO, times(1)).delete(correctGiftCertificate.getId());
    }

    @Test
    void shouldFindAllCertificates() throws EntityRetrievalException, ResourceNotFoundException {
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificateDTOList.add(correctCertificate);
        when(certificateDAO.findAll()).thenReturn(certificateDTOList);
        assertEquals(1, giftCertificateService.findAll().size());
    }

    @Test
    void shouldNotFindAllCertificates() throws EntityRetrievalException, ResourceNotFoundException {
        when(certificateDAO.findAll()).thenReturn(null);
        ArrayList<GiftCertificate> excepted = new ArrayList<>();
        List<GiftCertificate> actual = giftCertificateService.findAll();
        assertEquals(excepted, actual);
    }

    @Test
    void shouldFindCertificateByTag() throws EntityRetrievalException, ResourceNotFoundException {
        TagDTO tagDTO = TagDTO.builder()
                .id(1l)
                .name("moto")
                .build();
        GiftTag giftTag = GiftTag.builder()
                .id(1l)
                .name("moto")
                .build();
        Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
        tagDTOSet.add(tagDTO);
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(2l)
                .name("Hello")
                .description("World")
                .price(BigDecimal.valueOf(12.00))
                .duration(1)
                .tags(tagDTOSet)
                .build();
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificateDTOList.add(certificateDTO);
        when(certificateDAO.findCertificateByParams(any(SearchAndSortParams.class))).thenReturn(certificateDTOList);
        SearchAndSortGiftCertificateOptions options = new SearchAndSortGiftCertificateOptions();
        options.setTag("moto");
        GiftCertificate giftCertificateFindByTag = giftCertificateService.findCertificateByParams(options).get(0);
        boolean containTag = giftCertificateFindByTag.getTags().contains(giftTag);
        assertEquals(containTag, true);
    }

    @Test
    void shouldFindCertificateByName() throws EntityRetrievalException, ResourceNotFoundException {
        TagDTO tagDTO = TagDTO.builder()
                .id(1l)
                .name("moto")
                .build();
        Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
        tagDTOSet.add(tagDTO);
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(2l)
                .name("Hello")
                .description("World")
                .price(BigDecimal.valueOf(12.00))
                .duration(1)
                .tags(tagDTOSet)
                .build();
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificateDTOList.add(certificateDTO);
        when(certificateDAO.findCertificateByParams(any(SearchAndSortParams.class))).thenReturn(certificateDTOList);
        SearchAndSortGiftCertificateOptions options = new SearchAndSortGiftCertificateOptions();
        options.setName("Hello");
        GiftCertificate giftCertificateFindByName = giftCertificateService.findCertificateByParams(options).get(0);
        boolean findByName = giftCertificateFindByName.getName().equals("Hello");
        assertEquals(findByName, true);
    }

    @Test
    void shouldFindCertificateByDescription() throws EntityRetrievalException, ResourceNotFoundException {
        TagDTO tagDTO = TagDTO.builder()
                .id(1l)
                .name("moto")
                .build();
        Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
        tagDTOSet.add(tagDTO);
        CertificateDTO certificateDTO = CertificateDTO.builder()
                .id(2l)
                .name("Hello")
                .description("World")
                .price(BigDecimal.valueOf(12.00))
                .duration(1)
                .tags(tagDTOSet)
                .build();
        List<CertificateDTO> certificateDTOList = new ArrayList<>();
        certificateDTOList.add(certificateDTO);
        when(certificateDAO.findCertificateByParams(any(SearchAndSortParams.class))).thenReturn(certificateDTOList);
        SearchAndSortGiftCertificateOptions options = new SearchAndSortGiftCertificateOptions();
        options.setDescription("World");
        GiftCertificate giftCertificateFindByName = giftCertificateService.findCertificateByParams(options).get(0);
        boolean findByDescription = giftCertificateFindByName.getDescription().equals("World");
        assertEquals(findByDescription, true);
    }
}