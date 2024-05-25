package org.example.cybooks;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BookNewBorrowController {
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
        book_category.setText(book.getCategory());
        book_language.setText(book.getLanguage());
        book_date.setText(df.format(book.getPublicationDate()));
        book_image.setFill(new ImagePattern(new Image(book.getimageUrl())));
    }
    public void NewBorrow(){
        if(LoanDAO.addNewLoan(book_isbn.getText(),UserContext.getCurrentUser().getId())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Emprunt ajouté avec succès");
            Image icon = new Image("file:assets/icon-no-text-white.png");
            Stage stage_alert = (Stage) alert.getDialogPane().getScene().getWindow();
            stage_alert.getIcons().add(icon);
            alert.showAndWait();
            MemberBorrowController memberBorrowController = ControllerManager.getMemberBorrowController();
            if (memberBorrowController != null) {
                memberBorrowController.updateBorrowedBooks(LoanDAO.getLoans(UserContext.getCurrentUser().getId()));
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Emprunt déjà exist!");
            Image icon = new Image("file:assets/icon-no-text-white.png");
            Stage stage_alert = (Stage) alert.getDialogPane().getScene().getWindow();
            stage_alert.getIcons().add(icon);
            alert.showAndWait();
        }
    }
}
