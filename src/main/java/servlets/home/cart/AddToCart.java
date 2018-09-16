package servlets.home.cart;

import exceptions.EmptyResultSetException;
import models.Cart;
import service.cart.CartService;
import service.cart.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * AddToCart
 * Created on: May 10, 2018
 * Author: marc
 */
@WebServlet("/cart/add")
public class AddToCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String ISBN = request.getParameter("ISBN");
        String quantityStr = request.getParameter("quantity");
        int quantity = quantityStr == null ? 1 : Integer.parseInt(quantityStr);

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        CartService cartService = new CartServiceImpl();
        try {
            cartService.addBookToCart(cart, ISBN, quantity);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (EmptyResultSetException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        session.setAttribute("cart", cart);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("");
    }
}
