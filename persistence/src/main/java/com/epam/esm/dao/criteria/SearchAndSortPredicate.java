package com.epam.esm.dao.criteria;

import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SearchAndSortPredicate implements PredicateConstructor<SearchAndSortParams, Certificate> {
    private static final String NAME = "name";
    private static final String TAGS = "tags";
    private static final String DESCRIPTION = "description";
    private static final String PERCENT = "%";

    @Override
    public Predicate createPredicate(SearchAndSortParams params, CriteriaBuilder cb, Root<Certificate> root, CriteriaQuery<Certificate> cr) {
        Predicate searchPredicate = cb.like(root.get(NAME), PERCENT);
        if (params.getTag() != null) {
            Join<Certificate, Tag> join = root.join(TAGS);
            searchPredicate = cb.equal(join.get(NAME), params.getTag());
        } else if (params.getName() != null) {
            searchPredicate = cb.like(root.get(NAME), PERCENT + params.getName() + PERCENT);
        } else if (params.getDescription() != null) {
            searchPredicate = cb.like(root.get(DESCRIPTION), PERCENT + params.getDescription() + PERCENT);
        }
        return searchPredicate;
    }
}
