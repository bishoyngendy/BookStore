package service.role;

import dao.role.RoleDao;
import dao.role.RoleDaoImpl;
import models.Role;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    public RoleServiceImpl() {
        this.roleDao = new RoleDaoImpl();
    }

    public List<Role> getAllRoles() {
        try {
            return this.roleDao.getAllRoles();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<Role>();
    }
}
