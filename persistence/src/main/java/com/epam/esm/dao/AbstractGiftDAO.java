package com.epam.esm.dao;

import com.epam.esm.dao.criteria.PredicateConstructor;
import com.epam.esm.dao.criteria.QueryConstructor;
import com.epam.esm.dto.Pageable;
import com.epam.esm.dto.SearchAndSortParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Persistable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class AbstractGiftDAO<T extends Persistable<? extends Serializable>> implements GiftDAO<T> {
    private static final int ONE = 1;
    private Class<T> clazz;

    @PersistenceContext
    EntityManager entityManager;

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public EntityManager getEm() {
        return entityManager;
    }

    public T findById(Long id) {
        return id == null ? null : getEm().find(clazz, id);
    }

    public T createNativeQuery(String query, String param, String value) {
        return (T) getEm().createNativeQuery(query, clazz)
                .setParameter(param, value)
                .getSingleResult();
    }

    public Long create(T entity) {
        getEm().persist(entity);
        return (Long) entity.getId();
    }

    public T update(T entity) {
        return getEm().merge(entity);
    }

    public List<T> findAll(Pageable pageable) {
        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(clazz);
        Root<T> root = cr.from(clazz);
        cr.select(root);
        Query query = getEm().createQuery(cr);
        query.setFirstResult((pageable.getPage() - ONE) * pageable.getSize());
        query.setMaxResults(pageable.getSize());
        return query.getResultList();
    }

    @Override
    public List<T> findEntityByParams(SearchAndSortParams params, Pageable pageable,
                                      PredicateConstructor predicateConstructor, QueryConstructor queryConstructor) {
        CriteriaBuilder cb = getEm().getCriteriaBuilder();
        CriteriaQuery<T> cr = cb.createQuery(clazz);
        Root<T> root = cr.from(clazz);
        Predicate predicateSearchParams = predicateConstructor.createPredicate(params, cb, root, cr);
        queryConstructor.createQuery(params, cb, cr, root, predicateSearchParams);
        Query query = getEm().createQuery(cr);
        query.setFirstResult((pageable.getPage() - ONE) * pageable.getSize());
        query.setMaxResults(pageable.getSize());
        return query.getResultList();
    }

    public void delete(T entity) {
        getEm().remove(entity);
    }
}
