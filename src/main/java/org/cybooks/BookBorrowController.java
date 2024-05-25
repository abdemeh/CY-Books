package org.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller class for displaying book borrowing information and handling related actions.
 */
public class BookBorrowController {
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
    @FXML
    private Text loan_expired;
    @FXML
    private Text book_restant;
    @FXML
    private Text book_expire;
    @FXML
    private Button btnRenew;
    @FXML
    private Button btnReturn;

    private Book currentBook;
    private Loan currentLoan;

    /**
     * Sets the data for displaying book borrowing details.
     *
     * @param loan the loan details to display
     */
    public void setData(Loan loan) {
        String isbn = loan.getBookIsbn();
        List<Book> books = BookAPI.searchBooks(isbn, "", "", 1);
        if (!books.isEmpty()) {
            Book book = books.get(0);
            String pattern = "dd/MM/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);

            currentBook = book;
            currentLoan = loan;

            book_title.setText(book.getTitle());
            book_isbn.setText(book.getIsbn());
            book_author.setText(book.getAuthor());
            book_category.setText(new Category().getLibelle(book.getCategory()));            book_language.setText(book.getLanguage());
            book_date.setText(df.format(book.getPublicationDate()));
            book_image.setFill(new ImagePattern(new Image(book.getImageUrl())));
            book_expire.setText("Emprunt expiration: " + df.format(loan.getExpirationDate()));

            int restant = loan.getRemainingDays();

            if (restant > 1) {
                book_restant.setText(restant + " jours restants");
                book_restant.setFill(Color.web("#6fcd93"));
            } else if (restant == 0) {
                book_restant.setText(restant + " jours restants");
                book_restant.setFill(Color.web("#ccc670"));
            } else if (restant < 0) {
                book_restant.setText((restant * (-1)) + " jours dépassés");
                book_restant.setFill(Color.web("#cc7070"));
            } else {
                book_restant.setText(restant + " jour restant");
                book_restant.setFill(Color.web("#6fcd93"));
            }

            if (loan.isExpired()) {
                loan_expired.setVisible(true);
                btnRenew.setDisable(true);
            } else {
                loan_expired.setVisible(false);
            }
        }
    }

    /**
     * Closes the window.
     *
     * @param event the ActionEvent triggering the action
     */
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Deletes the current loan.
     */
    public void deleteLoan() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr(e) de vouloir retourner cet emprunt ?");
        Image icon = new Image("file:src/main/assets/icon-no-text-white.png");
        Stage stage_alert = (Stage) alert.getDialogPane().getScene().getWindow();
        stage_alert.getIcons().add(icon);

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOui) {
            LoanDAO.deleteLoan(currentLoan.getIdLoan());
            MemberBorrowController memberBorrowController = ControllerManager.getMemberBorrowController();
            if (memberBorrowController != null) {
                memberBorrowController.updateBorrowedBooks(LoanDAO.getLoans(currentLoan.getIdMember()));
            }
        }
    }
    public void renewLoan() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr(e) de vouloir renouveler cet emprunt ?");
        Image icon = new Image("file:src/main/assets/icon-no-text-white.png");
        Stage stage_alert = (Stage) alert.getDialogPane().getScene().getWindow();
        stage_alert.getIcons().add(icon);

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOui) {
            LoanDAO.renewLoan(currentLoan.getIdLoan());
            MemberBorrowController memberBorrowController = ControllerManager.getMemberBorrowController();
            if (memberBorrowController != null) {
                memberBorrowController.updateBorrowedBooks(LoanDAO.getLoans(currentLoan.getIdMember()));
            }
        }
    }
}
