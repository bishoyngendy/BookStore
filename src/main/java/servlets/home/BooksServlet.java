package servlets.home;

import com.sun.media.sound.InvalidFormatException;
import models.Book;
import service.book.BookService;
import service.book.BookServiceImpl;
import service.book.search.BookSearchParamFactory;
import service.book.search.BookSearchParamFactorySingleton;
import service.book.search.SearchParam;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebServlet({"/books", ""})
public class BooksServlet extends HttpServlet {

    private static final int ITEMS_PER_PAGE = 12;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        List<SearchParam> searchParams = getSearchParams(request.getParameterMap());

        long booksCount = bookService.getBooksCount(searchParams);
        long pagesCount = (long) Math.ceil(booksCount / (1.0 * ITEMS_PER_PAGE));
        long index = getValidIndex(request.getParameter("index"), pagesCount);
        List<Book> books = bookService.getBooks(index, ITEMS_PER_PAGE, booksCount, searchParams);
        long pagingStart = 1;
        long pagingEnd = pagesCount;
        if (pagesCount <= 5) {
            pagingEnd = pagesCount;
        } else if(index <= 2) {
            pagingEnd = 5;
        } else if (index >= (pagesCount - 1)) {
            pagingStart = index - 4 + (pagesCount - index);
        } else {
            pagingStart = index - 2;
            pagingEnd = index + 2;
        }

        request.setAttribute("count", pagesCount);
        request.setAttribute("searchURL", getSearchUrl(request.getParameterMap()));
        request.setAttribute("pagingStart", pagingStart);
        request.setAttribute("pagingEnd", pagingEnd);
        request.setAttribute("searchRange", bookService.getSearchRange());
        request.setAttribute("index", index);
        request.setAttribute("start", Math.min(booksCount, (index - 1) * ITEMS_PER_PAGE + 1));
        request.setAttribute("end", Math.min(booksCount, (index - 1) * ITEMS_PER_PAGE + ITEMS_PER_PAGE));
        request.setAttribute("total", booksCount);
        request.setAttribute("bookList", books);
        request.getRequestDispatcher("/home/books-all.jsp").forward(request, response);
    }

    private long getValidIndex(String indexStr, long count) {
        long index = 1;
        if (indexStr != null) {
            index = Long.valueOf(indexStr);
            if (index < 1) {
                index = 1;
            } else if (index > count) {
                index = count;
            }
        }

        return index;
    }

    private List<SearchParam> getSearchParams(Map<String, String[]> paramsMap) throws InvalidFormatException {
        List<SearchParam> searchParams = new LinkedList<SearchParam>();
        BookSearchParamFactory bookSearchParamFactory = BookSearchParamFactorySingleton.getInstance();
        int size = paramsMap.size();
        if (paramsMap.containsKey("index")) size--;

        if (size > 0) {
            // add join if needed
            if (paramsMap.containsKey("authors")) {
                searchParams.add(bookSearchParamFactory.getSearchParam("join"));
            }

            searchParams.add(bookSearchParamFactory.getSearchParam("where"));
            boolean first = true;
            for (Map.Entry<String, String[]> param : paramsMap.entrySet()) {
                if (param.getKey().equals("index")) continue;
                if (!first) searchParams.add(bookSearchParamFactory.getSearchParam("and"));
                first = false;
                String key = param.getKey();
                String[] values = param.getValue();
                SearchParam searchParam = bookSearchParamFactory.getSearchParam(key);
                searchParam.setValues(Arrays.asList(values));
                searchParams.add(searchParam);
            }
        }
        return searchParams;
    }

    private String getSearchUrl(Map<String, String[]> paramsMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String[]> param : paramsMap.entrySet()) {
            if (!param.getKey().equals("index")) {
                for (String value : param.getValue()) {
                    stringBuilder.append("&").append(param.getKey()).append("=").append(value);
                }
            }
        }

        return stringBuilder.toString();
    }
}
