package dao.role;

import models.Role;
import utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    public List<Role> getAllRoles() throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select ID, NAME FROM ROLE");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Role> roles = new ArrayList<Role>();
        while (resultSet.next()) {
            Role role = new Role();
            role.setRoleId(resultSet.getLong(1));
            role.setRoleName(resultSet.getString(2));
            roles.add(role);
        }
        preparedStatement.close();
        connection.close();
        return roles;
    }
}
