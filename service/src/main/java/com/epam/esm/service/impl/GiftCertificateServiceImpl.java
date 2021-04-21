package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.dto.Pageable;
import com.epam.esm.dto.SearchAndSortParams;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.CreateEntityException;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteEntityException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.GiftServiceUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private static final Logger logger = LoggerFactory.getLogger(GiftCertificateServiceImpl.class);

    private final GiftCertificateDAOImpl giftCertificateDAO;
    private final ModelMapper modelMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAOImpl giftCertificateDAO, ModelMapper modelMapper) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate, Long id) throws UpdateResourceException {
        GiftCertificate existing = findById(id);
        if (existing != null) {
            Set<GiftTag> tags = existing.getTags();
            GiftServiceUtils.copyNonNullProperties(giftCertificate, existing);
            existing.getTags().addAll(tags);
        }
        try {
            Certificate modified = giftCertificateDAO.update(modelMapper.map(existing, Certificate.class));
            return modelMapper.map(modified, GiftCertificate.class);
        } catch (ResourceNotFoundException e) {
            logger.error("Failed to update certificate", e);
            throw new UpdateResourceException("Failed to update certificate", e);
        }
    }

    @Override
    public Long create(GiftCertificate giftCertificate) throws CreateResourceException {
        giftCertificate.setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        giftCertificate.setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        try {
            return giftCertificateDAO.create(modelMapper.map(giftCertificate, Certificate.class));
        } catch (CreateEntityException e) {
            logger.error("Failed to create certificate", e);
            throw new CreateResourceException("Failed to create certificate", e);
        }
    }

    @Override
    public GiftCertificate findById(Long id) throws ResourceNotFoundException {
        try {
            Certificate byId = giftCertificateDAO.findById(id);
            GiftCertificate giftCertificateById = modelMapper.map(byId, GiftCertificate.class);
            if (giftCertificateById == null) {
                throw new ResourceNotFoundException("Certificate is not found");
            }
            return giftCertificateById;
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificate by id", e);
            throw new ResourceNotFoundException("Failed to find certificate by id", e);
        }
    }

    @Override
    public void delete(Long id) throws DeleteResourceException {
        try {
            Certificate byId = giftCertificateDAO.findById(id);
            if (byId != null) {
                giftCertificateDAO.delete(byId);
            } else {
                throw new ResourceNotFoundException();
            }
        } catch (DeleteEntityException | ResourceNotFoundException e) {
            logger.error("Failed to delete certificate", e);
            throw new DeleteResourceException("Failed to delete certificate", e);
        }
    }

    @Override
    public List<GiftCertificate> findAll(Pageable pageable) throws ResourceNotFoundException {

        List<Certificate> certificates = (pageable.getPage() <= 0 || pageable.getSize() <= 0) ?
                null :
                giftCertificateDAO.findAll(pageable);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(certificates)) {
            giftCertificates = certificates.stream()
                    .map(cert -> modelMapper.map(cert, GiftCertificate.class))
                    .collect(Collectors.toList());
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findCertificateByParams(SearchAndSortParams params, Pageable pageable) throws ResourceNotFoundException {
        try {
            List<GiftCertificate> giftCertificates;
            if (Stream.of(params.getTag(), params.getName(), params.getDescription(),
                    params.getSort())
                    .allMatch(Objects::isNull)) {
                giftCertificates = findAll(pageable);
            } else {
                giftCertificates = giftCertificateDAO
                        .findEntitiesByParams(params, pageable).stream()
                        .map(certificate -> modelMapper.map(certificate, GiftCertificate.class))
                        .collect(Collectors.toList());
            }
            return giftCertificates;
        } catch (EntityRetrievalException e) {
            logger.error("Failed to find certificate by params", e);
            throw new ResourceNotFoundException("Failed to find certificate by params", e);
        }
    }

}
