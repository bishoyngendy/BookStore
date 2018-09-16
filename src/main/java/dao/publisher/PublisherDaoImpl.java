package dao.publisher;

import models.Publisher;
import utils.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static dao.DaoUtil.getId;

/**
 * PublisherServiceImpl
 * Created on: May 05, 2018
 * Author: marc
 */
public class PublisherDaoImpl implements PublisherDao {
    public long addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into PUBLISHER (NAME, ADDRESS, PHONE) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, publisher.getName());
        preparedStatement.setString(2, publisher.getAddress());
        preparedStatement.setString(3, publisher.getPhone());

        int affectedRows = preparedStatement.executeUpdate();
        long id = -1;
        if (affectedRows != 0) {
            id = getId(preparedStatement, publisher);
        }

        preparedStatement.close();
        connection.close();
        return id;
    }

    public long addPublishers(List<Publisher> publishers) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("insert into PUBLISHER (NAME, ADDRESS, PHONE) values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        long c = 0;
        for (Publisher publisher : publishers) {
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getAddress());
            preparedStatement.setString(3, publisher.getPhone());
            try {
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            c++;
            if (c == 1000) {
                c = 0;
                connection.commit();
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        preparedStatement.close();
        connection.close();

        return 0;
    }

    public List<Publisher> getPublishersByNamePrefix(String namePrefix) throws SQLException, ClassNotFoundException {
        String query = "SELECT ID, NAME FROM PUBLISHER where NAME like ? limit 10";
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, namePrefix + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Publisher> publishers = new LinkedList<Publisher>();
        while (resultSet.next()) {
            Publisher publisher = new Publisher();
            publisher.setId(resultSet.getLong("ID"));
            publisher.setName(resultSet.getString("NAME"));
            publishers.add(publisher);
        }

        preparedStatement.close();
        connection.close();
        return publishers;
    }

    public List<Long> getPublishersId() throws SQLException, ClassNotFoundException {
        String query = "SELECT ID FROM PUBLISHER LIMIT 500000";
        List<Long> publishers = new ArrayList<Long>();
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            publishers.add(resultSet.getLong(1));
        }

        preparedStatement.close();
        connection.close();
        return publishers;
    }
}
