package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.extractor.CertificateListResultSetExtractor;
import com.epam.esm.dao.extractor.CertificateResultSetExtractor;
import com.epam.esm.dao.extractor.TagListResultSetExtractor;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.UpdateEntityException;
import com.epam.esm.util.ToDTOConverter;
import com.epam.esm.util.ToEntityConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
public class CertificateDAOImpl implements CertificateDAO {
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT gift_certificate.*, tag.* FROM gift_certificate\n" +
            "LEFT OUTER JOIN gift_certificate_tag gct on gift_certificate.id = gct.gift_certificate_id\n" +
            "LEFT OUTER JOIN tag tag on gct.tag_id = tag.id\n" +
            "WHERE gift_certificate.id = :id;";
    private static final String SQL_SELECT_FIND_ALL = "SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate;";
    private static final String SQL_SELECT_FIND_TAGS_BY_CERTIFICATE_ID = "SELECT tag.id, tag.name FROM tag\n" +
            "JOIN gift_certificate_tag gct on tag.id = gct.tag_id\n" +
            "JOIN gift_certificate gc on gc.id = gct.gift_certificate_id\n" +
            "WHERE gc.id = :id ;";
    private static final String SQL_INSERT_CREATE_CERTIFICATE = "INSERT INTO gift_certificate (name, description, price, duration)\n" +
            "VALUES (:name, :description, :price , :duration);";
    private static final String SQL_INSERT_CREATE_CERTIFICATE_TAG = "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (:certificateId, :tagId);";
    private static final String SQL_DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = :id;";
    private static final String BASIC_SQL_SELECT = "SELECT gift_certificate.* FROM gift_certificate\n" +
            "LEFT OUTER JOIN gift_certificate_tag gct on gift_certificate.id = gct.gift_certificate_id\n" +
            "LEFT OUTER JOIN tag tag on gct.tag_id = tag.id ";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TagDAO tagDAO;

    @Autowired
    public CertificateDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, TagDAO tagDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional
    public CertificateDTO update(CertificateDTO certificateDTO) {
        try {
            Certificate certificate = ToEntityConverter.convertToCertificate(certificateDTO);
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("id", certificateDTO.getId());
            jdbcTemplate.update(SqlCreator.getQueryUpdateByPart(certificate), parameterSource);
            if (CollectionUtils.isNotEmpty(certificate.getTags())) {
                List<Long> tagsIdList = createTags(certificateDTO.getTags());
                AddLinkBetweenTagAndCertificate(certificateDTO.getId(), tagsIdList);
            }
            return findById(certificate.getId());
        } catch (DataAccessException ex) {
            log.error("Request update certificate execution error", ex);
            throw new UpdateEntityException(ex);
        }

    }

    @Override
    @Transactional
    public Long create(CertificateDTO entity) {
        try {
            Certificate certificate = ToEntityConverter.convertToCertificate(entity);
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("name", certificate.getName())
                    .addValue("description", certificate.getDescription())
                    .addValue("price", certificate.getPrice())
                    .addValue("duration", certificate.getDuration());
            jdbcTemplate.update(SQL_INSERT_CREATE_CERTIFICATE, parameterSource, holder, new String[]{"id"});
            Long certificateId = holder.getKey().longValue();
            if (CollectionUtils.isNotEmpty(certificate.getTags())) {
                List<Long> tagsIdList = createTags(entity.getTags());
                AddLinkBetweenTagAndCertificate(certificateId, tagsIdList);
            }
            return certificateId;
        } catch (DataAccessException ex) {
            log.error("Request create certificate execution error", ex);
            throw new CreateEntityException(ex);
        }
    }

    private List<Long> createTags(Set<TagDTO> tagList) {
        List<Long> tagsIdList = new ArrayList<>();
        for (TagDTO tagDTO : tagList) {
            tagsIdList.add(tagDAO.create(tagDTO));
        }
        return tagsIdList;
    }

    private void AddLinkBetweenTagAndCertificate(Long certificateId, List<Long> tagsIdList) {
        tagsIdList.forEach(id -> {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("certificateId", certificateId)
                    .addValue("tagId", id);
            jdbcTemplate.update(SQL_INSERT_CREATE_CERTIFICATE_TAG, params);
        });
    }

    @Override
    public CertificateDTO findById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            Certificate certificate = jdbcTemplate.query(SQL_SELECT_FIND_BY_ID, parameterSource, new CertificateResultSetExtractor());
            return ToDTOConverter.convertToCertificateDTO(certificate);
        } catch (DataAccessException ex) {
            log.error("Request find certificate execution error", ex);
            throw new EntityRetrievalException(ex);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("id", id);
            jdbcTemplate.update(SQL_DELETE_CERTIFICATE, parameterSource);
        } catch (DataAccessException ex) {
            log.error("Request delete certificate execution error", ex);
            throw new DeleteEntityException(ex);
        }
    }

    @Override
    public List<CertificateDTO> findAll() {
        return getCertificateWithTags(SQL_SELECT_FIND_ALL);
    }

    @Override
    public List<CertificateDTO> findCertificateByParams(SearchAndSortParams params) {
        String query = SqlCreator.getQuerySelectFindByParams(params, BASIC_SQL_SELECT);
        return getCertificateWithTags(query);
    }

    private List<CertificateDTO> getCertificateWithTags(String query) {
        List<Certificate> tmp;
        try {
            tmp = jdbcTemplate.query(query, new CertificateListResultSetExtractor());
        } catch (DataAccessException ex) {
            log.error("Request find certificates execution error", ex);
            throw new EntityRetrievalException(ex);
        }
        List<CertificateDTO> certificateDTOList = tmp.stream()
                .map(ToDTOConverter::convertToCertificateDTO)
                .collect(Collectors.toList());
        List<CertificateDTO> result = new ArrayList<>();
        for (CertificateDTO certificateDTO : certificateDTOList) {
            CertificateDTO cert = certificateDTO;
            cert.setTags(findTagsByCertificateId(cert.getId()));
            result.add(cert);
        }
        return result;
    }

    private Set<TagDTO> findTagsByCertificateId(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        Set<Tag> tags = jdbcTemplate.query(SQL_SELECT_FIND_TAGS_BY_CERTIFICATE_ID, parameterSource, new TagListResultSetExtractor());
        return tags.stream()
                .map(ToDTOConverter::convertToTagDTO)
                .collect(Collectors.toSet());
    }
}
