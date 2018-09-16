package dao.payment;

import models.Payment;

import java.sql.SQLException;
import java.util.List;

public interface PaymentDao {
    long addPayment(Payment payment) throws SQLException, ClassNotFoundException;
    List<Payment> getPaymentsOfUser(long userId) throws SQLException, ClassNotFoundException;
}
