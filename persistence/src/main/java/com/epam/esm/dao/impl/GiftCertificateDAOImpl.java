package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.criteria.SearchAndSortCertificatePredicate;
import com.epam.esm.dao.criteria.SearchAndSortCertificateQuery;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.SearchAndSortCertificateParams;
import com.epam.esm.entity.Certificate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
public class GiftCertificateDAOImpl implements GiftCertificateDao<Certificate> {

    private final GiftDaoBean<Certificate> giftDao;

    @Autowired
    public GiftCertificateDAOImpl(GiftDaoBean giftDao) {
        this.giftDao = giftDao;
        giftDao.setClazz(Certificate.class);
    }

    @Override
    @Transactional
    public Long create(Certificate entity) {
        return giftDao.create(entity);
    }

    @Override
    public void delete(Certificate entity) {
        giftDao.delete(entity);
    }

    @Override
    public List<Certificate> findAll(Pageable pageable) {
        return giftDao.findAll(pageable);
    }

    @Override
    public List<Certificate> findEntitiesByParams(SearchAndSortCertificateParams params, Pageable pageable) {
        return giftDao.findEntityByParams(params, pageable, new SearchAndSortCertificatePredicate(), new SearchAndSortCertificateQuery());
    }

    @Override
    public Certificate findById(Long id) {
        return giftDao.findById(id);
    }

    @Override
    @Transactional
    public Certificate update(Certificate entity) {
        return giftDao.update(entity);
    }
}
