package com.epam.esm.service;

import com.epam.esm.dto.Pageable;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.UserGift;

import java.util.List;

public interface UserService {
    UserGift findById(Long id) throws ResourceNotFoundException;

    List<UserGift> findAll(Pageable pageable) throws ResourceNotFoundException;

    UserGift findByName(String name) throws ResourceNotFoundException;
}
