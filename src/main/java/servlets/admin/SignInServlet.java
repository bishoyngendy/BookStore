package servlets.admin;

import exceptions.EmptyResultSetException;
import models.User;
import service.user.UserService;
import service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signin")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.getUser(username, password);
            request.getSession().setAttribute("userId", user.getUserID());
            request.getSession().setAttribute("username", username);
            if (userService.isManager(user.getUserID())) {
                request.getSession().setAttribute("isManager", true);
            }
            response.sendRedirect("/books");
        } catch (EmptyResultSetException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.getRequestDispatcher("/login/signin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login/signin.jsp").forward(request, response);
    }
}
