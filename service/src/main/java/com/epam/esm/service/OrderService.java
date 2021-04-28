package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftOrderWithoutCertificatesAndUser;
import com.epam.esm.model.Pageable;
import com.epam.esm.exception.CreateResourceException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftOrder;

import java.util.List;

/**
 * Base interface for Order Service
 *
 * @author Egor Miheev
 * @version 1.0.0
 */
public interface OrderService {
    /**
     * Create order
     *
     * @param userId           user id
     * @param giftCertificates list of certificates
     * @return order id
     * @throws CreateResourceException if error is occurred during SQL command execution
     */
    Long createOrder(Long userId, List<GiftCertificate> giftCertificates) throws CreateResourceException;

    /**
     * Get user order
     *
     * @param orderId order id
     * @param userId  user id
     * @return cost and timestamp of a purchase
     * @throws ResourceNotFoundException if fail to retrieve data from DB
     */
    GiftOrderWithoutCertificatesAndUser findUserOrderInfo(Long orderId, Long userId) throws ResourceNotFoundException;

    /**
     * Get user orders
     *
     * @param id       user id
     * @param pageable pagination
     * @return list of user orders
     * @throws ResourceNotFoundException if fail to retrieve data from DB
     */
    List<GiftOrder> findUserOrders(Long id, Pageable pageable) throws ResourceNotFoundException;
}
