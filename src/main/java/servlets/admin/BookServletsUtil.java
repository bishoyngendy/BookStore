package servlets.admin;

import com.sun.media.sound.InvalidFormatException;
import exceptions.DuplicateKeyException;
import models.Author;
import models.Book;
import models.Publisher;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

public class BookServletsUtil {
    public static Book getBookFromRequest(HttpServletRequest request) throws InvalidFormatException, NumberFormatException {
        Book book = new Book();
        String ISBN = request.getParameter("ISBN");
        String title = request.getParameter("title");
        String publisherId = request.getParameter("publisher");
        String[] authors = request.getParameterValues("authors");
        String publicationYear = request.getParameter("publication_year");
        String price = request.getParameter("price");
        String threshold = request.getParameter("threshold");
        String category = request.getParameter("category");
        String stockOrderQuantity = request.getParameter("stock_order_quamntity");

        if (authors == null && publisherId == null) {
            request.setAttribute("error_author", "Author must be selected");
            request.setAttribute("error_publisher", "Publisher must be selected");
            throw new InvalidFormatException();
        } else if (publisherId == null) {
            request.setAttribute("error_publisher", "Publisher must be selected");
            throw new InvalidFormatException();
        } else if (authors == null) {
            request.setAttribute("error_author", "Author must be selected");
            throw new InvalidFormatException();
        }

        if (ISBN.contains(".")) {
            throw new NumberFormatException("Invalid ISBN Value");
        }
        book.setIsbn(ISBN);
        book.setCategory(category);
        try {
            book.setPrice(Float.parseFloat(price));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid Price Value");
        }
        try {
            book.setPublicationYear(Integer.valueOf(publicationYear));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid Publication Year Value");
        }
        book.setTitle(title);
        try {
            book.setThreshold(Integer.parseInt(threshold));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid Threshold Value");
        }
        try {
            book.setStockOrderQuantity(Integer.parseInt(stockOrderQuantity));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid Stock Order Quantity Value");
        }

        Publisher publisher = new Publisher();
        publisher.setId(Integer.parseInt(publisherId));
        book.setPublisher(publisher);

        List<Author> authorList = new LinkedList<Author>();
        for (String id : authors) {
            Author author = new Author();
            author.setId(Long.valueOf(id));
            authorList.add(author);
        }

        book.setAuthors(authorList);

        return book;
    }

    public static void setDuplicateError(HttpServletRequest request, DuplicateKeyException e) {
        if (e.getMessage().contains("TITLE") && e.getMessage().contains("PRIMARY")) {
            request.setAttribute("error_isbn", "ISBN already exist.");
            request.setAttribute("error_title", "Title already exist.");
        } else if (e.getMessage().contains("TITLE")) {
            request.setAttribute("error_title", "Title already exist.");
        } else if (e.getMessage().contains("PRIMARY")) {
            request.setAttribute("error_isbn", "ISBN already exist.");
        }
    }

    public static void handleNumberFormatException(NumberFormatException e, HttpServletRequest request) {
        if (e.getMessage().contains("ISBN")) {
            request.setAttribute("error_isbn", "Invalid ISBN should be an integer");
        } else if (e.getMessage().contains("Price")) {
            request.setAttribute("error_price", "Invalid Price ");
        } else if (e.getMessage().contains("Publication")) {
            request.setAttribute("error_publication_year", "Invalid Publication Year should be an integer");
        } else if (e.getMessage().contains("Threshold")) {
            request.setAttribute("error_threshold", "Invalid Threshold should be an integer");
        } else if (e.getMessage().contains("Stock")) {
            request.setAttribute("error_stock_order_quantity", "Invalid Stock Order Quantity should be an integer");
        }
    }
}
