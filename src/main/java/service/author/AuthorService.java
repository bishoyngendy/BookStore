package service.author;

import exceptions.DuplicateKeyException;
import models.Author;

import java.util.List;

/**
 * AuthorService
 * Created on: May 05, 2018
 * Author: marc
 */
public interface AuthorService {
    /**.
     * Add new author to the system.
     * @param author the author to add.
     * @return the author id after add
     * @throws DuplicateKeyException on duplicate key values.
     */
    long addAuthor(Author author) throws DuplicateKeyException;

    List<Author> getAuthorsByNamePrefix(String namePrefix);

    List<Author> getBookAuthors(String ISBN);

}
