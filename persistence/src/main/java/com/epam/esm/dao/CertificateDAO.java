package com.epam.esm.dao;

import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.UpdateEntityException;

import java.util.List;

/**
 * Base interface class for CertificateDAO
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface CertificateDAO extends Dao<CertificateDTO> {
    /**
     * Update entry in DB
     *
     * @param certificateDTO an DTO of business model
     * @return updated certificateDTO
     * @throws UpdateEntityException if fail to update data in DB
     */
    CertificateDTO update(CertificateDTO certificateDTO) throws UpdateEntityException;

    /**
     * Find entry in table
     *
     * @param params the search options
     * @return list of CertificateDTO
     * @throws EntityRetrievalException if fail to retrieve data from DB
     */
    List<CertificateDTO> findCertificateByParams(SearchAndSortParams params) throws EntityRetrievalException;
}
