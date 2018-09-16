package models;

import java.util.List;

/**
 * Book
 * Created on: May 05, 2018
 * Author: marc
 */
public class Book {
    private String isbn;
    private String title;
    private Publisher publisher;
    private int publicationYear;
    private float price;
    private int threshold;
    private int availableQuantity;
    private int stockOrderQuantity;
    private String category;
    private List<Author> authors;

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockOrderQuantity() {
        return stockOrderQuantity;
    }

    public void setStockOrderQuantity(int stockOrderQuantity) {
        this.stockOrderQuantity = stockOrderQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
