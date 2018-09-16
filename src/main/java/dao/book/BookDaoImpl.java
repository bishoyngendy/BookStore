/**
 * Created by programajor on 5/4/18.
 */

package dao.book;

import exceptions.EmptyResultSetException;
import models.Author;
import models.Book;
import models.BookWithPublisherName;
import models.Publisher;
import models.dto.BookSearchRange;
import service.book.search.SearchParam;
import utils.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class BookDaoImpl implements BookDao {
    public long getBooksCount(List<SearchParam> searchParams) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        StringBuilder query = new StringBuilder();
        query.append("select count(*) from BOOK");
        query.append(getSearchQuery(searchParams));

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query.toString());
        if (resultSet.next()) {
            long ret = resultSet.getLong(1);
            statement.close();
            connection.close();
            return ret;
        }
        statement.close();
        connection.close();
        return 0;
    }

    public BookSearchRange getSearchRange() throws SQLException, ClassNotFoundException {
        BookSearchRange bookSearchRange = new BookSearchRange();
        Connection connection = ConnectionProvider.getConnection();
        String query = "SELECT MIN(PUBLICATION_YEAR), MAX(PUBLICATION_YEAR), MIN(PRICE), MAX(PRICE) FROM BOOK";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        bookSearchRange.setYearMin(resultSet.getLong(1));
        bookSearchRange.setYearMax(resultSet.getLong(2));
        bookSearchRange.setPriceMin(resultSet.getFloat(3));
        bookSearchRange.setPriceMax(resultSet.getFloat(4));
        statement.close();
        connection.close();

        return bookSearchRange;
    }

    public List<Book> getBooks(long pageIndex, int itemsPerPage, long booksCount, List<SearchParam> searchParams) throws SQLException, ClassNotFoundException {
        long start = pageIndex * itemsPerPage;
        Connection connection = ConnectionProvider.getConnection();
        StringBuilder query = new StringBuilder();
        query.append("SELECT ISBN, TITLE, PUBLICATION_YEAR, PRICE, THRESHOLD, AVAILABLE_QUANTITY," +
                "CATEGORY, STOCK_ORDER_QUANTITY FROM BOOK");

        query.append(getSearchQuery(searchParams));

        query.append(" ORDER BY CREATION_DATE");
        query.append(" LIMIT ").append(itemsPerPage).append(" OFFSET ").append(Math.max(booksCount-start, 0));
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query.toString());
        List<Book> books = new ArrayList<Book>();
        while (resultSet.next()) {
            Book book = getBook(resultSet);
            books.add(book);
        }
        Collections.reverse(books);
        statement.close();
        connection.close();
        return books;
    }

    private String getSearchQuery(List<SearchParam> searchParams) {
        StringBuilder query = new StringBuilder();
        for (SearchParam searchParam : searchParams) {
            query.append(" ").append(searchParam.getEvaluationValue());
        }

        return query.toString();
    }

    public Book getBook(String ISBN) throws SQLException, ClassNotFoundException, EmptyResultSetException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from BOOK inner join PUBLISHER " +
                "on BOOK.PUBLISHER_ID = PUBLISHER.ID where ISBN = ?");
        preparedStatement.setString(1, ISBN);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Book book = new Book();
            book.setIsbn(resultSet.getString(1));
            book.setTitle(resultSet.getString(2));
            book.setPublicationYear(resultSet.getInt(4));
            book.setPrice(resultSet.getFloat(5));
            book.setThreshold(resultSet.getInt(6));
            book.setAvailableQuantity(resultSet.getInt(7));
            book.setCategory(resultSet.getString(8));
            book.setStockOrderQuantity(resultSet.getInt(9));
            Publisher publisher = new Publisher();
            publisher.setId(resultSet.getLong(11));
            publisher.setName(resultSet.getString(12));
            publisher.setAddress(resultSet.getString(13));
            publisher.setPhone(resultSet.getString(14));
            book.setPublisher(publisher);
            preparedStatement.close();
            connection.close();
            return book;
        }
        preparedStatement.close();
        connection.close();
        throw new EmptyResultSetException("No Book with this ISBN");
    }

    public void addBook(Book book) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        connection.setAutoCommit(false);
        String addBookQuery = "INSERT INTO BOOK (ISBN, TITLE, PUBLISHER_ID, PUBLICATION_YEAR," +
                " PRICE, THRESHOLD, CATEGORY, STOCK_ORDER_QUANTITY) values (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement addBook = connection.prepareStatement(addBookQuery);
        PreparedStatement addBookAuthor = null;
        addBook.setString(1, book.getIsbn());
        addBook.setString(2, book.getTitle());
        addBook.setLong(3, book.getPublisher().getId());
        addBook.setInt(4, book.getPublicationYear());
        addBook.setFloat(5, book.getPrice());
        addBook.setInt(6, book.getThreshold());
        addBook.setString(7, book.getCategory());
        addBook.setInt(8, book.getStockOrderQuantity());
        addBook.executeUpdate();
        try {
            String bookAuthorQuery = "INSERT INTO BOOK_AUTHOR (BOOK_ISBN, AUTHOR_ID) values (?, ?)";
            addBookAuthor = connection.prepareStatement(bookAuthorQuery);
            for (Author author : book.getAuthors()) {
                addBookAuthor.setString(1, book.getIsbn());
                addBookAuthor.setLong(2, author.getId());
                addBookAuthor.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            if (addBookAuthor != null) addBookAuthor.close();
            addBook.close();
            connection.close();
        }
    }

    public void addBooks(Set<Book> books) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        connection.setAutoCommit(false);
        String addBookQuery = "INSERT INTO BOOK (ISBN, TITLE, PUBLISHER_ID, PUBLICATION_YEAR," +
                " PRICE, THRESHOLD, CATEGORY) values (?, ?, ?, ?, ?, ?, ?)";
        String bookAuthorQuery = "INSERT INTO BOOK_AUTHOR (BOOK_ISBN, AUTHOR_ID) values (?, ?)";
        PreparedStatement addBook = connection.prepareStatement(addBookQuery);
        PreparedStatement addBookAuthor = connection.prepareStatement(bookAuthorQuery);;
        for (Book book : books) {
            addBook.setString(1, book.getIsbn());
            addBook.setString(2, book.getTitle());
            addBook.setLong(3, book.getPublisher().getId());
            addBook.setInt(4, book.getPublicationYear());
            addBook.setFloat(5, book.getPrice());
            addBook.setInt(6, book.getThreshold());
            addBook.setString(7, book.getCategory());
            try {
                addBook.executeUpdate();
                for (Author author : book.getAuthors()) {
                    addBookAuthor.setString(1, book.getIsbn());
                    addBookAuthor.setLong(2, author.getId());
                    addBookAuthor.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
                addBook.close();
                if (addBookAuthor != null) addBookAuthor.close();
                connection.close();
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        addBook.close();
        addBookAuthor.close();
        connection.close();
    }

    public List<BookWithPublisherName> getBooksByStartAndLength(int start, int length) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select ISBN, TITLE, " +
                "PUBLICATION_YEAR, PRICE, THRESHOLD,  AVAILABLE_QUANTITY, " +
                "CATEGORY, STOCK_ORDER_QUANTITY, PUBLISHER.NAME " +
                "from BOOK INNER JOIN PUBLISHER ON BOOK.PUBLISHER_ID = PUBLISHER.ID" +
                " LIMIT " + start + ", "  + length);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<BookWithPublisherName> books = new ArrayList<BookWithPublisherName>();
        while (resultSet.next()) {
            Book book = getBook(resultSet);
            String publisherName = resultSet.getString(9);
            books.add(new BookWithPublisherName(book, publisherName));
        }
        preparedStatement.close();
        connection.close();
        return books;
    }

    public void updateBook(String oldISBN, Book book, List<Long> removedAuthors, List<Long> addedAuthors) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement editBook = null;
        PreparedStatement addBookAuthor = null;
        PreparedStatement deleteBookAuthor = null;
        try {
            String editBookQuery = "UPDATE BOOK SET " +
                    "ISBN = ?, TITLE = ?, PUBLISHER_ID = ?, PUBLICATION_YEAR = ?," +
                    " PRICE = ?, THRESHOLD = ?, CATEGORY = ?, STOCK_ORDER_QUANTITY = ? " +
                    "WHERE ISBN = ?";
            editBook = connection.prepareStatement(editBookQuery);
            editBook.setString(1, book.getIsbn());
            editBook.setString(2, book.getTitle());
            editBook.setLong(3, book.getPublisher().getId());
            editBook.setInt(4, book.getPublicationYear());
            editBook.setFloat(5, book.getPrice());
            editBook.setInt(6, book.getThreshold());
            editBook.setString(7, book.getCategory());
            editBook.setInt(8, book.getStockOrderQuantity());
            editBook.setString(9, oldISBN);
            editBook.executeUpdate();
            String insertAuthorQuery = "INSERT INTO BOOK_AUTHOR (BOOK_ISBN, AUTHOR_ID) values (?, ?)";
            addBookAuthor = connection.prepareStatement(insertAuthorQuery);
            for (Long addedId : addedAuthors) {
                addBookAuthor.setString(1, book.getIsbn());
                addBookAuthor.setLong(2, addedId);
                addBookAuthor.executeUpdate();
            }
            String deleteAuthorQuery = "DELETE FROM BOOK_AUTHOR WHERE BOOK_ISBN = ? AND AUTHOR_ID = ?";
            deleteBookAuthor = connection.prepareStatement(deleteAuthorQuery);
            for (Long removedId : removedAuthors) {
                deleteBookAuthor.setString(1, book.getIsbn());
                deleteBookAuthor.setLong(2, removedId);
                deleteBookAuthor.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
            if (editBook != null) editBook.close();
            if (addBookAuthor != null) addBookAuthor.close();
            if (deleteBookAuthor != null) deleteBookAuthor.close();
            connection.close();
        }
    }

    public long getBooksCount() throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        StringBuilder query = new StringBuilder();
        query.append("select count(*) from BOOK");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query.toString());
        if (resultSet.next()) {
            long ret = resultSet.getLong(1);
            statement.close();
            connection.close();
            return ret;
        }
        statement.close();
        connection.close();
        return 0;
    }

    public long getBooksCount(String search) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        StringBuilder query = new StringBuilder();
        query.append("select count(*) from BOOK WHERE TITLE like " + "\"%" + search + "%\" " +
                "or ISBN like \"%" + search +"%\"");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query.toString());
        if (resultSet.next()) {
            long ret = resultSet.getLong(1);
            statement.close();
            connection.close();
            return ret;
        }
        statement.close();
        connection.close();
        return 0;
    }

    public List<BookWithPublisherName> getBooksByStartAndLength(int start, int length, String search) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select ISBN, TITLE, " +
                "PUBLICATION_YEAR, PRICE, THRESHOLD,  AVAILABLE_QUANTITY, " +
                "CATEGORY, STOCK_ORDER_QUANTITY, PUBLISHER.NAME " +
                "from BOOK INNER JOIN PUBLISHER ON BOOK.PUBLISHER_ID = PUBLISHER.ID" +
                " WHERE TITLE like \"%" + search +"%\" OR ISBN like \"%" + search +
                "%\" LIMIT " + start + ", "  + length);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<BookWithPublisherName> books = new ArrayList<BookWithPublisherName>();
        while (resultSet.next()) {
            Book book = getBook(resultSet);
            String publisherName = resultSet.getString(9);
            books.add(new BookWithPublisherName(book, publisherName));
        }
        preparedStatement.close();
        connection.close();
        return books;
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setIsbn(resultSet.getString(1));
        book.setTitle(resultSet.getString(2));
        book.setPublicationYear(resultSet.getInt(3));
        book.setPrice(resultSet.getFloat(4));
        book.setThreshold(resultSet.getInt(5));
        book.setAvailableQuantity(resultSet.getInt(6));
        book.setCategory(resultSet.getString(7));
        book.setStockOrderQuantity(resultSet.getInt(8));
        return book;
    }
}
