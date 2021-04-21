package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftDAO;
import com.epam.esm.dao.GiftTagDao;
import com.epam.esm.dto.Pageable;
import com.epam.esm.entity.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
public class GiftTagDAOImpl implements GiftTagDao<Tag> {
    private static final String SQL_SELECT_FIND_NAME = "SELECT id, name FROM tag WHERE name=:name;";
    private static final String NAME = "name";

    private final GiftDAO<Tag> giftDao;

    @Autowired
    public GiftTagDAOImpl(GiftDaoBean giftDao) {
        this.giftDao = giftDao;
        giftDao.setClazz(Tag.class);
    }

    @Override
    public Long create(Tag entity) {
        return giftDao.create(entity);
    }

    @Override
    public void delete(Tag entity) {
        giftDao.delete(entity);
    }

    @Override
    public List<Tag> findAll(Pageable pageable) {
        return giftDao.findAll(pageable);
    }

    @Override
    public Tag findById(Long id) {
        return giftDao.findById(id);
    }

    @Override
    public Tag findByName(String name) {
        return giftDao.createNativeQuery(SQL_SELECT_FIND_NAME, NAME, name);
    }
}
