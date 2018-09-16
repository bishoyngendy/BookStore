package models;

import exceptions.EmptyResultSetException;
import models.dto.BookForCart;

import java.util.LinkedList;
import java.util.List;

/**
 * Cart
 * Created on: May 08, 2018
 * Author: marc
 */
public class Cart {
    private List<BookForCart> bookForCartList;
    private float totalOrder = 0;

    public Cart() {
        bookForCartList = new LinkedList<BookForCart>();
    }

    /**.
     * Add new book or increase the quantity of the book if already added.
     * @param book the book to add.
     */
    public void addBook(Book book, int quantity) {
        BookForCart bookForCart = getFromList(book);
        if (bookForCart == null) {
            bookForCart = new BookForCart(book.getIsbn(), book.getTitle(), book.getPrice(), quantity);
            bookForCartList.add(bookForCart);
        } else {
            bookForCart.increaseQuantity(quantity);
        }

        totalOrder += bookForCart.getPrice() * quantity;
    }

    /**.
     * Remove a copy from the given book.
     * Ex: If book1 has 5 quantities, it will decrease it to 4.
     * If quantity becomes zero, book is totally removed from card.
     * @param book the book to remove
     */
    public void removeBook(Book book) throws EmptyResultSetException {
        BookForCart bookForCart = getFromList(book);
        if (bookForCart == null) throw new EmptyResultSetException("Book not in cart to remove.");
        this.totalOrder -= bookForCart.getPrice();
        bookForCart.decreaseQuantity();
        if (bookForCart.getQuantity() == 0) {
            this.bookForCartList.remove(bookForCart);
        }
    }

    /**.
     * Remove all copies of a book from the cart.
     * @param book the book to remove
     */
    public void removeAllBook(Book book) throws EmptyResultSetException {
        BookForCart bookForCart = getFromList(book);
        if (bookForCart == null) throw new EmptyResultSetException("Book not in cart to remove.");
        this.totalOrder -= bookForCart.getPrice() * bookForCart.getQuantity();
        this.bookForCartList.remove(bookForCart);
    }

    private BookForCart getFromList(Book book) {
        for (BookForCart bookForCart : bookForCartList) {
            if (bookForCart.getISBN().equals(book.getIsbn())) return bookForCart;
        }

        return null;
    }

    public float getTotalOrder() {
        return totalOrder;
    }

    public List<BookForCart> getBooks() {
        return this.bookForCartList;
    }
}
