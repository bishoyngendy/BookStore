/**
 * Created by programajor on 5/4/18.
 */

package service.book;

import exceptions.DuplicateKeyException;
import exceptions.EmptyResultSetException;
import models.Book;
import models.BookWithPublisherName;
import models.dto.BookSearchRange;
import service.book.search.SearchParam;

import java.util.List;

public interface BookService {
    long getBooksCount(List<SearchParam> searchParams);
    List<Book> getBooks(long pageIndex, int itemsPerPage, long totalBooksCount, List<SearchParam> searchParams);
    Book getBook(String ISBN) throws EmptyResultSetException;
    void addBook(Book book) throws DuplicateKeyException;
    BookSearchRange getSearchRange();
    void updateBook(String oldISBN, Book book) throws DuplicateKeyException;

    List<BookWithPublisherName> getBooksByStartAndLength(int start, int length);
    long getBooksCount();

    List<BookWithPublisherName> getBooksByStartAndLength(int start, int length, String search);
    long getBooksCount(String search);
}
