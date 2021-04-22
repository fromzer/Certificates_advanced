package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.model.Pageable;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.UserGift;
import com.epam.esm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDaoImpl userDao;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserDaoImpl userDao, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserGift findById(Long id) throws ResourceNotFoundException {
        return modelMapper.map(userDao.findById(id), UserGift.class);
    }

    @Override
    public List<UserGift> findAll(Pageable pageable) throws ResourceNotFoundException {
        List<UserGift> users = userDao.findAll(pageable).stream()
                .map(user -> modelMapper.map(user, UserGift.class))
                .collect(Collectors.toList());
        return users;
    }
}
