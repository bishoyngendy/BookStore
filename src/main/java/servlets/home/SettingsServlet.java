package servlets.home;

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

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");
        UserService userService = new UserServiceImpl();
        User user = userService.getUser(userId);
        if (user != null) {
            setAttributes(request, user);
            request.getRequestDispatcher("/home/settings.jsp").forward(request, response);
        } else {
            response.sendRedirect("/signin.jsp");
        }
    }

    private void setAttributes(HttpServletRequest request, User user) {
        request.setAttribute("firstName", user.getFirstName());
        request.setAttribute("lastName", user.getLastName());
        request.setAttribute("email", user.getEmail());
        request.setAttribute("username", user.getUsername());
        request.setAttribute("phone", user.getPhone());
        request.setAttribute("shipping_address", user.getShippingAddress());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");
        UserService userService = new UserServiceImpl();
        String password = request.getParameter("old_password");
        User user = getUser(request, userId);
        if (password == null || password.equals("")) {
            updateUser(userService, user, false, request);
        } else {
            boolean isValid = userService.isValid(userId, password);
            if (isValid) {
                updateUser(userService, user, true, request);
                setAttributes(request, user);
            } else {
                request.setAttribute("oldError", "Incorrect Password");
                request.setAttribute("oldPassword", password);
            }
        }
        setAttributes(request, user);
        request.getRequestDispatcher("/home/settings.jsp").forward(request, response);
    }

    private void updateUser(UserService userService, User user, boolean updatePassword, HttpServletRequest request) {
        try {
            userService.updateUser(user, updatePassword);
            request.setAttribute("success", "Settings updated successfully");
        } catch (DuplicateKeyException e) {
            setDuplicateAttributes(e, request, user);
        }
    }

    private void setDuplicateAttributes(DuplicateKeyException e, HttpServletRequest request, User user) {
        if (e.getMessage().contains("EMAIL")) {
            request.setAttribute("emailError", "Invalid Email Address");
            request.setAttribute("email", user.getEmail());
        } else {
            request.setAttribute("usernameError", "Invalid Username");
            request.setAttribute("username", user.getUsername());
        }
    }

    private User getUser(HttpServletRequest request, Long userId) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String shippingAddress = request.getParameter("shipping_address");
        String password = request.getParameter("new_password");
        User user = new User();
        user.setUserID(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setShippingAddress(shippingAddress);
        user.setPassword(password);
        user.setPhone(phone);
        return user;
    }
}
