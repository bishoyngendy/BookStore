package models;

import models.dto.BookForCart;

import java.util.LinkedList;
import java.util.List;

/**
 * UserOrder
 * Created on: May 11, 2018
 * Author: marc
 */
public class UserOrder {
    private long id;
    private User user;
    private Payment payment;
    private List<UserOrderDetails> orderDetailsList;

    public UserOrder(User user, Payment payment) {
        this.orderDetailsList = new LinkedList<UserOrderDetails>();
        this.user = user;
        this.payment = payment;
    }

    public void addOrderDetails(List<BookForCart> bookForCarts) {
        for (BookForCart book : bookForCarts) {
            addOrderDetails(book);
        }
    }

    private void addOrderDetails(BookForCart bookForCart) {
        UserOrderDetails orderDetails = new UserOrderDetails();
        orderDetails.book = bookForCart;
        orderDetails.order = this;
        this.orderDetailsList.add(orderDetails);
    }

    public User getUser() {
        return user;
    }

    public Payment getPayment() {
        return payment;
    }

    public List<UserOrderDetails> getOrderDetailsList() {
        return orderDetailsList;
    }

    public class UserOrderDetails {
        private UserOrder order;
        private BookForCart book;

        public UserOrder getOrder() {
            return order;
        }

        public BookForCart getBook() {
            return book;
        }
    }

}
