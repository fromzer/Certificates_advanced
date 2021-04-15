package com.epam.esm.dao.impl;

import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.entity.Certificate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlCreator {
    private static final String ORDER_BY = " ORDER BY gift_certificate.";
    private static final String WHERE_TAG_NAME = "WHERE tag.name LIKE ";
    private static final String WHERE_CERTIFICATE_NAME = "WHERE gift_certificate.name LIKE ";
    private static final String WHERE_CERTIFICATE_DESCRIPTION = "WHERE gift_certificate.description LIKE ";

    public static String getQuerySelectFindByParams(SearchAndSortParams params, String sqlRequest) {
        if (params.getSort() == null) {
            params.setSort("name,ASC");
        }
        String[] sortParams = params.getSort().split(",");
        StringBuilder sb = new StringBuilder();
        sb.append(sqlRequest);
        if (params.getTag() != null) {
            sb.append(WHERE_TAG_NAME + "'%" + params.getTag() + "%'" + ORDER_BY + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else if (params.getName() != null) {
            sb.append(WHERE_CERTIFICATE_NAME + "'%" + params.getName() + "%'" + ORDER_BY + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else if (params.getDescription() != null) {
            sb.append(WHERE_CERTIFICATE_DESCRIPTION + "'%" + params.getDescription() + "%'" + ORDER_BY + sortParams[0] + " " + sortParams[1]);
            return sb.toString();
        } else {
            sb.append(ORDER_BY + sortParams[0] + " " + sortParams[1]);
        }
        return sb.toString();
    }

    public static String getQueryUpdateByPart(Certificate certificate) {
        String sqlRequest = "UPDATE gift_certificate SET ";
        String update = " last_update_date = NOW() ";
        String where = "WHERE id = :id";
        StringBuilder sb = new StringBuilder();
        sb.append(sqlRequest);
        sb.append(certificate.getName() == null ? "" : "name = '" + certificate.getName() + "', ");
        sb.append(certificate.getDescription() == null ? "" : "description = '" + certificate.getDescription() + "', ");
        sb.append(certificate.getPrice() == null ? "" : "price = " + certificate.getPrice() + ", ");
        sb.append(certificate.getDuration() == null ? "" : "duration = " + certificate.getDuration() + ", ");
        sb.append(update);
        sb.append(where);
        return sb.toString();
    }
}
