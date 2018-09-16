package service.user;

import dao.user.UserDao;
import dao.user.UserDaoImpl;
import exceptions.DuplicateKeyException;
import exceptions.EmptyResultSetException;
import models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.MySqlErrorCodes.MYSQL_DUPLICATE_PK;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public long addUser(User user) throws DuplicateKeyException {
        try {
            return this.userDao.addUser(user);
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                throw new DuplicateKeyException("Username or Email already exist.");
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public User getUser(String username, String password) throws EmptyResultSetException {
        try {
            return this.userDao.getUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers(Integer start, Integer length) {
        try {
            return this.userDao.getUsers(start, length);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<User>();
    }

    public long getUsersCount() {
        try {
            return this.userDao.getUsersCount();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void changeUserRole(long userId, long roleId) {
        try {
            this.userDao.changeUserRole(userId, roleId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUser(long userId) {
        try {
            return this.userDao.getUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValid(long userId, String password) {
        try {
            return this.userDao.isValid(userId, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateUser(User user, boolean updatePassword) throws DuplicateKeyException {
        try {
            this.userDao.updateUser(user, updatePassword);
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                throw new DuplicateKeyException(e.getMessage());
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean isManager(long userId) {
        try {
            return this.userDao.isManager(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
