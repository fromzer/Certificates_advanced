package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftOrderWithoutCertificatesAndUser;
import com.epam.esm.model.Pageable;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftOrder;

import java.util.List;

public interface OrderService {
    Long createOrder(Long id, List<GiftCertificate> giftCertificates) throws CreateResourceException;

    GiftOrderWithoutCertificatesAndUser findUserOrderInfo(Long orderId, Long userId) throws ResourceNotFoundException;

    List<GiftOrder> findUserOrders(Long id, Pageable pageable);
}
