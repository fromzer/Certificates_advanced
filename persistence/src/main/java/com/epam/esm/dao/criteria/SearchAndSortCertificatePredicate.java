package com.epam.esm.dao.criteria;

import com.epam.esm.model.SearchAndSortCertificateParams;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchAndSortCertificatePredicate implements PredicateConstructor<SearchAndSortCertificateParams, Certificate> {
    private static final String NAME = "name";
    private static final String TAGS = "tags";
    private static final String DESCRIPTION = "description";
    private static final String PERCENT = "%";

    @Override
    public Predicate createPredicate(SearchAndSortCertificateParams params, CriteriaBuilder cb, Root<Certificate> root) {
        return Optional.ofNullable(params.getTag())
                .map(tagName -> searchByTagsName(cb, root, tagName))
                .or(() -> Optional.ofNullable(params.getName())
                        .map(name -> cb.like(root.get(NAME), PERCENT + name + PERCENT)))
                .or(() -> Optional.ofNullable(params.getDescription())
                        .map(description -> cb.like(root.get(DESCRIPTION), PERCENT + description + PERCENT)))
                .orElse(cb.like(root.get(NAME), PERCENT));
    }

    private Predicate searchByTagsName(CriteriaBuilder cb, Root<Certificate> root, String tagName) {
        Join<Certificate, Tag> tagJoin = root.join(TAGS, JoinType.INNER);
        String[] tagsName = tagName.split(",");
        List<Predicate> predicateList = Arrays.stream(tagsName)
                .map(name -> {
                    Join<Certificate, Tag> tagsJoin = root.join(TAGS, JoinType.INNER);
                    return cb.equal(tagsJoin.get(NAME), name);
                })
                .collect(Collectors.toList());
        Predicate[] predicates = new Predicate[predicateList.size()];
        return tagsName.length > 1 ?
                cb.and(predicateList.toArray(predicates)) :
                cb.equal(tagJoin.get(NAME), tagName);
    }
}