package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftTag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateResource implements SimpleRepresentationModelAssembler<GiftCertificate> {
    private static final String FIND_BY_ID = "get_by_id";
    private static final String FIND_ALL = "get_all";
    private static final String FIND_BY_PARAMS = "get_by_params";
    private static final String CREATE = "create";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String SEARCH_SORT_PAGEABLE_PARAMS = "tags=&name=&description=&sort=&page=&size=";

    @Override
    public void addLinks(EntityModel<GiftCertificate> resource) {
        resource.add(linkTo(methodOn(CertificateController.class).getCertificateById(resource.getContent().getId())).withRel(FIND_BY_ID));
        resource.add(linkTo(methodOn(CertificateController.class).create(null)).withRel(CREATE));
        resource.add(linkTo(methodOn(CertificateController.class).update(null, resource.getContent().getId())).withRel(UPDATE));
        resource.add(linkTo(methodOn(CertificateController.class).delete(resource.getContent().getId())).withRel(DELETE));
        resource.add(linkTo(methodOn(CertificateController.class).getCertificatesWithParameters(null, null)).withRel(FIND_ALL));
        addTagLinks(resource);
    }

    private void addTagLinks(EntityModel<GiftCertificate> resource) {
        for (final GiftTag tag : resource.getContent().getTags()) {
            Link getTagById = linkTo(methodOn(TagController.class).getTagById(tag.getId())).withRel(FIND_BY_ID);
            tag.add(getTagById);
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<GiftCertificate>> resources) {
        UriComponentsBuilder componentsBuilder = linkTo(methodOn(CertificateController.class)
                .getCertificatesWithParameters(null, null))
                .toUriComponentsBuilder()
                .replaceQuery(SEARCH_SORT_PAGEABLE_PARAMS);
        componentsBuilder.encode();
        Link link = Link.of(componentsBuilder.toUriString());
        resources.add(link.withRel(FIND_BY_PARAMS));
        resources.add(linkTo(methodOn(CertificateController.class).getCertificatesWithParameters(null, null)).withRel(FIND_ALL));
    }
}
