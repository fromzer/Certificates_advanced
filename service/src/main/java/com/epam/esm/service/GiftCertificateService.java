package com.epam.esm.service;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.SearchAndSortGiftCertificateOptions;

import java.util.List;

/**
 * Base interface for Certificates
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface GiftCertificateService extends GiftService<GiftCertificate> {
    /**
     * Update entity
     *
     * @param certificateDTO an DTO of business model
     * @return updated GiftCertificate
     * @throws UpdateResourceException if fail to update data
     */
    GiftCertificate update(GiftCertificate certificateDTO, Long id) throws UpdateResourceException;

    /**
     * Find entity
     *
     * @param options the search options
     * @return list of GiftCertificates
     * @throws ResourceNotFoundException if fail to retrieve data
     */
    List<GiftCertificate> findCertificateByParams(SearchAndSortGiftCertificateOptions options) throws ResourceNotFoundException;
}
