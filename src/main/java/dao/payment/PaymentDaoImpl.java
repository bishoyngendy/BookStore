package dao.payment;

import models.Payment;
import utils.ConnectionProvider;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static dao.DaoUtil.getId;

public class PaymentDaoImpl implements PaymentDao {

    public long addPayment(Payment payment) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into PAYMENT " +
                "(USER_ID, TYPE, CARD_NUMBER, CVV, EXPIRE_DATE) values (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, payment.getUserId());
        preparedStatement.setString(2, payment.getType());
        preparedStatement.setString(3, payment.getCardNumber());
        preparedStatement.setString(4, payment.getCvv());
        preparedStatement.setString(5, payment.getExpireDate());

        int affectedRows = preparedStatement.executeUpdate();
        long id = -1;
        if (affectedRows != 0) {
            id = getId(preparedStatement, payment);
        }

        preparedStatement.close();
        connection.close();
        return id;
    }

    public List<Payment> getPaymentsOfUser(long userId) throws SQLException, ClassNotFoundException {
        String query = "SELECT ID, USER_ID, TYPE, CARD_NUMBER, CVV, EXPIRE_DATE FROM PAYMENT " +
                "where USER_ID = " + userId;
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Payment> payments = new LinkedList<Payment>();
        while (resultSet.next()) {
            Payment payment = new Payment();
            payment.setPaymentId(resultSet.getLong(1));
            payment.setUserId(resultSet.getLong(2));
            payment.setType(resultSet.getString(3));
            payment.setCardNumber(resultSet.getString(4));
            payment.setCvv(resultSet.getString(5));
            payment.setExpireDate(resultSet.getString(6));
            payments.add(payment);
        }
        preparedStatement.close();
        connection.close();
        return payments;
    }
}
