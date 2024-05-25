package org.cybooks;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a book entity.
 */
public class Book {
    private String isbn;
    private String imageUrl;
    private String title;
    private String author;
    private String language;
    private String category;
    private Date publicationDate;

    /**
     * Constructs a new Book with the specified attributes.
     *
     * @param isbn           the ISBN of the book
     * @param imageUrl       the URL of the book's image
     * @param title          the title of the book
     * @param author         the author of the book
     * @param language       the language of the book
     * @param category       the category of the book
     * @param publicationDate the publication date of the book
     */
    public Book(String isbn, String imageUrl, String title, String author, String language, String category, Date publicationDate) {
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.language = language;
        this.category = category;
        this.publicationDate = publicationDate;
    }

    /**
     * Gets the ISBN of the book.
     *
     * @return the ISBN of the book
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     *
     * @param isbn the ISBN of the book
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the URL of the book's image.
     *
     * @return the URL of the book's image
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the URL of the book's image.
     *
     * @param imageUrl the URL of the book's image
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title the title of the book
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author the author of the book
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the language of the book.
     *
     * @return the language of the book
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language of the book.
     *
     * @param language the language of the book
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets the category of the book.
     *
     * @return the category of the book
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category of the book.
     *
     * @param category the category of the book
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the publication date of the book.
     *
     * @return the publication date of the book
     */
    public Date getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the publication date of the book.
     *
     * @param publicationDate the publication date of the book
     */
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Returns a string representation of the Book.
     *
     * @return a string representation of the Book
     */
    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(publicationDate);
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", language='" + language + '\'' +
                ", category='" + category + '\'' +
                ", publicationDate='" + formattedDate + '\'' +
                '}';
    }
}
