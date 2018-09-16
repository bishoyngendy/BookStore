package sample.generator;

import dao.author.AuthorDao;
import dao.author.AuthorDaoImpl;
import models.Author;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * AuthorGenerator
 * Created on: May 06, 2018
 * Author: marc
 */
public class AuthorGenerator extends Thread {
    public void run()
    {
        try
        {
            // Displaying the thread that is running
            System.out.println ("Thread " +
                    Thread.currentThread().getId() +
                    " is running, Add Authors.");
            addAuthors(500000);

        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
    }

    void addAuthors(int count) throws SQLException, ClassNotFoundException {
        AuthorDao authorDao = new AuthorDaoImpl();
        List<Author> authors = new ArrayList<Author>();

        for (int i = 0; i < count; i++) {
            Author author = new Author();
            String name = DataGenerator.getRandomName() + " "
                    + DataGenerator.getRandomName() + " " + DataGenerator.getRandomName();
            author.setName(name);
            authors.add(author);
        }
        authorDao.addAuthors(authors);
    }
}
