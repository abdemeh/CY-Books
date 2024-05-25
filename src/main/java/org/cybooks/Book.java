package org.cybooks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    private String isbn;
    private String imageUrl;
    private String title;
    private String author;
    private String language;
    private String category;
    private Date publicationDate;

    public Book(String isbn, String imageUrl,String title, String author, String language, String category, Date publicationDate) {
        this.isbn = isbn;
        this.imageUrl = imageUrl;
        this.title = title;
        this.author = author;
        this.language = language;
        this.category = category;
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getimageUrl() {
        return imageUrl;
    }

    public void setimageUrl(String imageID) {
        this.imageUrl = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        Category category_obj = new Category();
        return category_obj.getLibelle(category);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

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
