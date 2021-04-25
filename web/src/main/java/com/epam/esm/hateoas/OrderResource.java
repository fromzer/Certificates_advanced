package com.epam.esm.hateoas;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.UserController;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftOrder;
import com.epam.esm.model.UserGift;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderResource implements SimpleRepresentationModelAssembler<GiftOrder> {
    private static final String GET_CERTIFICATE_BY_ID = "get_certificate_by_id";
    private static final String GET_ORDER = "get_order";
    private static final String GET_ORDERS = "get_user_orders";
    private static final String PAGE_OPTIONS = "page=1&size=20";

    private final UserResource userResource;

    public OrderResource(UserResource userResource) {
        this.userResource = userResource;
    }

    @Override
    public void addLinks(EntityModel<GiftOrder> resource) {
        resource.add(linkTo(methodOn(UserController.class)
                .getUserOrder(resource.getContent().getUser().getId(), resource.getContent().getId())).withRel(GET_ORDER));
        GiftOrder giftOrder = resource.getContent();
        UserGift user = giftOrder.getUser();
        user.add(userResource.toModel(user).getLinks());
        List<GiftCertificate> certificates = giftOrder.getCertificates();
        for (GiftCertificate certificate : certificates) {
            certificate.add(linkTo(methodOn(CertificateController.class)
                    .getCertificateById(certificate.getId())).withRel(GET_CERTIFICATE_BY_ID));
        }
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<GiftOrder>> resources) {
        UriComponentsBuilder componentsBuilder = linkTo(methodOn(UserController.class)
                .getUserOrders(null, null))
                .toUriComponentsBuilder()
                .replaceQuery(PAGE_OPTIONS);
        componentsBuilder.encode();
        Link link = Link.of(componentsBuilder.toUriString());
        resources.add(link.withRel(GET_ORDERS));
    }
}
