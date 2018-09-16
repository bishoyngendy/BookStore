package dao.user.order;

import models.UserOrder;

import java.sql.SQLException;

/**
 * UserOrderDao
 * Created on: May 11, 2018
 * Author: marc
 */
public interface UserOrderDao {
    void addOrder(UserOrder userOrder) throws SQLException, ClassNotFoundException;
}
