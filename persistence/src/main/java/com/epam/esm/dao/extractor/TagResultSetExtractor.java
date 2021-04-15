package com.epam.esm.dao.extractor;

import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagResultSetExtractor implements ResultSetExtractor<Tag> {

    @Override
    public Tag extractData(ResultSet rs) throws SQLException, DataAccessException {
        Tag tag = null;
        while (rs.next()) {
            if (tag == null) {
                tag = Tag.builder()
                        .id(rs.getLong("tag.id"))
                        .name(rs.getString("tag.name"))
                        .build();
            }
        }
        return tag;
    }
}
