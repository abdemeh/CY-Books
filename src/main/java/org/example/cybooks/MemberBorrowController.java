package org.example.cybooks;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MemberBorrowController implements Initializable {
    private Book currentBook;
    @FXML
    private VBox booksGrid;
    @FXML
    private ScrollPane booksScrollPane;
    @FXML
    private Text textCurrentIdUser;
    @FXML
    private Text textTotalEmprunts;
    @FXML
    private Text textCurrentNameUser;
    @FXML
    private Button btnAddEmprunt;
    @FXML
    private Text textCurrentUserState;
    private List<Loan> list_borrowed_loans;
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerManager.setMemberBorrowController(this);
        booksScrollPane.setFitToWidth(true);
    }

    public void updateBorrowedBooks(List<Loan> list_loans) {
        if(list_loans.size()>=5){
            btnAddEmprunt.setDisable(true);
        }else{
            btnAddEmprunt.setDisable(false);
        }
        Platform.runLater(() -> {
            try{
                booksGrid.getChildren().clear();
                for (int i=0;i<list_loans.size();i++){
                    FXMLLoader fxmlLoader=new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("book_borrow.fxml"));
                    AnchorPane bookPane=fxmlLoader.load();
                    BookBorrowController cardController = fxmlLoader.getController();
                    cardController.setData(list_loans.get(i));
                    booksGrid.getChildren().add(bookPane);
                }
            }catch (
                    IOException e){
                e.printStackTrace();
            }
            textTotalEmprunts.setText(String.valueOf(list_borrowed_loans.size()));
        });
    }
    public void setMemberData(Member currentMember) {
        System.out.println(currentMember);
        textCurrentIdUser.setText(String.valueOf(currentMember.getId()));
        textCurrentNameUser.setText(currentMember.getLastName()+" "+currentMember.getFirstName());
        textCurrentUserState.setText(currentMember.getState());
        if (currentMember.getState().equals("Actif")) {
            textCurrentUserState.setFill(Color.web("#6fcd7f"));
        } else if (currentMember.getState().equals("Bloqué") || currentMember.getState().equals("Suspendu")) {
            textCurrentUserState.setFill(Color.web("#cc7070"));
        } else if (currentMember.getState().equals("Inactif")) {
            textCurrentUserState.setFill(Color.web("#ccc670"));
        }
        list_borrowed_loans=new ArrayList<>(LoanDAO.getLoans(Integer.parseInt(textCurrentIdUser.getText())));
        updateBorrowedBooks(list_borrowed_loans);
    }
}
