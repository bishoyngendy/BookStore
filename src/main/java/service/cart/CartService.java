package service.cart;

import exceptions.EmptyResultSetException;
import models.Cart;

/**
 * CartService
 * Created on: May 10, 2018
 * Author: marc
 */
public interface CartService {
    void addBookToCart(Cart cart, String ISBN, int quantity) throws EmptyResultSetException;
    void removeBookFromCart(Cart cart, String ISBN) throws EmptyResultSetException;
    void removeAllBookFromCart(Cart cart, String ISBN) throws EmptyResultSetException;
}
