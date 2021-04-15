package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate implements Entity{
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private ZonedDateTime createDate;
    private ZonedDateTime lastUpdateDate;
    private Set<Tag> tags;
}
