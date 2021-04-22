package com.epam.esm.controller;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftOrder;
import com.epam.esm.model.GiftOrderWithoutCertificatesAndUser;
import com.epam.esm.model.GiftTag;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.UserGift;
import com.epam.esm.service.GiftTagService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    public UserController(UserService userService, OrderService orderService, GiftTagService tagService) {
        this.userService = userService;
        this.orderService = orderService;
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGift> getUserById(@PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<Long> createOrder(@PathVariable @Min(value = 1) Long id,
                                            @Valid @RequestBody List<GiftCertificate> giftCertificates) {
        return ResponseEntity.ok(orderService.createOrder(id, giftCertificates));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<GiftOrder>> getUserOrders(@PathVariable @Min(value = 1) Long id,
                                                         @ModelAttribute Pageable pageable) {
        return ResponseEntity.ok(orderService.findUserOrders(id, pageable));
    }

    @GetMapping("/{id}/orders/{orderId}")
    public ResponseEntity<GiftOrderWithoutCertificatesAndUser> getUserOrder(@PathVariable @Min(value = 1) Long id,
                                                                            @PathVariable @Min(value = 1) Long orderId) {
        return ResponseEntity.ok(orderService.findUserOrderInfo(orderId, id));
    }

    @GetMapping("/{id}/tag")
    public ResponseEntity<GiftTag> getMostPopularUserTag(@PathVariable @Min(value = 1) Long id) {
        return ResponseEntity.ok(tagService.findMostPopularUserTag(id));
    }

    @GetMapping
    public ResponseEntity<List<UserGift>> getAll(@ModelAttribute Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }
}
