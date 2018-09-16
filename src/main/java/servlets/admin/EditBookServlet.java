package servlets.admin;

import com.sun.media.sound.InvalidFormatException;
import exceptions.DuplicateKeyException;
import exceptions.EmptyResultSetException;
import models.Author;
import models.Book;
import models.Publisher;
import service.book.BookService;
import service.book.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static servlets.admin.BookServletsUtil.getBookFromRequest;
import static servlets.admin.BookServletsUtil.handleNumberFormatException;
import static servlets.admin.BookServletsUtil.setDuplicateError;

/**
 * AddBookServlet
 * Created on: May 05, 2018
 * Author: marc
 */
@WebServlet("/admin/books/edit")
public class EditBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String isbn = request.getParameter("isbn");
        if (isbn != null) {
            BookService bookService = new BookServiceImpl();
            try {
                Book book = bookService.getBook(isbn);
                setAttributes(request, book);
                request.setAttribute("oldISBN", book.getIsbn());
            } catch (EmptyResultSetException e) {
                e.printStackTrace();
                response.sendRedirect("/admin/books/add");
                return;
            }
            request.getRequestDispatcher("/admin/edit-book.jsp").forward(request, response);
        } else {
            response.sendRedirect("/admin/books/add");
        }
    }

    private void setAttributes(HttpServletRequest request, Book book) {
        if (book != null) {
            request.setAttribute("isbn", book.getIsbn());
            request.setAttribute("title", book.getTitle());
            request.setAttribute("category", book.getCategory());
            request.setAttribute("stock_order_quamntity", book.getStockOrderQuantity());
            request.setAttribute("threshold", book.getThreshold());
            request.setAttribute("price", book.getPrice());
            request.setAttribute("publication_year", book.getPublicationYear());
            request.setAttribute("publisher_id", book.getPublisher().getId());
            request.setAttribute("publisher_name", book.getPublisher().getName());
            request.setAttribute("authors", book.getAuthors());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldISBN = null;
        Book book = null;
        BookService bookService = new BookServiceImpl();
        try {
            oldISBN = request.getParameter("oldISBN");
            book = getBookFromRequest(request);
            bookService.updateBook(oldISBN, book);
            initializeAuthorsAndPublisher(book, bookService);
            setAttributes(request, book);
            request.setAttribute("oldISBN", book.getIsbn());
            request.setAttribute("success", "Book edited successfully");
        } catch (NumberFormatException e) {
            try {
                book = bookService.getBook(oldISBN);
            } catch (EmptyResultSetException e1) {
                e1.printStackTrace();
            }
            setAttributes(request, book);
            request.setAttribute("oldISBN", book.getIsbn());
            handleNumberFormatException(e, request);
            request.getRequestDispatcher("/admin/edit-book.jsp").forward(request, response);
            return;
        } catch (InvalidFormatException e) {
            handleInvalidError(request, oldISBN, book, bookService);
//            response.sendRedirect("/admin/books/edit?isbn=" + oldISBN);
            request.getRequestDispatcher("/admin/edit-book.jsp").forward(request, response);
            return;
        } catch (DuplicateKeyException e) {
            handleDuplicateError(request, oldISBN, book, bookService, e);
//            response.sendRedirect("/admin/books/edit?isbn=" + oldISBN);
            request.getRequestDispatcher("/admin/edit-book.jsp").forward(request, response);
            return;
        }
//        response.sendRedirect("/admin/books/edit?isbn=" + book.getIsbn());
        request.getRequestDispatcher("/admin/edit-book.jsp").forward(request, response);
    }

    private void initializeAuthorsAndPublisher(Book book, BookService bookService) {
        try {
            Book databaseBook = bookService.getBook(book.getIsbn());
            book.setPublisher(databaseBook.getPublisher());
            book.setAuthors(databaseBook.getAuthors());
        } catch (EmptyResultSetException e1) {
            e1.printStackTrace();
        }
    }

    private void handleInvalidError(HttpServletRequest request, String oldISBN, Book book, BookService bookService) {
        try {
            book = bookService.getBook(oldISBN);
        } catch (EmptyResultSetException e1) {
            e1.printStackTrace();
        }
        setAttributes(request, book);
        request.setAttribute("oldISBN", oldISBN);
    }

    private void handleDuplicateError(HttpServletRequest request, String oldISBN, Book book, BookService bookService, DuplicateKeyException e) {
        setDuplicateError(request, e);
        try {
            book = bookService.getBook(oldISBN);
            book.setIsbn(request.getParameter("ISBN"));
            book.setTitle(request.getParameter("title"));
        } catch (EmptyResultSetException e1) {
            e1.printStackTrace();
        }
        setAttributes(request, book);
        request.setAttribute("oldISBN", oldISBN);
    }

}
