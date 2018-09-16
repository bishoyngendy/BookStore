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
import java.sql.SQLException;
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
@WebServlet("/admin/books/add")
public class AddBookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/add-book.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Book book = getBookFromRequest(request);
            BookService bookService = new BookServiceImpl();
            bookService.addBook(book);
            request.setAttribute("success", "Book added successfully");
        } catch (NumberFormatException e) {
            handleNumberFormatException(e, request);
            request.getRequestDispatcher("/admin/add-book.jsp").forward(request, response);
            return;
        } catch (InvalidFormatException e) {
            request.getRequestDispatcher("/admin/add-book.jsp").forward(request, response);
            return;
        } catch (DuplicateKeyException e) {
            setDuplicateError(request, e);
            request.getRequestDispatcher("/admin/add-book.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/admin/add-book.jsp").forward(request, response);
    }
}
