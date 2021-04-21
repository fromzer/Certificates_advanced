package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.criteria.SearchAndSortPredicate;
import com.epam.esm.dao.criteria.SearchAndSortQuery;
import com.epam.esm.dto.Pageable;
import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public List<Certificate> findEntitiesByParams(SearchAndSortParams params, Pageable pageable) {
        return giftDao.findEntityByParams(params, pageable, new SearchAndSortPredicate(), new SearchAndSortQuery());
    }

    @Override
    public Certificate findById(Long id) {
        return giftDao.findById(id);
    }

    @Override
    public Certificate update(Certificate entity) {
        return giftDao.update(entity);
    }
}
