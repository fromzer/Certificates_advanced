package com.epam.esm.controller;

import com.epam.esm.dto.Pageable;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;
import com.epam.esm.service.GiftTagService;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Rest controller for Tags
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
@RestController
@RequestMapping(value = "/tags", produces = {MediaType.APPLICATION_JSON_VALUE})
public class TagController {

    private GiftTagService tagService;
//    private TagValidator tagValidator;

    @Autowired
    public TagController(GiftTagService tagService) {
        this.tagService = tagService;
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
    public ResponseEntity<Object> delete(@PathVariable @Min(value = 0) Long id) {
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
    public ResponseEntity<GiftTag> getTagById(@PathVariable @Min(value = 0) Long id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    /**
     * Get all tags
     *
     * @return List of GiftTags
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping
    public ResponseEntity<List<GiftTag>> getAll(@ModelAttribute Pageable pageable) {
        return ResponseEntity.ok(tagService.findAll(pageable));
    }
}
