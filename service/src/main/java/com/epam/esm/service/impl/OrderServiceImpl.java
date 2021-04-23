package com.epam.esm.service.impl;

import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.entity.Order;
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
import com.epam.esm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDaoImpl orderDao;
    private final ModelMapper mapper;
    private final UserService userService;
    private final GiftCertificateService certificateService;

    @Autowired
    public OrderServiceImpl(OrderDaoImpl orderDao, ModelMapper mapper, UserService userService, GiftCertificateService certificateService) {
        this.orderDao = orderDao;
        this.mapper = mapper;
        this.userService = userService;
        this.certificateService = certificateService;
    }

    @Transactional
    @Override
    public Long createOrder(Long id, List<GiftCertificate> giftCertificates) throws CreateResourceException {
        return Optional.ofNullable(userService.findById(id))
                .map(userGift -> buildGiftOrder(giftCertificates, userGift))
                .map(order -> mapper.map(order, Order.class))
                .map(orderDao::create)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private GiftOrder buildGiftOrder(List<GiftCertificate> giftCertificates, UserGift userGift) {
        List<GiftCertificate> certificates = giftCertificates.stream()
                .map(GiftCertificate::getId)
                .map(certificateService::findById)
                .collect(Collectors.toList());
        BigDecimal cost = certificates.stream()
                .map(GiftCertificate::getPrice)
                .reduce(BigDecimal::add).get();
        return GiftOrder.builder()
                .user(userGift)
                .certificates(certificates)
                .cost(cost)
                .purchaseDate(ZonedDateTime.now(ZoneId.systemDefault()))
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
        List<Order> ordersByUserId = orderDao.findOrdersByUserId(new SearchOrderByUserIdParams(id), pageable);
        return ordersByUserId.stream()
                .map(order -> mapper.map(order, GiftOrder.class))
                .collect(Collectors.toList());
    }
}
