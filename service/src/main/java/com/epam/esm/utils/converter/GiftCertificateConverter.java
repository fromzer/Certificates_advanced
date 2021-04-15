package com.epam.esm.utils.converter;


import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.model.GiftCertificate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class GiftCertificateConverter {

    public static GiftCertificate convertToServiceLayerEntity(CertificateDTO certificateDTO) {
        return certificateDTO == null ? null :
                GiftCertificate.builder()
                        .id(certificateDTO.getId())
                        .name(certificateDTO.getName())
                        .description(certificateDTO.getDescription())
                        .price(certificateDTO.getPrice())
                        .duration(certificateDTO.getDuration())
                        .createDate(certificateDTO.getCreateDate())
                        .lastUpdateDate(certificateDTO.getLastUpdateDate())
                        .tags(CollectionUtils.isNotEmpty(certificateDTO.getTags()) ?
                                certificateDTO.getTags().stream()
                                        .map(GiftTagConverter::convertToServiceLayerEntity)
                                        .collect(Collectors.toSet())
                                : new LinkedHashSet<>())
                        .build();
    }

    public static CertificateDTO convertToPersistenceLayerEntity(GiftCertificate giftCertificate) {
        return giftCertificate == null ? null :
                CertificateDTO.builder()
                        .id(giftCertificate.getId())
                        .name(giftCertificate.getName())
                        .description(giftCertificate.getDescription())
                        .price(giftCertificate.getPrice())
                        .duration(giftCertificate.getDuration())
                        .createDate(giftCertificate.getCreateDate())
                        .lastUpdateDate(giftCertificate.getLastUpdateDate())
                        .tags(CollectionUtils.isNotEmpty(giftCertificate.getTags()) ?
                                giftCertificate.getTags().stream()
                                        .map(GiftTagConverter::convertToPersistenceLayerEntity)
                                        .collect(Collectors.toSet())
                                : new LinkedHashSet<>())
                        .build();
    }
}
