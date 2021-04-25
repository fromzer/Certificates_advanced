package com.epam.esm.hateoas;

import com.epam.esm.controller.TagController;
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
public class TagResource implements SimpleRepresentationModelAssembler<GiftTag> {
    private static final String GET_BY_ID = "get_tag_by_id";
    private static final String DELETE = "delete";
    private static final String CREATE = "create";
    private static final String GET_ALL = "get_all_tags";
    private static final String PAGE_OPTIONS = "page=1&size=20";

    @Override
    public void addLinks(EntityModel<GiftTag> resource) {
        resource.add(linkTo(methodOn(TagController.class).getTagById(resource.getContent().getId())).withRel(GET_BY_ID));
        resource.add(linkTo(methodOn(TagController.class).delete(resource.getContent().getId())).withRel(DELETE));
        resource.add(linkTo(methodOn(TagController.class).create(null)).withRel(CREATE));
        resource.add(linkTo(methodOn(TagController.class).getAll(null)).withRel(GET_ALL));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<GiftTag>> resources) {
        UriComponentsBuilder componentsBuilder = linkTo(methodOn(TagController.class)
                .getAll(null))
                .toUriComponentsBuilder()
                .replaceQuery(PAGE_OPTIONS);
        componentsBuilder.encode();
        Link link = Link.of(componentsBuilder.toUriString());
        resources.add(link.withRel(GET_ALL));
    }
}
