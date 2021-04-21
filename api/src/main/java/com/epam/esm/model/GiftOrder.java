package com.epam.esm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftOrder {
    private Long id;
    private BigDecimal cost;
    private ZonedDateTime purchaseDate;
    private UserGift user;
    private List<GiftCertificate> certificates;
}
