package com.epam.esm.dao.extractor;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class CertificateListResultSetExtractor implements ResultSetExtractor<List<Certificate>> {

    @Override
    public List<Certificate> extractData(ResultSet rs) throws SQLException {
        List<Certificate> certificates = new ArrayList<>();
        while (rs.next()) {
            Certificate certificate = Certificate.builder()
                    .id(rs.getLong("gift_certificate.id"))
                    .name(rs.getString("gift_certificate.name"))
                    .description(rs.getString("gift_certificate.description"))
                    .price(rs.getBigDecimal("gift_certificate.price"))
                    .duration(rs.getInt("gift_certificate.duration"))
                    .createDate((rs.getTimestamp("gift_certificate.create_date").toInstant().atZone(ZoneId.systemDefault())))
                    .lastUpdateDate((rs.getTimestamp("gift_certificate.last_update_date").toInstant().atZone(ZoneId.systemDefault())))
                    .tags(new LinkedHashSet<>())
                    .build();
            if (!certificates.contains(certificate)) {
                certificates.add(certificate);
            }
        }
        return certificates;
    }
}
