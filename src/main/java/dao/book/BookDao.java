/**
 * Created by programajor on 5/4/18.
 */

package dao.book;

import exceptions.EmptyResultSetException;
import models.Book;
import models.BookWithPublisherName;
import models.dto.BookSearchRange;
import service.book.search.SearchParam;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface BookDao {

    void addBook(Book book) throws SQLException, ClassNotFoundException;
    void addBooks(Set<Book> books) throws SQLException, ClassNotFoundException;

    BookSearchRange getSearchRange() throws SQLException, ClassNotFoundException;

    void updateBook(String oldISBN, Book book, List<Long> removedAuthors, List<Long> addedAuthors) throws SQLException, ClassNotFoundException;

    long getBooksCount(List<SearchParam> searchParams) throws SQLException, ClassNotFoundException;
    long getBooksCount() throws SQLException, ClassNotFoundException;
    long getBooksCount(String search) throws SQLException, ClassNotFoundException;

    Book getBook(String ISBN) throws SQLException, ClassNotFoundException, EmptyResultSetException;
    List<Book> getBooks(long pageIndex, int itemsPerPage, long booksCount, List<SearchParam> searchParams) throws SQLException, ClassNotFoundException;
    List<BookWithPublisherName> getBooksByStartAndLength(int start, int length, String search) throws SQLException, ClassNotFoundException;
    List<BookWithPublisherName> getBooksByStartAndLength(int start, int length) throws SQLException, ClassNotFoundException;
}
