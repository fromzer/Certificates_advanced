package com.epam.esm.dao.criteria;

import com.epam.esm.model.SearchAndSortParams;
import org.springframework.data.domain.Persistable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;

public interface PredicateConstructor<T extends SearchAndSortParams, K extends Persistable<? extends Serializable>> {

    Predicate createPredicate(T params, CriteriaBuilder cb, Root<K> root);
}
