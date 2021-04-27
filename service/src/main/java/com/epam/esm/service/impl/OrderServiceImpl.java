package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftOrder;
import com.epam.esm.model.GiftOrderWithoutCertificatesAndUser;
import com.epam.esm.model.Pageable;
import com.epam.esm.model.SearchOrderByUserIdParams;
import com.epam.esm.model.UserGift;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDaoImpl orderDao;
    private final ModelMapper mapper;
    private final UserDaoImpl userDao;
    private final GiftCertificateService certificateService;


    @Autowired
    public OrderServiceImpl(OrderDaoImpl orderDao, ModelMapper mapper, GiftCertificateService certificateService, UserDaoImpl userDao) {
        this.orderDao = orderDao;
        this.mapper = mapper;
        this.certificateService = certificateService;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public Long createOrder(Long id, List<GiftCertificate> giftCertificates) throws CreateResourceException {
        return Optional.ofNullable(userDao.findById(id))
                .map(user -> buildGiftOrder(giftCertificates, user))
                .map(order -> mapper.map(order, Order.class))
                .map(orderDao::create)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private GiftOrder buildGiftOrder(List<GiftCertificate> giftCertificates, User user) {
        List<GiftCertificate> certificates = giftCertificates.stream()
                .map(GiftCertificate::getId)
                .map(certificateService::findById)
                .collect(Collectors.toList());
        BigDecimal cost = certificates.stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));
        return GiftOrder.builder()
                .user(mapper.map(user, UserGift.class))
                .certificates(certificates)
                .cost(cost)
                .build();
    }

    @Override
    public GiftOrderWithoutCertificatesAndUser findUserOrderInfo(Long orderId, Long userId) throws ResourceNotFoundException {
        return Optional.ofNullable(orderDao.findByUserIdAndOrderId(orderId, userId))
                .map(byId -> mapper.map(byId, GiftOrderWithoutCertificatesAndUser.class))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<GiftOrder> findUserOrders(Long id, Pageable pageable) {
        Optional.ofNullable(userDao.findById(id))
                .orElseThrow(ResourceNotFoundException::new);
        return orderDao.findOrdersByUserId(new SearchOrderByUserIdParams(id), pageable).stream()
                .map(order -> mapper.map(order, GiftOrder.class))
                .collect(Collectors.toList());
    }
}
