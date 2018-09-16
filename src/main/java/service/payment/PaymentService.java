package service.payment;

import exceptions.DuplicateKeyException;
import models.Payment;

import java.util.List;

public interface PaymentService {

    long addPayment(Payment payment) throws DuplicateKeyException;

    List<Payment> getPaymentsOfUser(long userId);
}
