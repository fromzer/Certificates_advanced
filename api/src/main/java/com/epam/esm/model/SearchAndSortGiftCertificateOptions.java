package com.epam.esm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchAndSortGiftCertificateOptions {
    private String tag;
    private String name;
    private String description;
    private String sort;
}
