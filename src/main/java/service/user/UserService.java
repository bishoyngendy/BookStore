package service.user;

import exceptions.DuplicateKeyException;
import exceptions.EmptyResultSetException;
import models.User;

import java.util.List;

public interface UserService {
    long addUser(User user) throws DuplicateKeyException;
    User getUser(String username, String password) throws EmptyResultSetException;
    List<User> getUsers(Integer start, Integer length);
    long getUsersCount();
    void changeUserRole(long userId, long roleId);
    User getUser(long userId);
    boolean isValid(long userId, String password);
    void updateUser(User user, boolean updatePassword) throws DuplicateKeyException;
    boolean isManager(long userId);
}
