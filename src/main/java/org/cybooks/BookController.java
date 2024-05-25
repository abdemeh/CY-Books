package org.cybooks;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Controller class for managing individual book entries.
 */
public class BookController {
    @FXML
    private Rectangle book_image;

    @FXML
    private Text book_title;

    @FXML
    private Text book_author;

    @FXML
    private Text book_category;

    @FXML
    private Text book_language;

    @FXML
    private Text book_date;

    @FXML
    private Text book_isbn;

    /**
     * Sets data for the book entry.
     *
     * @param book the book data to display
     */
    public void setData(Book book){
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        if(book.getTitle().length()>70){
            book_title.setText(book.getTitle().substring(0, 67) + "...");
        }else{
            book_title.setText(book.getTitle());
        }
        book_isbn.setText(book.getIsbn());
        book_author.setText(book.getAuthor());
        book_category.setText(new Category().getLibelle(book.getCategory()));
        book_language.setText(book.getLanguage());
        book_date.setText(df.format(book.getPublicationDate()));
        book_image.setFill(new ImagePattern(new Image(book.getImageUrl())));
    }

    /**
     * Copies the text content of the source text element to the clipboard.
     *
     * @param mouseEvent the MouseEvent triggering the action
     */
    public void copyToClipboard(MouseEvent mouseEvent) {
        Text source = (Text) mouseEvent.getSource();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getText());
        clipboard.setContent(content);
    }
}
