package service.cart;

import exceptions.DuplicateKeyException;
import exceptions.InvalidOrderQuantity;
import models.Cart;
import models.Payment;
import models.User;

/**
 * OrderService
 * Created on: May 11, 2018
 * Author: marc
 */
public interface OrderService {
    void addOrder(User userCart, Cart cart, Payment payment) throws DuplicateKeyException, InvalidOrderQuantity;
}
