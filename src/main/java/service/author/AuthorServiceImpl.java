package service.author;

import dao.author.AuthorDao;
import dao.author.AuthorDaoImpl;
import exceptions.DuplicateKeyException;
import models.Author;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.MySqlErrorCodes.MYSQL_DUPLICATE_PK;

/**
 * AuthorServiceImpl
 * Created on: May 05, 2018
 * Author: marc
 */
public class AuthorServiceImpl implements AuthorService {
    private AuthorDao authorDao;
    public AuthorServiceImpl() {
        this.authorDao = new AuthorDaoImpl();
    }

    public long addAuthor(Author author) throws DuplicateKeyException {
        try {
            return this.authorDao.addAuthor(author);
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK ){
                throw new DuplicateKeyException("Author name already exist.");
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Author> getAuthorsByNamePrefix(String namePrefix) {
        List<Author> authors = null;
        try {
            authors = this.authorDao.getAuthorsByNamePrefix(namePrefix);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public List<Author> getBookAuthors(String ISBN) {
        List<Author> authors = new ArrayList<Author>();
        try {
            authors = this.authorDao.getBookAuthors(ISBN);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
