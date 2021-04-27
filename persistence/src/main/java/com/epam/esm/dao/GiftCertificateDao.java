package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.SearchAndSortCertificateParams;

import java.util.List;

public interface GiftCertificateDao {
    Long create(Certificate entity);

    Certificate update(Certificate entity);

    Certificate findById(Long id);

    List<Certificate> findAll(Pageable pageable);

    List<Certificate> findEntitiesByParams(SearchAndSortCertificateParams params, Pageable pageable);

    void delete(Certificate entity);
}
