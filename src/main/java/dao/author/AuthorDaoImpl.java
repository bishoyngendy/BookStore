package dao.author;

import models.Author;
import utils.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.DaoUtil.getId;

/**
 * AuthorDaoImpl
 * Created on: May 05, 2018
 * Author: marc
 */
public class AuthorDaoImpl implements AuthorDao {

    public long addAuthor(Author author) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into AUTHOR (NAME) values (?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, author.getName());
        int affectedRows = preparedStatement.executeUpdate();
        long id = -1;
        if (affectedRows != 0) {
            id = getId(preparedStatement, author);
        }
        preparedStatement.close();
        connection.close();
        return id;
    }

    public long addAuthors(List<Author> authors) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement preparedStatement = connection.prepareStatement("insert into AUTHOR (NAME) values (?)", Statement.RETURN_GENERATED_KEYS);

        for (Author author : authors) {
            preparedStatement.setString(1, author.getName());
            try {
                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        connection.commit();
        connection.setAutoCommit(true);
        preparedStatement.close();
        connection.close();

        return 0;
    }

    public List<Author> getAuthorsByNamePrefix(String namePrefix) throws SQLException, ClassNotFoundException {
        String query = "SELECT ID, NAME FROM AUTHOR where NAME like ? limit 10";
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, namePrefix + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Author> authors = new ArrayList<Author>();
        fillResultSet(resultSet, authors);

        preparedStatement.close();
        connection.close();
        return authors;
    }

    private void fillResultSet(ResultSet resultSet, List<Author> authors) throws SQLException {
        while (resultSet.next()) {
            Author author = new Author();
            author.setId(resultSet.getLong("ID"));
            author.setName(resultSet.getString("NAME"));
            authors.add(author);
        }
    }

    public List<Author> getBookAuthors(String ISBN) throws SQLException, ClassNotFoundException {
        String query = "select AUTHOR.ID, AUTHOR.NAME " +
                "from (AUTHOR inner join BOOK_AUTHOR on AUTHOR.ID = BOOK_AUTHOR.AUTHOR_ID) " +
                "inner join BOOK on BOOK.ISBN = BOOK_AUTHOR.BOOK_ISBN where BOOK.ISBN = ?";
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ISBN);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Author> authors = getAuthorsFromResultSet(resultSet);
        preparedStatement.close();
        connection.close();
        return authors;
    }

    public List<Long> getAuthorsId() throws SQLException, ClassNotFoundException {
        String query = "SELECT ID FROM AUTHOR LIMIT 500000";
        List<Long> authors = new ArrayList<Long>();
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            authors.add(resultSet.getLong(1));
        }

        preparedStatement.close();
        connection.close();
        return authors;
    }

    private List<Author> getAuthorsFromResultSet(ResultSet resultSet) throws SQLException {
        List<Author> authors = new ArrayList<Author>();
        fillResultSet(resultSet, authors);
        return authors;
    }
}
