package dao.user.order;

import models.UserOrder;
import utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserOrderDaoImpl
 * Created on: May 11, 2018
 * Author: marc
 */
public class UserOrderDaoImpl implements UserOrderDao {
    public void addOrder(UserOrder userOrder) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        connection.setAutoCommit(false);

        String addOrderQuery = "INSERT INTO USER_ORDER (USER_ID, PAYMENT_ID) values (?, ?)";
        PreparedStatement addOrder = connection.prepareStatement(addOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        PreparedStatement addOrderDetails = null;

        addOrder.setLong(1, userOrder.getUser().getUserID());
        addOrder.setLong(2, userOrder.getPayment().getPaymentId());

        int affectedRows = addOrder.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating order failed, no rows affected.");
        }

        long orderId;
        ResultSet generatedKeys = addOrder.getGeneratedKeys();
        if (generatedKeys.next()) {
            orderId = generatedKeys.getLong(1);
        } else {
            throw new SQLException("Creating order failed, no ID obtained.");
        }

        try {
            String addOrderDetailsQuery = "INSERT INTO USER_ORDER_DETAILS (USER_ORDER_ID, BOOK_ISBN" +
                    ", BOOK_QUANTITY) VALUES(?, ?, ?)";
            addOrderDetails = connection.prepareStatement(addOrderDetailsQuery);
            for (UserOrder.UserOrderDetails details : userOrder.getOrderDetailsList()) {
                addOrderDetails.setLong(1, orderId);
                addOrderDetails.setString(2, details.getBook().getISBN());
                addOrderDetails.setInt(3, details.getBook().getQuantity());
                addOrderDetails.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (addOrderDetails != null) addOrderDetails.close();
            addOrder.close();
            connection.close();
        }
    }
}
