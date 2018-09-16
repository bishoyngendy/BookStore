/**
 * Created by programajor on 5/4/18.
 */

package service.book;

import dao.book.BookDao;
import dao.book.BookDaoImpl;
import exceptions.DuplicateKeyException;
import exceptions.EmptyResultSetException;
import models.Author;
import models.Book;
import models.BookWithPublisherName;
import models.dto.BookSearchRange;
import service.author.AuthorService;
import service.author.AuthorServiceImpl;
import service.book.search.SearchParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.MySqlErrorCodes.MYSQL_DUPLICATE_COMPOSITE_PK;
import static utils.MySqlErrorCodes.MYSQL_DUPLICATE_PK;

public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    public BookServiceImpl() {
        this.bookDao = new BookDaoImpl();
    }

    public long getBooksCount(List<SearchParam> searchParam) {
        try {
            return this.bookDao.getBooksCount(searchParam);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Book> getBooks(long pageIndex, int itemsPerPage, long totalBooksCount, List<SearchParam> searchParams) {
        try {
            return this.bookDao.getBooks(pageIndex, itemsPerPage, totalBooksCount, searchParams);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<Book>();
    }

    public Book getBook(String ISBN) throws EmptyResultSetException {
        try {
            Book book = this.bookDao.getBook(ISBN);
            AuthorService authorService = new AuthorServiceImpl();
            book.setAuthors(authorService.getBookAuthors(book.getIsbn()));
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addBook(Book book) throws DuplicateKeyException{
        try {
            this.bookDao.addBook(book);
        } catch (SQLException e) {
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK) {
                throw new DuplicateKeyException(e.getMessage());
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BookSearchRange getSearchRange() {
        BookSearchRange bookSearchRange = null;
        try {
            bookSearchRange = this.bookDao.getSearchRange();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bookSearchRange;
    }

    public List<BookWithPublisherName> getBooksByStartAndLength(int start, int length) {
        try {
            return this.bookDao.getBooksByStartAndLength(start, length);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<BookWithPublisherName>();
    }

    public long getBooksCount() {
        try {
            return bookDao.getBooksCount();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<BookWithPublisherName> getBooksByStartAndLength(int start, int length, String search) {
        try {
            return this.bookDao.getBooksByStartAndLength(start, length, search);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<BookWithPublisherName>();
    }

    public long getBooksCount(String search) {
        try {
            return bookDao.getBooksCount(search);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateBook(String oldISBN, Book book) throws DuplicateKeyException {
        try {
            Book oldBook = this.getBook(oldISBN);
            List<Long> removedAuthors = getDifference(oldBook.getAuthors(), book.getAuthors());
            List<Long> addedAuthors = getDifference(book.getAuthors(), oldBook.getAuthors());
            this.bookDao.updateBook(oldISBN, book, removedAuthors, addedAuthors);
        } catch (SQLException e) {
            if (e.getErrorCode() == MYSQL_DUPLICATE_PK || e.getErrorCode() == MYSQL_DUPLICATE_COMPOSITE_PK) {
                throw new DuplicateKeyException(e.getMessage());
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (EmptyResultSetException e) {
            e.printStackTrace();
        }
    }

    private List<Long> getDifference(List<Author> first, List<Author> second) {
        List<Long> ids = new ArrayList<Long>();
        Set<Long> set = new HashSet<Long>();
        for (Author author : second) {
            set.add(author.getId());
        }
        for (Author author : first) {
            if (!set.contains(author.getId())) {
                ids.add(author.getId());
            }
        }
        return ids;
    }
}
