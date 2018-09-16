package service.book.search;

import com.sun.media.sound.InvalidFormatException;

/**
 * BookSearchParamFactory
 * Created on: May 13, 2018
 * Author: marc
 */
public interface BookSearchParamFactory {
    SearchParam getSearchParam(String key) throws InvalidFormatException;
}
