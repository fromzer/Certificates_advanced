package com.epam.esm.controller;

import com.epam.esm.hateoas.TagResource;
import com.epam.esm.model.Pageable;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.GiftTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Rest controller for Tags
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/tags", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TagController {

    private final GiftTagService tagService;
    private final TagResource tagResource;
//    private TagValidator tagValidator;

    @Autowired
    public TagController(GiftTagService tagService, TagResource tagResource) {
        this.tagService = tagService;
        this.tagResource = tagResource;
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(tagValidator);
//    }

    /**
     * Create tag
     *
     * @param tag the GiftTag
     * @return the response entity
     * @throws CreateResourceException the service exception
     */
    @PostMapping
    public ResponseEntity<Long> create(@Valid @RequestBody GiftTag tag) {
        return ResponseEntity.ok(tagService.create(tag));
    }

    /**
     * Delete tag
     *
     * @param id the GiftTag id
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<GiftTag> delete(@PathVariable @Min(value = 0) Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get tag by id
     *
     * @param id the GiftTag id
     * @return the tag
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<GiftTag>> getTagById(@PathVariable @Min(value = 0) Long id) {
        return ResponseEntity.ok(tagResource.toModel(tagService.findById(id)));
    }

    /**
     * Get all tags
     *
     * @return List of GiftTags
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<GiftTag>>> getAll(@ModelAttribute Pageable pageable) {
        return ResponseEntity.ok(tagResource.toCollectionModel(tagService.findAll(pageable)));
    }
}
