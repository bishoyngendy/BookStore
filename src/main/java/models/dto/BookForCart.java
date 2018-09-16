package models.dto;

/**
 * BookForCart
 * Created on: May 08, 2018
 * Author: marc
 */
public class BookForCart {
    private String ISBN;
    private float price;
    private int quantity;
    private String title;

    public BookForCart(String ISBN, String title, float price, int quantity) {
        this.quantity = 1;
        this.ISBN = ISBN;
        this.price = price;
        this.title = title;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decreaseQuantity() {
        this.quantity--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookForCart that = (BookForCart) o;

        return ISBN.equals(that.ISBN);
    }

    @Override
    public int hashCode() {
        return ISBN.hashCode();
    }
}
