package servlets.home.cart;

import exceptions.DuplicateKeyException;
import models.Payment;
import service.payment.PaymentService;
import service.payment.PaymentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AddPayment
 * Created on: May 10, 2018
 * Author: marc
 */
@WebServlet("/payments/add")
public class AddPayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String cardNumber = request.getParameter("cardNumber");
        String cvv = request.getParameter("cvv");
        String date = request.getParameter("date");
        long userId = Long.valueOf(request.getSession().getAttribute("userId").toString());
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setCardNumber(cardNumber);
        payment.setCvv(cvv);
        payment.setExpireDate(date);
        payment.setType(type);

        PaymentService paymentService = new PaymentServiceImpl();
        try {
            paymentService.addPayment(payment);
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }

        response.sendRedirect("/checkout");
    }
}
