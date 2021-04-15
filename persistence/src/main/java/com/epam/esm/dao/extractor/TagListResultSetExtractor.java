package com.epam.esm.dao.extractor;

import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class TagListResultSetExtractor implements ResultSetExtractor<Set<Tag>> {
    @Override
    public Set<Tag> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Set<Tag> tags = new LinkedHashSet<>();
        while (rs.next()) {
            Tag tag = Tag.builder()
                    .id(rs.getLong("tag.id"))
                    .name(rs.getString("tag.name"))
                    .build();
            tags.add(tag);
        }
        return tags;
    }
}
