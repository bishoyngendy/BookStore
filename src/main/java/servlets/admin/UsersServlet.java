package servlets.admin;

import com.google.gson.Gson;
import models.Role;
import models.User;
import service.role.RoleService;
import service.role.RoleServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import servlets.models.DatatableModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/users")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        RoleService roleService = new RoleServiceImpl();
        List<Role> roles = roleService.getAllRoles();
        request.setAttribute("roles", roles);
        if (start != null && length != null) {
            UserService userService = new UserServiceImpl();
            List<User> users = userService.getUsers(Integer.valueOf(start), Integer.valueOf(length));
            long totalCount = userService.getUsersCount();
            DatatableModel datatableModel = buildDataTableModel(users, totalCount);
            String json = new Gson().toJson(datatableModel);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
        }
    }

    private DatatableModel buildDataTableModel(List<User> users, long totalCount) {
        DatatableModel datatableModel = new DatatableModel();
        datatableModel.setRecordsTotal(totalCount);
        datatableModel.setRecordsFiltered(totalCount);
        for (User user : users) {
            List<String> strings = new ArrayList<String>();
            strings.add(user.getUserID() + "");
            strings.add(user.getFirstName() + " " + user.getLastName());
            strings.add(user.getUsername());
            strings.add(user.getPhone());
            strings.add(user.getShippingAddress());
            strings.add(user.getRoleName());
            strings.add(user.getRoleId() + "");
            datatableModel.getData().add(strings);
        }
        return datatableModel;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String roleId = request.getParameter("roleId");
        UserService userService = new UserServiceImpl();
        userService.changeUserRole(Long.valueOf(userId), Long.valueOf(roleId));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("test/html");
        response.getWriter().write("success");
    }

}
