package sample.generator;

import dao.book.BookDao;
import dao.book.BookDaoImpl;
import models.Author;
import models.Book;
import models.Publisher;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * BookGenerator
 * Created on: May 06, 2018
 * Author: marc
 */
public class BookGenerator extends Thread {
    public void run()
    {
        try
        {
            // Displaying the thread that is running
            System.out.println ("Thread " +
                    Thread.currentThread().getId() +
                    " is running, Add Books.");
            addBooks(1000000);
        }
        catch (Exception e)
        {
            // Throwing an exception
            e.printStackTrace();
            System.out.println ("Exception is caught");
        }
    }

    public void addBooks(int count) throws SQLException, ClassNotFoundException {
        Set<Book> bookSet = new HashSet<Book>();
        for (long i = 700000; i < count + 700000; i++) {
            Book book = new Book();
            book.setIsbn(String.valueOf(i));
            book.setTitle(DataGenerator.getRandomNoun() + " " + DataGenerator.getRandomVerb()
                    + " " + DataGenerator.getRandomAdverb());
            book.setCategory(DataGenerator.getRandomCategory());
            book.setPublicationYear(DataGenerator.getRandomYear());
            book.setThreshold(DataGenerator.getRandomNum(100));
            book.setStockOrderQuantity(DataGenerator.getRandomNum(10) * 20);
            book.setAvailableQuantity(0);
            Publisher publisher = new Publisher();
            publisher.setId(DataGenerator.getRandomPublisherId());
            book.setPublisher(publisher);
            int authorsCount = DataGenerator.getRandomNum(5);
            List<Author> authors = new LinkedList<Author>();
            for (int j = 0; j < authorsCount; j++) {
                Author author = new Author();
                author.setId(DataGenerator.getRandomAuthorId());
                authors.add(author);
            }
            book.setAuthors(authors);
            bookSet.add(book);
        }

        System.out.println(bookSet.size());
        BookDao bookDao = new BookDaoImpl();
        bookDao.addBooks(bookSet);
    }
}
