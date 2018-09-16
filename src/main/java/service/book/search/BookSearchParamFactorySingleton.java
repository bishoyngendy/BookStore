package service.book.search;

import com.sun.media.sound.InvalidFormatException;

import java.util.Map;

import static service.book.search.RelationalOperation.*;
import static service.book.search.RelationalOperation.EQUAL;

/**
 * BookSearchParamFactorySingleton
 * Created on: May 13, 2018
 * Author: marc
 */
public class BookSearchParamFactorySingleton implements BookSearchParamFactory {
    private static BookSearchParamFactorySingleton ourInstance = new BookSearchParamFactorySingleton();

    public static BookSearchParamFactorySingleton getInstance() {
        return ourInstance;
    }

    private BookSearchParamFactorySingleton() {
    }

    public SearchParam getSearchParam(String key) throws InvalidFormatException {
        if (key.equals("yearMin")) return new OneOperation("PUBLICATION_YEAR", BIGGER_THAN_EQUAL);
        if (key.equals("yearMax")) return new OneOperation("PUBLICATION_YEAR", LESS_THAN_EQUAL);
        if (key.equals("priceMin")) return new OneOperation("PRICE", BIGGER_THAN_EQUAL);
        if (key.equals("priceMax")) return new OneOperation("PRICE", LESS_THAN_EQUAL);
        if (key.equals("title")) return new ContainsOperation("TITLE", CONTAINS);
        if (key.equals("isbn")) return new StartWithOperation("ISBN", START_WITH);
        if (key.equals("publisher")) return new OneOperation("PUBLISHER_ID", EQUAL);
        if (key.equals("authors")) return new MultipleOperations("AUTHOR_ID", EQUAL);
        if (key.equals("category")) return new OneOperation("CATEGORY", EQUAL);
        if (key.equals("inStock")) return new OneOperation("AVAILABLE_QUANTITY", NOT_EQUAL);
        if (key.equals("where")) return new NoOperation("where", NO_OPERATION);
        if (key.equals("join")) return new NoOperation("JOIN BOOK_AUTHOR on ISBN = BOOK_ISBN", NO_OPERATION);
        if (key.equals("and")) return new NoOperation("and", NO_OPERATION);
        if (key.equals("index")) return new NoOperation("", NO_OPERATION);
        throw new InvalidFormatException("Invalid Key for search");
    }
}
