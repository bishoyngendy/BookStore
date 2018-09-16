package servlets.admin;

import com.google.gson.Gson;
import models.Book;
import models.BookWithPublisherName;
import models.User;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.book.search.SearchParam;
import service.user.UserService;
import service.user.UserServiceImpl;
import servlets.models.DatatableModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//urlPatterns = {"/admin/books"}
@WebServlet(urlPatterns = {"/admin", "/admin/books"})
public class BooksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String search = request.getParameter("search[value]");

        if (start != null && length != null) {
            BookService bookService = new BookServiceImpl();
            List<BookWithPublisherName> books;
            long totalCount;
            if (search.equals("")) {
                books = bookService.getBooksByStartAndLength(Integer.valueOf(start), Integer.valueOf(length));
                totalCount = bookService.getBooksCount();
            } else {
                books = bookService.getBooksByStartAndLength(Integer.valueOf(start), Integer.valueOf(length), search);
                totalCount = bookService.getBooksCount(search);
            }
            DatatableModel datatableModel = buildDataTableModel(books, totalCount);
            String json = new Gson().toJson(datatableModel);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            request.getRequestDispatcher("/admin/books.jsp").forward(request, response);
        }
    }

    private DatatableModel buildDataTableModel(List<BookWithPublisherName> books, long totalCount) {
        DatatableModel datatableModel = new DatatableModel();
        datatableModel.setRecordsTotal(totalCount);
        datatableModel.setRecordsFiltered(totalCount);
        for (BookWithPublisherName book : books) {
            List<String> strings = new ArrayList<String>();
            strings.add(book.getBook().getIsbn());
            strings.add(book.getBook().getTitle());
            strings.add(book.getPublisherName());
            strings.add(book.getBook().getPrice() + "");
            strings.add(book.getBook().getAvailableQuantity() + "");
            strings.add(book.getBook().getThreshold() + "");
            strings.add(book.getBook().getStockOrderQuantity() + "");
            datatableModel.getData().add(strings);
        }
        return datatableModel;
    }
}
