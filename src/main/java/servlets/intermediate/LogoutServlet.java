package servlets.intermediate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LogoutServlet
 * Created on: May 13, 2018
 * Author: marc
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("userId", null);
        request.getSession().setAttribute("isManager", null);
        request.getSession().setAttribute("cart", null);

        response.sendRedirect("/signin");
    }
}
