package servlets.home;

import models.Payment;
import service.payment.PaymentService;
import service.payment.PaymentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * CartServlet
 * Created on: May 08, 2018
 * Author: marc
 */
@WebServlet("/checkout")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PaymentService paymentService = new PaymentServiceImpl();
        long userId = Long.valueOf(request.getSession().getAttribute("userId").toString());
        List<Payment> payments = paymentService.getPaymentsOfUser(userId);
        request.setAttribute("payments", payments);
        request.getRequestDispatcher("/home/shop-cart.jsp").forward(request, response);
    }
}
