package com.epam.esm.controller;

import com.epam.esm.hateoas.OrderResource;
import com.epam.esm.hateoas.TagResource;
import com.epam.esm.hateoas.UserResource;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftOrder;
import com.epam.esm.model.GiftOrderWithoutCertificatesAndUser;
import com.epam.esm.model.GiftTag;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.UserGift;
import com.epam.esm.service.GiftTagService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.PageableUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
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

@RestController
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final GiftTagService tagService;
    private final UserResource userResource;
    private final OrderResource orderResource;
    private final TagResource tagResource;
    private final Validator certificateValidator;

    public UserController(UserService userService,
                          OrderService orderService,
                          GiftTagService tagService,
                          UserResource userResource,
                          OrderResource orderResource,
                          TagResource tagResource,
                          @Qualifier("certificateValidator") Validator certificateValidator) {
        this.userService = userService;
        this.orderService = orderService;
        this.tagService = tagService;
        this.userResource = userResource;
        this.orderResource = orderResource;
        this.tagResource = tagResource;
        this.certificateValidator = certificateValidator;
    }

    @InitBinder("certificate")
    public void initCertificateBinder(WebDataBinder binder) {
        binder.addValidators(certificateValidator);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserGift>> getUserById(@PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(userResource.toModel(userService.findById(id)));
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<Long> createOrder(@PathVariable @Min(value = 1) Long id,
                                            @Valid @RequestBody List<GiftCertificate> giftCertificates) {
        return ResponseEntity.ok(orderService.createOrder(id, giftCertificates));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<CollectionModel<EntityModel<GiftOrder>>> getUserOrders(@PathVariable @Min(value = 1) Long id,
                                                                                 @ModelAttribute Pageable pageable) {
        return ResponseEntity.ok(orderResource.toCollectionModel(
                orderService.findUserOrders(id, PageableUtils.setDefaultValueIfEmpty(pageable))));
    }

    @GetMapping("/{id}/orders/{orderId}")
    public ResponseEntity<GiftOrderWithoutCertificatesAndUser> getUserOrder(@PathVariable @Min(value = 1) Long id,
                                                                            @PathVariable @Min(value = 1) Long orderId) {
        return ResponseEntity.ok(orderService.findUserOrderInfo(orderId, id));
    }

    @GetMapping("/{id}/tag")
    public ResponseEntity<EntityModel<GiftTag>> getMostPopularUserTag(@PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(tagResource.toModel(tagService.findMostPopularUserTag(id)));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserGift>>> getAll(@ModelAttribute Pageable pageable) {
        return ResponseEntity.ok(userResource.toCollectionModel(
                userService.findAll(PageableUtils.setDefaultValueIfEmpty(pageable))));
    }
}
