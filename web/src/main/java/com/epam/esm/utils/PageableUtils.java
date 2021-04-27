package com.epam.esm.utils;

import com.epam.esm.exception.PaginationSpecifiedException;
import com.epam.esm.model.Pageable;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PageableUtils {
    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    public static Pageable setDefaultValueIfEmpty(Pageable pageable) {
        Pageable pagination = (pageable.getSize() == null) || (pageable.getPage() == null) ?
                new Pageable(DEFAULT_PAGE, DEFAULT_PAGE_SIZE) :
                pageable;
        check(pagination);
        return pagination;
    }

    private static void check(Pageable pageable) {
        if (pageable.getPage() < 0 || pageable.getSize() < 0) {
            throw new PaginationSpecifiedException();
        }
    }
}
