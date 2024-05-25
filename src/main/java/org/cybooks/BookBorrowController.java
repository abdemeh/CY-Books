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
    @FXML
    Button btnRenew;
    @FXML
    Button btnReturn;
    private Loan currentLoan;
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
            book_category.setText(book.getCategory());
            book_language.setText(book.getLanguage());
            book_date.setText(df.format(book.getPublicationDate()));
            book_image.setFill(new ImagePattern(new Image(book.getimageUrl())));
            book_expire.setText("Emprunt expiration: "+df.format(loan.getExpiredDate()));
            if (loan.getRestant() > 1) {
                book_restant.setText(String.valueOf(loan.getRestant()) + " jours restants");
                book_restant.setFill(Color.web("#6fcd93"));
            } else if (loan.getRestant() == 0) {
                book_restant.setText(String.valueOf(loan.getRestant()) + " jours restants");
                book_restant.setFill(Color.web("#ccc670"));
            } else if (loan.getRestant() < 0) {
                book_restant.setText(String.valueOf(loan.getRestant() * (-1)) + " jours dépassés");
                book_restant.setFill(Color.web("#cc7070"));
            } else {
                book_restant.setText(String.valueOf(loan.getRestant()) + " jour restant");
                book_restant.setFill(Color.web("#6fcd93"));
            }
            if(loan.getExpired()){
                loan_expired.setVisible(true);
                btnRenew.setDisable(true);

            }else{
                loan_expired.setVisible(false);
            }
        }
    }
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void deleteLoan(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Etes-vous sûr(e) de vouloir renouveler cet emprunt ?");
        Image icon = new Image("file:assets/icon-no-text-white.png");
        Stage stage_alert = (Stage) alert.getDialogPane().getScene().getWindow();
        stage_alert.getIcons().add(icon);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            LoanDAO.deleteLoan(currentLoan.getIdLoan());
            // Notify DashboardController to update members
            MemberBorrowController memberBorrowController = ControllerManager.getMemberBorrowController();
            //DashboardController dashboardController = ControllerManager.getDashboardController();
            if (memberBorrowController != null) {
                memberBorrowController.updateBorrowedBooks(LoanDAO.getLoans(currentLoan.getIdMember()));
                //dashboardController.updateMembersFromDatabase();
            }
        }
    }
}
