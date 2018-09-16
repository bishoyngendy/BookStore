/**
 * Created by programajor on 5/4/18.
 */

package dao.user;

import exceptions.EmptyResultSetException;
import models.User;
import utils.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    public int addUser(User user) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into USER(USERNAME, PASSWORD," +
                "EMAIL, FIRST_NAME, LAST_NAME, PHONE, SHIPPING_ADDRESS) values (?, MD5(?), ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getLastName());
        preparedStatement.setString(6, user.getPhone());
        preparedStatement.setString(7, user.getShippingAddress());

        int status = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return status;
    }

    public User getUser(String username, String password) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from USER where USERNAME = ? and PASSWORD = MD5(?)");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User user = new User();
            user.setUserID(resultSet.getLong(1));
            user.setUsername(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setFirstName(resultSet.getString(5));
            user.setLastName(resultSet.getString(6));
            user.setPhone(resultSet.getString(7));
            user.setShippingAddress(resultSet.getString(8));
            user.setRoleId(resultSet.getInt(9));
            preparedStatement.close();
            connection.close();
            return user;
        }
        preparedStatement.close();
        connection.close();
        throw new EmptyResultSetException("Wrong Email or Password!!");
    }

    public List<User> getUsers(Integer start, Integer length) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from USER " +
                "inner join ROLE ON USER.ROLE_ID = ROLE.ID LIMIT ?, ?");
        preparedStatement.setInt(1, start);
        preparedStatement.setInt(2, length);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new ArrayList<User>();
        while (resultSet.next()) {
            User user = new User();
            user.setUserID(resultSet.getLong(1));
            user.setUsername(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setEmail(resultSet.getString(4));
            user.setFirstName(resultSet.getString(5));
            user.setLastName(resultSet.getString(6));
            user.setPhone(resultSet.getString(7));
            user.setShippingAddress(resultSet.getString(8));
            user.setRoleId(resultSet.getInt(9));
            user.setRoleName(resultSet.getString(11));
            users.add(user);
        }
        preparedStatement.close();
        connection.close();
        return users;
    }

    public long getUsersCount() throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from USER");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            long count = resultSet.getLong(1);
            preparedStatement.close();
            connection.close();
            return count;
        }
        preparedStatement.close();
        connection.close();
        return 0;
    }

    public void changeUserRole(long userId, long roleId) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE USER SET ROLE_ID = ? WHERE ID = ?");
        preparedStatement.setLong(1, roleId);
        preparedStatement.setLong(2, userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public User getUser(long userId) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID, USERNAME, " +
                "FIRST_NAME, LAST_NAME, EMAIL, PHONE, SHIPPING_ADDRESS FROM USER WHERE ID = ?");
        preparedStatement.setLong(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = new User();
        if (resultSet.next()) {
            user.setUserID(resultSet.getLong(1));
            user.setUsername(resultSet.getString(2));
            user.setFirstName(resultSet.getString(3));
            user.setLastName(resultSet.getString(4));
            user.setEmail(resultSet.getString(5));
            user.setPhone(resultSet.getString(6));
            user.setShippingAddress(resultSet.getString(7));
        }
        preparedStatement.close();
        connection.close();
        return user;
    }

    public boolean isValid(long userId, String password) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * " +
                "FROM USER WHERE ID = ? AND PASSWORD = MD5(?)");
        preparedStatement.setLong(1, userId);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean ret = false;
        if (resultSet.next()) {
            ret = true;
        }
        preparedStatement.close();
        connection.close();
        return ret;
    }

    public void updateUser(User user, boolean updatePassword) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        String editUserQuery = "UPDATE USER SET " +
                "FIRST_NAME = ?, LAST_NAME = ?, USERNAME = ?, EMAIL = ?, " +
                "PHONE = ?, SHIPPING_ADDRESS = ?";
        if (updatePassword) editUserQuery += ", PASSWORD = MD5(" + user.getPassword() + ")";
        editUserQuery += " WHERE ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(editUserQuery);
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        preparedStatement.setString(3, user.getUsername());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getPhone());
        preparedStatement.setString(6, user.getShippingAddress());
        preparedStatement.setLong(7, user.getUserID());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public boolean isManager(long userId) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        String query = "select exists(SELECT * FROM USER JOIN ROLE ON USER.ROLE_ID = ROLE.ID " +
                "JOIN ROLE_ACL on ROLE.ID = ROLE_ACL.ROLE_ID JOIN ACL ON ROLE_ACL.ACL_ID = ACL.ID " +
                "WHERE ACL.NAME = 'openAdmin' and USER.ID = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        boolean ret = resultSet.getBoolean(1);
        preparedStatement.close();
        connection.close();
        return ret;
    }
}
