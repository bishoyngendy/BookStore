package dao.author;

import models.Author;

import java.sql.SQLException;
import java.util.List;

/**
 * AuthorDao
 * Created on: May 05, 2018
 * Author: marc
 */
public interface AuthorDao {
    long addAuthor(Author author) throws SQLException, ClassNotFoundException;
    long addAuthors(List<Author> author) throws SQLException, ClassNotFoundException;
    List<Author> getAuthorsByNamePrefix(String namePrefix) throws SQLException, ClassNotFoundException;
    List<Author> getBookAuthors(String ISBN) throws SQLException, ClassNotFoundException;
    List<Long> getAuthorsId() throws SQLException, ClassCastException, ClassNotFoundException;
}
