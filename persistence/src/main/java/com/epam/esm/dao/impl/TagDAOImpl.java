package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.extractor.TagListResultSetExtractor;
import com.epam.esm.dao.extractor.TagResultSetExtractor;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ExistEntityException;
import com.epam.esm.util.ToDTOConverter;
import com.epam.esm.util.ToEntityConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
public class TagDAOImpl implements TagDAO {
    private static final String SQL_SELECT_FIND_BY_ID = "SELECT id, name FROM tag WHERE id = :id;";
    private static final String SQL_SELECT_FIND_NAME = "SELECT id, name FROM tag WHERE name=:name;";
    private static final String SQL_SELECT_FIND_ALL = "SELECT id, name FROM tag";
    private static final String SQL_INSERT_CREATE_TAG = "INSERT INTO tag(name) VALUES (:name);";
    private static final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id = :id;";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Long create(TagDTO entity) {
        TagDTO dto = findByName(entity.getName());
        if (dto != null) {
            throw new ExistEntityException(entity.getName());
        }
        try {
            Tag tag = ToEntityConverter.convertToTag(entity);
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("name", tag.getName());
            jdbcTemplate.update(SQL_INSERT_CREATE_TAG, parameterSource, holder, new String[]{"id"});
            return holder.getKey().longValue();
        } catch (DataAccessException ex) {
            log.error("Request create tag execution error", ex);
            throw new CreateEntityException(ex);
        }
    }

    @Override
    public TagDTO findById(Long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id);
        try {
            Tag tag = jdbcTemplate.query(SQL_SELECT_FIND_BY_ID, parameterSource, new TagResultSetExtractor());
            return ToDTOConverter.convertToTagDTO(tag);
        } catch (DataAccessException ex) {
            log.error("Tag is not found", ex);
            throw new EntityRetrievalException(ex);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            SqlParameterSource parameterSource = new MapSqlParameterSource()
                    .addValue("id", id);
            jdbcTemplate.update(SQL_DELETE_TAG, parameterSource);
        } catch (DataAccessException ex) {
            log.error("Tag delete request error", ex);
            throw new DeleteEntityException(ex);
        }
    }

    @Override
    public List<TagDTO> findAll() {
        Set<Tag> tags = jdbcTemplate.query(SQL_SELECT_FIND_ALL, new TagListResultSetExtractor());
        return tags.stream()
                .map(ToDTOConverter::convertToTagDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TagDTO findByName(String name) throws EntityRetrievalException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        Tag tag = jdbcTemplate.query(SQL_SELECT_FIND_NAME, params, new TagResultSetExtractor());
        return ToDTOConverter.convertToTagDTO(tag);
    }
}
