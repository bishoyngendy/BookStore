package servlets.home.cart;

import com.google.gson.Gson;
import exceptions.DuplicateKeyException;
import models.Cart;
import models.Payment;
import models.User;
import service.cart.OrderService;
import service.cart.OrderServiceImpl;
import service.payment.PaymentService;
import service.payment.PaymentServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * PlaceOrder
 * Created on: May 11, 2018
 * Author: marc
 */
@WebServlet("/orders")
public class PlaceOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        int paymentId = Integer.parseInt(request.getParameter("paymentId"));
        long userId = Long.valueOf(request.getSession().getAttribute("userId").toString());

        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setPaymentId(paymentId);

        User user = new User();
        user.setUserID(userId);
        JsonRet res = new JsonRet();
        OrderService orderService = new OrderServiceImpl();
        try {
            orderService.addOrder(user, cart, payment);
            res.value = "Success";
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            request.getSession().setAttribute("cart", null);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            res.value = e.getMessage();
            res.type = "error";
        }

        String retVal = new Gson().toJson(res);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(retVal);
    }

    private class JsonRet {
        String type = "";
        String value = "";
    }
}
