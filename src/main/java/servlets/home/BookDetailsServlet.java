package servlets.home;

import exceptions.EmptyResultSetException;
import models.Author;
import models.Book;
import service.author.AuthorService;
import service.author.AuthorServiceImpl;
import service.book.BookService;
import service.book.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/book")
public class BookDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        String ISBN = request.getParameter("isbn");
        if (ISBN == null) {
            response.sendRedirect("/books");
            return;
        }
        try {
            Book book = bookService.getBook(ISBN);
            request.setAttribute("title", book.getTitle());
            request.setAttribute("price", book.getPrice());
            request.setAttribute("category", book.getCategory());
            request.setAttribute("year", book.getPublicationYear());
            request.setAttribute("available", book.getAvailableQuantity());
            request.setAttribute("publisher", book.getPublisher());
            request.setAttribute("authors", book.getAuthors());
        } catch (EmptyResultSetException e) {
            response.sendRedirect("/books");
            return;
        }
        
        request.getRequestDispatcher("/home/book-details.jsp").forward(request, response);
    }
}
