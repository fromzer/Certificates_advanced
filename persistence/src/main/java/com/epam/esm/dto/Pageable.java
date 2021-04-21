package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pageable {
    private int page;
    private int size;
}
