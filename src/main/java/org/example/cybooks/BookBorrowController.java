package org.example.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BookBorrowController{
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
    private Book currentBook;
    @FXML
    Text loan_expired;
    @FXML
    Text book_restant;
    @FXML
    Text book_expire;
    public void setData(Loan loan) {
        String isbn = loan.getBookIsbn();
        List<Book> books = BookAPI.searchBooks(isbn, "", "", 1);
        if (!books.isEmpty()) {
            Book book = books.get(0);
            String pattern = "dd/MM/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);

            currentBook = book;

            book_title.setText(book.getTitle());
            book_isbn.setText(book.getIsbn());
            book_author.setText(book.getAuthor());
            book_category.setText(book.getCategory());
            book_language.setText(book.getLanguage());
            book_date.setText(df.format(book.getPublicationDate()));
            book_image.setFill(new ImagePattern(new Image(book.getimageUrl())));
            book_expire.setText("Emprunt expiration: "+df.format(loan.getExpiredDate()));
            if(loan.getRestant()>1 || loan.getRestant()==0){
                book_restant.setText(String.valueOf(loan.getRestant())+" jours réstants");
                book_restant.setStyle("-fx-text-fill: #846fcd;");
            } else if (loan.getRestant()<0) {
                book_restant.setText(String.valueOf(loan.getRestant()*(-1))+" jours dépassés");
                book_restant.setStyle("-fx-text-fill: #cc7070;");
            }else{
                book_restant.setText(String.valueOf(loan.getRestant())+" jour réstants");
                book_restant.setStyle("-fx-text-fill: #846fcd;");
            }
            if(loan.getExpired()){
                loan_expired.setVisible(true);
            }else{
                loan_expired.setVisible(false);
            }
        } else {
            // Handle the case when no book is found
        }
    }
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
