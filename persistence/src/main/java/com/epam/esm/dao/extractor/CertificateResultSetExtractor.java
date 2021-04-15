package com.epam.esm.dao.extractor;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.LinkedHashSet;
import java.util.Set;

public class CertificateResultSetExtractor implements ResultSetExtractor<Certificate> {

    @Override
    public Certificate extractData(ResultSet rs) throws SQLException {
        Set<Tag> tagSet = new LinkedHashSet<>();
        Certificate certificate = null;
        while (rs.next()) {
            if (certificate == null) {
                certificate = Certificate.builder()
                        .id(rs.getLong("gift_certificate.id"))
                        .name(rs.getString("gift_certificate.name"))
                        .description(rs.getString("gift_certificate.description"))
                        .price(rs.getBigDecimal("gift_certificate.price"))
                        .duration(rs.getInt("gift_certificate.duration"))
                        .createDate((rs.getTimestamp("gift_certificate.create_date").toInstant().atZone(ZoneId.systemDefault())))
                        .lastUpdateDate((rs.getTimestamp("gift_certificate.last_update_date").toInstant().atZone(ZoneId.systemDefault())))
                        .build();
            }
            Long tagId = rs.getLong("tag.id");
            if (tagId > 0) {
                Tag tag = Tag.builder()
                        .id(rs.getLong("tag.id"))
                        .name(rs.getString("tag.name"))
                        .build();
                tagSet.add(tag);
            }
        }
        if (CollectionUtils.isNotEmpty(tagSet)) {
            certificate.setTags(tagSet);
        }
        return certificate;
    }
}
