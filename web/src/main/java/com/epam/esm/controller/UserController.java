package com.epam.esm.controller;

import com.epam.esm.dto.Pageable;
import com.epam.esm.model.GiftTag;
import com.epam.esm.model.UserGift;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGift> getUserById(@PathVariable @Min(value = 0) Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/name={name}")
    public ResponseEntity<UserGift> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    @GetMapping
    public ResponseEntity<List<UserGift>> getAll(@ModelAttribute Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }
}
