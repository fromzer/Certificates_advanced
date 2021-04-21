package com.epam.esm.dao.criteria;

import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.entity.Certificate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class SearchAndSortQuery implements QueryConstructor<SearchAndSortParams, Certificate>{
    private static final String MINUS = "-";
    private static final String EMPTY = "";
    @Override
    public void createQuery(SearchAndSortParams params, CriteriaBuilder cb, CriteriaQuery<Certificate> cr, Root<Certificate> root, Predicate predicate) {
        if (params.getSort() != null) {
            paramsSortIsNotEmpty(params, cb, cr, root, predicate);
        } else {
            isEmptySortParams(cb, cr, root, predicate);
        }
    }

    private void paramsSortIsNotEmpty(SearchAndSortParams params, CriteriaBuilder cb, CriteriaQuery<Certificate> cr, Root<Certificate> root, Predicate predicateSearchParams) {
        //
        String sortColumn = params.getSort().replace(MINUS, EMPTY).trim();
        if (params.getSort().startsWith(MINUS)) {
            cr.select(root).where(cb.and(predicateSearchParams)).orderBy(cb.desc(root.get(sortColumn)));
        } else {
            cr.select(root).where(cb.and(predicateSearchParams)).orderBy(cb.asc(root.get(sortColumn)));
        }
        //todo Unable to locate Attribute  with the the given name [qweduration] ??
    }

    private void isEmptySortParams(CriteriaBuilder cb, CriteriaQuery<Certificate> cr, Root<Certificate> root, Predicate predicateSearchParams) {
        if (predicateSearchParams != null) {
            cr.select(root).where(cb.and(predicateSearchParams));
        } else {
            cr.select(root).where();
        }
    }
}
