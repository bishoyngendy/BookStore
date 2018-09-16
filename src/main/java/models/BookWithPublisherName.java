package models;

public class BookWithPublisherName {
    private Book book;
    private String publisherName;

    public BookWithPublisherName(Book book, String publisherName) {
        this.book = book;
        this.publisherName = publisherName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
