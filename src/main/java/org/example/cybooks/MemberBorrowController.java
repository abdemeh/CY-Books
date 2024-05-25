package org.example.cybooks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML
    private Pane paneNewEmprunt;
    @FXML
    private Pane paneEmprunts;
    @FXML
    private TextField textSearchBookISBN;
    @FXML
    private TextField textSearchBookTitre;
    @FXML
    private TextField textSearchBookAuteur;
    @FXML
    private TextField textSearchBookMaxRes;
    @FXML
    private Text textSearchBookMessage;
    private double xOffset = 0;
    private double yOffset = 0;
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void openNewBorrow() throws IOException {
        paneNewEmprunt.setVisible(true);
        paneEmprunts.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerManager.setMemberBorrowController(this);
        booksScrollPane.setFitToWidth(true);
        paneEmprunts.setVisible(true);
        paneNewEmprunt.setVisible(false);
    }
    public void searchBook() {
        String NombreResultats = textSearchBookMaxRes.getText();
        int number_max_records = 25;
        try {
            number_max_records = Integer.parseInt(NombreResultats);
            if (number_max_records < 0) {
                number_max_records = 0;
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid textSearchNombreResultats: Not a valid integer");
        }
        textSearchBookMessage.setText("Recherche en cours...");
        int finalNumber_max_records = number_max_records;
        Task<Void> searchTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                List<Book> searchResult = BookAPI.searchBooks(textSearchBookISBN.getText(), textSearchBookTitre.getText(), textSearchBookAuteur.getText(), finalNumber_max_records);
                Platform.runLater(() -> updateBooks(searchResult));
                return null;
            }
        };
        Thread searchThread = new Thread(searchTask);
        searchThread.setDaemon(true);
        searchThread.start();
    }
    public void updateBooks(List<Book> list_books) {
        Platform.runLater(() -> {
            try {
                booksGrid.getChildren().clear();
                for (int i = 0; i < list_books.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("book_new_borrow.fxml"));
                    AnchorPane dashboardPane = fxmlLoader.load();
                    BookNewBorrowController bookController = fxmlLoader.getController();
                    bookController.setData(list_books.get(i));
                    booksGrid.getChildren().add(dashboardPane);
                }
                if (list_books.isEmpty()) {
                    textSearchBookMessage.setText("Aucune résultat.");
                } else {
                    textSearchBookMessage.setText("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void updateBorrowedBooks(List<Loan> list_loans) {
        paneEmprunts.setVisible(true);
        paneNewEmprunt.setVisible(false);
        if(list_loans.size()>=5){
            btnAddEmprunt.setDisable(true);
        }else{
            if(UserContext.getCurrentUser().getState().equals("Bloqué") || UserContext.getCurrentUser().getState().equals("Suspendu") || UserContext.getCurrentUser().getState().equals("Inactif")){
                btnAddEmprunt.setDisable(true);
            }else{
                btnAddEmprunt.setDisable(false);
            }
        }
        textTotalEmprunts.setText(String.valueOf(list_loans.size()));
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
        });
    }
    public void setMemberData(Member currentMember) {
        UserContext.setCurrentUser(currentMember);
        System.out.println(currentMember);
        textCurrentIdUser.setText(String.valueOf(currentMember.getId()));
        textCurrentNameUser.setText(currentMember.getLastName()+" "+currentMember.getFirstName());
        textCurrentUserState.setText(currentMember.getState());
        if (currentMember.getState().equals("Actif")) {
            textCurrentUserState.setFill(Color.web("#6fcd7f"));
        } else if (currentMember.getState().equals("Bloqué") || currentMember.getState().equals("Suspendu")) {
            textCurrentUserState.setFill(Color.web("#cc7070"));
            btnAddEmprunt.setDisable(true);
        } else if (currentMember.getState().equals("Inactif")) {
            textCurrentUserState.setFill(Color.web("#ccc670"));
        }
        list_borrowed_loans=new ArrayList<>(LoanDAO.getLoans(Integer.parseInt(textCurrentIdUser.getText())));
        updateBorrowedBooks(list_borrowed_loans);
    }
}
