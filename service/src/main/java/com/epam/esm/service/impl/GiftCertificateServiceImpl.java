package com.epam.esm.service.impl;


import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.SearchAndSortGiftCertificateOptions;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.converter.GiftCertificateConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger logger = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);
    private CertificateDAO certificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate certificateDTO, Long id) throws UpdateResourceException {
        findById(id);
        CertificateDTO dto;
        try {
            CertificateDTO certDTO = GiftCertificateConverter.convertToPersistenceLayerEntity(certificateDTO);
            certDTO.setId(id);
            dto = certificateDAO.update(certDTO);
        } catch (ResourceNotFoundException e) {
            logger.error("Failed to update certificate", e);
            throw new UpdateResourceException("Failed to update certificate", e);
        }
        return GiftCertificateConverter.convertToServiceLayerEntity(dto);
    }

    @Override
    @Transactional
    public Long create(GiftCertificate entity) throws CreateResourceException {
        try {
            return certificateDAO.create(GiftCertificateConverter.convertToPersistenceLayerEntity(entity));
        } catch (CreateEntityException e) {
            logger.error("Failed to create certificate", e);
            throw new CreateResourceException("Failed to create certificate", e);
        }
    }

    @Override
    public GiftCertificate findById(Long id) throws ResourceNotFoundException {
        try {
            CertificateDTO certificateDTO = certificateDAO.findById(id);
            if (certificateDTO == null) {
                throw new ResourceNotFoundException("Certificate is not found");
            }
            return GiftCertificateConverter.convertToServiceLayerEntity(certificateDTO);
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificate by id", e);
            throw new ResourceNotFoundException("Failed to find certificate by id", e);
        }
    }

    @Override
    public void delete(Long id) throws DeleteResourceException {
        try {
            if (findById(id) != null) {
                certificateDAO.delete(id);
            } else {
                throw new ResourceNotFoundException();
            }
        } catch (DeleteEntityException | ResourceNotFoundException e) {
            logger.error("Failed to delete certificate", e);
            throw new DeleteResourceException("Failed to delete certificate", e);
        }
    }

    @Override
    public List<GiftCertificate> findAll() throws ResourceNotFoundException {
        List<CertificateDTO> certificates = certificateDAO.findAll();
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(certificates)) {
            giftCertificates = certificateDAO.findAll().stream()
                    .map(GiftCertificateConverter::convertToServiceLayerEntity)
                    .collect(Collectors.toList());
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findCertificateByParams(SearchAndSortGiftCertificateOptions options) throws ResourceNotFoundException {
        try {
            List<GiftCertificate> giftCertificates;
            if (Stream.of(options.getTag(), options.getName(), options.getDescription(), options.getSort())
                    .allMatch(Objects::isNull)) {
                giftCertificates = findAll();
            } else {
                SearchAndSortParams searchAndSortParams = SearchAndSortParams.builder()
                        .tag(options.getTag())
                        .name(options.getName())
                        .description(options.getDescription())
                        .sort(options.getSort())
                        .build();
                giftCertificates = certificateDAO
                        .findCertificateByParams(searchAndSortParams).stream()
                        .map(GiftCertificateConverter::convertToServiceLayerEntity)
                        .collect(Collectors.toList());
            }
            return giftCertificates;
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificate by params", e);
            throw new ResourceNotFoundException("Failed to find certificate by params", e);
        }
    }
}
