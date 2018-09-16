package servlets.admin;

import exceptions.DuplicateKeyException;
import models.User;
import service.user.UserService;
import service.user.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String shippingAddress = request.getParameter("shippingAddress");

        User user = new User(firstName, lastName, username, email, password, phone, shippingAddress);
        UserService userService = new UserServiceImpl();
        try {
            long status = userService.addUser(user);
        } catch (DuplicateKeyException e) {
            setAttributes(request, firstName, lastName, username, email, password, phone, shippingAddress);
            if (e.getMessage().contains("USERNAME")) {
                request.setAttribute("usernameError", "Invalid Username");
            } else {
                request.setAttribute("emailError", "Invalid Email Address");
            }
            request.getRequestDispatcher("/login/signup.jsp").forward(request, response);
        }
        response.sendRedirect("/signin");
//        request.getRequestDispatcher("/login/signin.jsp").forward(request, response);
    }

    private void setAttributes(HttpServletRequest request, String firstName, String lastName, String username, String email, String password, String phone, String shippingAddress) {
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("passwordConfirm", password);
        request.setAttribute("phone", phone);
        request.setAttribute("shippingAddress", shippingAddress);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login/signup.jsp").forward(request, response);
    }
}
