package org.cybooks;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Controller class for handling new book borrow operations.
 */
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
     * Handles the action of adding a new borrow for the current book.
     */
    public void NewBorrow() {
        Result result = LoanDAO.addNewLoan(book_isbn.getText(), MemberContext.getCurrentMember().getId());

        Alert alert = new Alert(result.isSuccess() ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING);
        alert.setTitle(result.isSuccess() ? "Succès" : "Alerte");
        alert.setHeaderText(null);
        alert.setContentText(result.getMessage());
        Image icon = new Image("file:src/main/assets/icon-no-text-white.png");
        Stage stage_alert = (Stage) alert.getDialogPane().getScene().getWindow();
        stage_alert.getIcons().add(icon);
        alert.showAndWait();

        if (result.isSuccess()) {
            MemberBorrowController memberBorrowController = ControllerManager.getMemberBorrowController();
            if (memberBorrowController != null) {
                memberBorrowController.updateBorrowedBooks(LoanDAO.getLoans(MemberContext.getCurrentMember().getId()));
            }
        }
    }
}
