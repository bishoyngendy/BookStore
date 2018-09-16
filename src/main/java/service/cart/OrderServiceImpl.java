package service.cart;

import dao.user.order.UserOrderDao;
import dao.user.order.UserOrderDaoImpl;
import exceptions.DuplicateKeyException;
import exceptions.InvalidOrderQuantity;
import models.Cart;
import models.Payment;
import models.User;
import models.UserOrder;

import java.sql.SQLException;

import static utils.MySqlErrorCodes.MYSQL_DUPLICATE_PK;
import static utils.MySqlErrorCodes.MYSQL_INVALID_QUANTITY;

/**
 * OrderServiceImpl
 * Created on: May 11, 2018
 * Author: marc
 */
public class OrderServiceImpl implements OrderService {
    private UserOrderDao userOrderDao;

    public OrderServiceImpl() {
        this.userOrderDao = new UserOrderDaoImpl();
    }

    public void addOrder(User user, Cart cart, Payment payment) throws DuplicateKeyException, InvalidOrderQuantity {
        UserOrder userOrder = getOrder(user, cart, payment);
        try {
            this.userOrderDao.addOrder(userOrder);
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK ){
                throw new DuplicateKeyException(e.getMessage());
            } else if (e.getErrorCode() == MYSQL_INVALID_QUANTITY) {
                throw new InvalidOrderQuantity(e.getMessage());
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private UserOrder getOrder(User user, Cart cart, Payment payment) {
        UserOrder userOrder = new UserOrder(user, payment);
        userOrder.addOrderDetails(cart.getBooks());
        return userOrder;
    }
}
