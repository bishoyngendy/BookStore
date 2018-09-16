package service.cart;

import exceptions.EmptyResultSetException;
import models.Book;
import models.Cart;
import service.book.BookService;
import service.book.BookServiceImpl;

/**
 * CartServiceImpl
 * Created on: May 10, 2018
 * Author: marc
 */
public class CartServiceImpl implements CartService {
    private BookService bookService;

    public CartServiceImpl() {
        this.bookService = new BookServiceImpl();
    }

    public void addBookToCart(Cart cart, String ISBN, int quantity) throws EmptyResultSetException {
        Book book = bookService.getBook(ISBN);
        cart.addBook(book, quantity);
    }

    public void removeBookFromCart(Cart cart, String ISBN) throws EmptyResultSetException {
        Book book = bookService.getBook(ISBN);
        cart.removeBook(book);
    }

    public void removeAllBookFromCart(Cart cart, String ISBN) throws EmptyResultSetException {
        Book book  = bookService.getBook(ISBN);
        cart.removeAllBook(book);
    }
}
