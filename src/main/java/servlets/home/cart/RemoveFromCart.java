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
 * RemoveFromCart
 * Created on: May 10, 2018
 * Author: marc
 */
@WebServlet("/cart/remove")
public class RemoveFromCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String ISBN = request.getParameter("ISBN");
        String removeAll = request.getParameter("ALL");

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        CartService cartService = new CartServiceImpl();
        try {
            if (removeAll == null) cartService.removeBookFromCart(cart, ISBN);
            else cartService.removeAllBookFromCart(cart, ISBN);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (EmptyResultSetException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        session.setAttribute("cart", cart);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("");
    }
}
