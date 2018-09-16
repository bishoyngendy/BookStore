/**
 * Created by programajor on 5/4/18.
 */

package dao.user;

import exceptions.EmptyResultSetException;
import models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    int addUser(User user) throws SQLException, ClassNotFoundException;
    User getUser(String username, String password) throws SQLException, ClassNotFoundException, EmptyResultSetException;
    User getUser(long userId) throws SQLException, ClassNotFoundException;
    List<User> getUsers(Integer start, Integer length) throws SQLException, ClassNotFoundException;
    long getUsersCount() throws SQLException, ClassNotFoundException;
    void changeUserRole(long userId, long roleId) throws SQLException, ClassNotFoundException;
    boolean isValid(long userId, String password) throws SQLException, ClassNotFoundException;
    boolean isManager(long userId) throws SQLException, ClassNotFoundException;
    void updateUser(User user, boolean updatePassword) throws SQLException, ClassNotFoundException;
}
