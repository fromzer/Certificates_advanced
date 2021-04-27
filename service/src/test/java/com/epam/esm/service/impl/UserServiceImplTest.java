package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityRetrievalException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftTag;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.UserGift;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private UserServiceImpl userService;
    @Mock
    private UserDaoImpl userDao;
    private Pageable pageable;
    private ModelMapper modelMapper;
    private User excepted;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        userService = new UserServiceImpl(userDao, modelMapper);
        pageable = new Pageable(1,10);
        excepted = User.builder()
                .id(2l)
                .login("alex")
                .firstName("Alex")
                .lastName("Pusher")
                .build();
    }

    @Test
    void shouldFindUserById() throws EntityRetrievalException, ResourceNotFoundException {
        when(userDao.findById(anyLong())).thenReturn(excepted);
        UserGift actual = userService.findById(2l);
        assertEquals(excepted.getLogin(), actual.getLogin());
    }

    @Test
    void shouldNotFindUserById() throws EntityRetrievalException, ResourceNotFoundException {
        when(userDao.findById(anyLong())).thenReturn(excepted);
        UserGift actual = userService.findById(1L);
        assertNotEquals(excepted, actual);
    }

    @Test
    void shouldFindAllUsers() throws EntityRetrievalException, ResourceNotFoundException {
        List<User> users = new ArrayList<>();
        users.add(excepted);
        when(userDao.findAll(pageable)).thenReturn(users);
        assertEquals(1, userService.findAll(pageable).size());
    }
}