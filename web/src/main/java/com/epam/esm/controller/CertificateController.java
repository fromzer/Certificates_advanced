package com.epam.esm.controller;

import com.epam.esm.hateoas.CertificateResource;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.SearchAndSortCertificateParams;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.DeleteResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.UpdateResourceException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.utils.PageableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Rest controller for Certificates
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/certificates", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CertificateController {
    private final GiftCertificateService giftCertificateService;
    private final CertificateResource certificateResource;
    private final Validator certificateValidator;
    private final Validator paramsValidator;

    @Autowired
    public CertificateController(
            GiftCertificateService giftCertificateService,
            @Qualifier("certificateValidator") Validator certificateValidator,
            @Qualifier("searchAndSortParamsValidator") Validator paramsValidator,
            CertificateResource certificateResource) {
        this.giftCertificateService = giftCertificateService;
        this.certificateValidator = certificateValidator;
        this.paramsValidator = paramsValidator;
        this.certificateResource = certificateResource;
    }

    @InitBinder("certificate")
    public void initCertificateBinder(WebDataBinder binder) {
        binder.addValidators(certificateValidator);
    }

    @InitBinder("params")
    public void initSearchParamsBinder(WebDataBinder binder) {
        binder.addValidators(paramsValidator);
    }

    /**
     * Create certificate
     *
     * @param giftCertificate the certificate
     * @return new certificate's id
     * @throws CreateResourceException the service exception
     */
    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody GiftCertificate giftCertificate) {
        return ResponseEntity.ok(giftCertificateService.create(giftCertificate));
    }

    /**
     * Update certificate and optionally create received tags
     *
     * @param giftCertificate the certificate and optionally tags
     * @return certificate and tags
     * @throws UpdateResourceException the service exception
     */
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificate> update(@Valid @RequestBody GiftCertificate giftCertificate,
                                                  @PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(giftCertificateService.update(giftCertificate, id));
    }

    /**
     * Delete certificate
     *
     * @param id the certificate id
     * @return response entity
     * @throws DeleteResourceException   the service exception
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Min(value = 1) Long id) {
        giftCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get certificate by id
     *
     * @param id the GiftCertificate id
     * @return the giftTag
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<GiftCertificate>> getCertificateById(@PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(certificateResource.toModel(giftCertificateService.findById(id)));
    }

    /**
     * Get certificates by parameters
     *
     * @param params the search and sort params
     * @return list of giftCertificate
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<GiftCertificate>>> getCertificatesWithParameters(
            @Valid @ModelAttribute SearchAndSortCertificateParams params,
            @ModelAttribute Pageable pageable) {
        Pageable pagination = PageableUtils.setDefaultValueIfEmpty(pageable);
        return ResponseEntity
                .ok(certificateResource.toCollectionModel(
                        giftCertificateService.findCertificateByParams(params, pagination)));
    }
}
