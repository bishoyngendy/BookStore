package service.payment;

import dao.payment.PaymentDao;
import dao.payment.PaymentDaoImpl;
import exceptions.DuplicateKeyException;
import models.Payment;

import java.sql.SQLException;
import java.util.List;

import static utils.MySqlErrorCodes.MYSQL_DUPLICATE_PK;

public class PaymentServiceImpl implements PaymentService {

    private PaymentDao paymentDao;

    public PaymentServiceImpl() {
        this.paymentDao = new PaymentDaoImpl();
    }

    public long addPayment(Payment payment) throws DuplicateKeyException {
        try {
            return this.paymentDao.addPayment(payment);
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                throw new DuplicateKeyException("Payment already exist.");
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Payment> getPaymentsOfUser(long userId) {
        List<Payment> payments = null;
        try {
            payments = this.paymentDao.getPaymentsOfUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return payments;
    }
}
