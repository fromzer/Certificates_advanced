package com.epam.esm.dao.impl;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ExistEntityException;
import com.epam.esm.exception.UpdateEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CertificateDAOImplTest {
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private CertificateDAOImpl certificateDAO;
    private TagDAOImpl tagDAOImpl;

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .addScript("create_tables.sql")
                .addScript("add_data.sql")
                .build();
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        tagDAOImpl = new TagDAOImpl(namedParameterJdbcTemplate);
        certificateDAO = new CertificateDAOImpl(namedParameterJdbcTemplate, tagDAOImpl);
    }

    private CertificateDTO certificate = CertificateDTO.builder()
            .name("New certificate name")
            .description("New certificate description")
            .price(BigDecimal.valueOf(12.13))
            .duration(1)
            .build();

    @Test
    void shouldUpdateOnlyName() throws EntityRetrievalException, UpdateEntityException {
        CertificateDTO expected = certificateDAO.update(CertificateDTO.builder().id(2l).name("QWERTY").build());
        CertificateDTO actual = certificateDAO.findById(2L);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdateNameAndDescription() throws EntityRetrievalException, UpdateEntityException {
        CertificateDTO expected = certificateDAO.update(CertificateDTO.builder()
                .id(5l)
                .name("New Name")
                .description("New Description")
                .build());
        CertificateDTO actual = certificateDAO.findById(5L);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindCertificatesByPartialName() throws EntityRetrievalException, UpdateEntityException {
        SearchAndSortParams params = new SearchAndSortParams();
        params.setName("hop");
        List<CertificateDTO> expected = certificateDAO.findCertificateByParams(params);
        CertificateDTO actual = certificateDAO.findById(6L);
        assertEquals(expected.get(0).getName(), actual.getName());
    }


    @Test
    void shouldUpdateAllFieldsAndCreateTag() throws EntityRetrievalException, UpdateEntityException {
        CertificateDTO certificateDTO = certificateDAO.findById(6L);
        certificateDTO.setName("New Name");
        certificateDTO.setDescription("New Description");
        certificateDTO.setDuration(77);
        certificateDTO.setTags(new LinkedHashSet<>());
        TagDTO tagDTO = TagDTO.builder()
                .name("Update")
                .build();
        certificateDTO.getTags().add(tagDTO);
        CertificateDTO expected = certificateDAO.update(certificateDTO);
        CertificateDTO actual = certificateDAO.findById(6L);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void shouldCreateCertificate() throws CreateEntityException, EntityRetrievalException {
        Long id = certificateDAO.create(certificate);
        CertificateDTO created = certificateDAO.findById(id);
        assertEquals(certificate.getName(), created.getName());
        assertEquals(certificate.getDescription(), created.getDescription());
        assertEquals(certificate.getPrice(), created.getPrice());
        assertEquals(certificate.getDuration(), created.getDuration());
    }

    @Test
    void shouldNotCreateCertificate() throws CreateEntityException, EntityRetrievalException {
        CertificateDTO cert = CertificateDTO.builder()
                .name("new")
                .build();
        assertThrows(CreateEntityException.class, () -> certificateDAO.create(cert));
    }

    @Test
    void shouldNotCreateCertificateTagIsExist() throws CreateEntityException, EntityRetrievalException {
        TagDTO tagDTO = TagDTO.builder()
                .name("WoW")
                .build();
        Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
        tagDTOSet.add(tagDTO);
        certificate.setTags(tagDTOSet);
        assertThrows(ExistEntityException.class, () -> certificateDAO.create(certificate));
    }

    @Test
    void shouldCreateCertificateTag() throws CreateEntityException, EntityRetrievalException {
        TagDTO tagDTO = TagDTO.builder()
                .name("Use")
                .build();
        Set<TagDTO> tagDTOSet = new LinkedHashSet<>();
        tagDTOSet.add(tagDTO);
        certificate.setTags(tagDTOSet);
        long id = certificateDAO.create(certificate);
        CertificateDTO expected = certificateDAO.findById(id);
        CertificateDTO actual = certificateDAO.findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindCertificateById() throws EntityRetrievalException {
        CertificateDTO dto = certificateDAO.findById(1L);
        assertNotNull(dto);
        assertEquals(dto.getName(), "TestCertificate");
    }

    @Test
    void shouldNotFindReturnNull() throws EntityRetrievalException {
        assertEquals(certificateDAO.findById(77L), null);
    }

    @Test
    void shouldDeleteCertificate() throws DeleteEntityException, EntityRetrievalException {
        CertificateDTO cert = CertificateDTO.builder()
                .id(6L)
                .build();
        certificateDAO.delete(cert.getId());
        assertEquals(certificateDAO.findById(cert.getId()), null);
    }

    @Test
    void shouldFindAllCertificates() throws EntityRetrievalException {
        CertificateDTO certificateDTO = certificateDAO.findById(1L);
        List<CertificateDTO> certificateDTOS = certificateDAO.findAll();
        assertEquals(certificateDTOS.get(0), certificateDTO);
    }

    @Test
    void shouldFindCertificateByParams() throws EntityRetrievalException {
        CertificateDTO certificateDTO = certificateDAO.findById(1L);
        TagDTO tagDTO = tagDAOImpl.findById(2L);
        SearchAndSortParams paramsWithTagName = SearchAndSortParams.builder().tag(tagDTO.getName()).build();
        SearchAndSortParams paramsWithCertName = SearchAndSortParams.builder().name(certificateDTO.getName()).build();
        SearchAndSortParams paramsWithCertDescription = SearchAndSortParams.builder().description(certificateDTO.getDescription()).build();
        List<CertificateDTO> certificateByTagName = certificateDAO.findCertificateByParams(paramsWithTagName);
        List<CertificateDTO> certificateByName = certificateDAO.findCertificateByParams(paramsWithCertName);
        List<CertificateDTO> certificateByDescription = certificateDAO.findCertificateByParams(paramsWithCertDescription);
        assertEquals(certificateByTagName.get(0).getId(), certificateDTO.getId()); //??
        assertEquals(certificateByName.get(0).getName(), certificateDTO.getName()); //??
        assertEquals(certificateByDescription.get(0).getDuration(), certificateDTO.getDuration()); //??
    }
}