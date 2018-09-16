package dao.role;

import models.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao {
    List<Role> getAllRoles() throws SQLException, ClassNotFoundException;
}
