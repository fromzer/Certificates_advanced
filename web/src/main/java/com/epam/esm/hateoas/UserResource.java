package com.epam.esm.hateoas;

import com.epam.esm.controller.UserController;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.UserGift;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserResource implements SimpleRepresentationModelAssembler<UserGift> {
    private static final String FIND_BY_ID = "get_user_by_id";
    private static final String GET_ALL_USERS = "get_all_users";
    private static final String GET_MOST_POPULAR_USER_TAG = "get_most_popular_user_tag";
    private static final String GET_ORDERS = "get_user_orders";
    private static final String PAGE_OPTIONS = "page=1&size=20";

    @Override
    public void addLinks(EntityModel<UserGift> resource) {
        resource.add(linkTo(methodOn(UserController.class).getUserById(resource.getContent().getId())).withRel(FIND_BY_ID));
        resource.add(linkTo(methodOn(UserController.class).getAll(null)).withRel(GET_ALL_USERS));
        resource.add(linkTo(methodOn(UserController.class)
                .getMostPopularUserTag(resource.getContent().getId())).withRel(GET_MOST_POPULAR_USER_TAG));
        resource.add(linkTo(methodOn(UserController.class)
                .getUserOrders(resource.getContent().getId(), new Pageable(1, 20))).withRel(GET_ORDERS));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<UserGift>> resources) {
        UriComponentsBuilder componentsBuilder = linkTo(methodOn(UserController.class)
                .getAll(null))
                .toUriComponentsBuilder()
                .replaceQuery(PAGE_OPTIONS);
        componentsBuilder.encode();
        Link link = Link.of(componentsBuilder.toUriString());
        resources.add(link.withRel(GET_ALL_USERS));
    }
}
