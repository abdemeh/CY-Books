package org.cybooks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller class for the dashboard UI.
 */
public class DashboardController implements Initializable {

    @FXML
    private Rectangle imageTopBooks1;
    @FXML
    private Rectangle imageTopBooks2;
    @FXML
    private Rectangle imageTopBooks3;
    @FXML
    private Rectangle imageTopBooks4;
    @FXML
    private Text numberTopBooks1;
    @FXML
    private Text numberTopBooks2;
    @FXML
    private Text numberTopBooks3;
    @FXML
    private Text numberTopBooks4;
    @FXML
    private Text dashboardTitle;
    @FXML
    private Pane paneHome;
    @FXML
    private Pane paneUsers;
    @FXML
    private Pane paneBooks;
    @FXML
    private VBox membersVbox;
    @FXML
    private VBox booksVbox;
    private List<Member> list_members;
    private List<Book> list_books;

    @FXML
    private Text textSommeUtilisateurs;
    @FXML
    private ScrollPane usersScrollPane;
    @FXML
    private ScrollPane booksScrollPane;
    @FXML
    private Button btnSearchBook;
    @FXML
    private Text textSearchBookResultats;
    @FXML
    private TextField textSearchBookMaxRes;
    @FXML
    private TextField textSearchBookISBN;
    @FXML
    private TextField textSearchBookTitre;
    @FXML
    private TextField textSearchBookAuteur;
    @FXML
    private Text textSearchBookMessage;
    @FXML
    private TextField textSearchMember;
    @FXML
    private Text dashboardNombreTotalMembers;
    @FXML
    private Text dashboardNombreTotalEmprunts;
    @FXML
    private Text dashboardNombreTotalEmpruntsExpired;
    @FXML
    private Pane paneTopFourBooks;
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerManager.setDashboardController(this);
        List<String[]> topBooks = LoanDAO.getTopFourBooks();
        try {
            imageTopBooks1.setFill(new ImagePattern(new Image(BookAPI.searchBook(topBooks.get(0)[0]).getImageUrl())));
            numberTopBooks1.setText(String.valueOf("Total: "+topBooks.get(0)[1]));
            paneTopFourBooks.setVisible(true);
            imageTopBooks1.setVisible(true);
            numberTopBooks1.setVisible(true);

            imageTopBooks2.setFill(new ImagePattern(new Image(BookAPI.searchBook(topBooks.get(1)[0]).getImageUrl())));
            numberTopBooks2.setText(String.valueOf("Total: "+topBooks.get(1)[1]));
            imageTopBooks2.setVisible(true);
            numberTopBooks2.setVisible(true);

            imageTopBooks3.setFill(new ImagePattern(new Image(BookAPI.searchBook(topBooks.get(2)[0]).getImageUrl())));
            numberTopBooks3.setText(String.valueOf("Total: "+topBooks.get(2)[1]));
            imageTopBooks3.setVisible(true);
            numberTopBooks3.setVisible(true);

            imageTopBooks4.setFill(new ImagePattern(new Image(BookAPI.searchBook(topBooks.get(3)[0]).getImageUrl())));
            numberTopBooks4.setText(String.valueOf("Total: "+topBooks.get(3)[1]));
            imageTopBooks4.setVisible(true);
            numberTopBooks4.setVisible(true);
        } catch (Exception e) {

        }
        refreshDashboard();
        dashboardTitle.setText("Accueil");
        paneHome.setVisible(true);
        paneUsers.setVisible(false);
        paneBooks.setVisible(false);

        textSearchBookMessage.setText("");

        usersScrollPane.setFitToWidth(true);
        booksScrollPane.setFitToWidth(true);

        LoanDAO.updateUsersState();
        updateMembers(new ArrayList<>(getMembersFromDatabase()));

        //list_books=new ArrayList<>(BookAPI.searchBooks("","","",25));
        //updateBooks(list_books);
    }

    public void refreshDashboard(){
        dashboardNombreTotalMembers.setText(String.valueOf(MemberDAO.getAllMembers().size()));
        dashboardNombreTotalEmprunts.setText(String.valueOf(LoanDAO.getTotalLoans()));
        dashboardNombreTotalEmpruntsExpired.setText(String.valueOf(LoanDAO.getLoanExpiredCount()));
    }

    /**
     * Updates the member list from the database and displays it.
     */
    public void updateMembersFromDatabase() {
        List<Member> updatedMembers = getMembersFromDatabase();
        updateMembers(updatedMembers);
    }

    /**
     * Updates the displayed member list.
     *
     * @param list_members the list of members to display
     */
    public void updateMembers(List<Member> list_members) {
        Platform.runLater(() -> {
            try {
                membersVbox.getChildren().clear();
                for (int i = 0; i < list_members.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("member.fxml"));
                    AnchorPane dashboardPane = fxmlLoader.load();
                    MemberController cardController = fxmlLoader.getController();
                    cardController.setData(list_members.get(i));
                    membersVbox.getChildren().add(dashboardPane);
                }
                textSommeUtilisateurs.setText(Integer.toString(list_members.size()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Updates the displayed book list.
     *
     * @param list_books the list of books to display
     */
    public void updateBooks(List<Book> list_books) {
        Platform.runLater(() -> {
            try {
                booksVbox.getChildren().clear();
                for (int i = 0; i < list_books.size(); i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                    AnchorPane dashboardPane = fxmlLoader.load();
                    BookController bookController = fxmlLoader.getController();
                    bookController.setData(list_books.get(i));
                    booksVbox.getChildren().add(dashboardPane);
                }
                textSearchBookResultats.setText(Integer.toString(list_books.size()));
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

    /**
     * Searches for members based on the input criteria and updates the displayed member list.
     */
    public void searchMember(){
        List<Member> list_members = MemberDAO.searchMembers(textSearchMember.getText());
        updateMembers(list_members);
    }

    /**
     * Searches for books based on the input criteria and updates the displayed book list.
     */
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

    /**
     * Retrieves all members from the database.
     *
     * @return the list of all members
     */
    public List<Member> getMembersFromDatabase() {
        List<Member> list_members = MemberDAO.getAllMembers();
        return list_members;
    }

    /**
     * Opens the window to add a new member.
     *
     * @throws IOException if an error occurs while loading the FXML file
     */
    @FXML
    private void openMemberAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("member_add.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:src/main/assets/icon-no-text-white.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

    }

    /**
     * Closes the current window.
     *
     * @param event the action event
     */
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Switches to the home view.
     *
     * @param event the action event
     */
    public void actionHome(ActionEvent event) {
        refreshDashboard();
        dashboardTitle.setText("Accueil");
        paneHome.setVisible(true);
        paneUsers.setVisible(false);
        paneBooks.setVisible(false);
    }

    /**
     * Switches to the users view.
     *
     * @param event the action event
     */
    public void actionUsers(ActionEvent event) {
        dashboardTitle.setText("Utilisateurs");
        paneUsers.setVisible(true);
        paneHome.setVisible(false);
        paneBooks.setVisible(false);
    }

    /**
     * Switches to the books view.
     *
     * @param event the action event
     */
    public void actionBooks(ActionEvent event) {
        dashboardTitle.setText("Livres");
        paneBooks.setVisible(true);
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
    }

    /**
     * Switches to the settings view.
     *
     * @param event the action event
     */
    public void actionSettings(ActionEvent event) {
        dashboardTitle.setText("Paramètres");
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
        paneBooks.setVisible(false);
    }

    /**
     * Switches to the profile view.
     *
     * @param event the action event
     */
    public void actionProfile(ActionEvent event) {
        dashboardTitle.setText("Mon profil");
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
        paneBooks.setVisible(false);
    }

    /**
     * Logs out the user and returns to the login screen.
     *
     * @param event the action event
     */
    public void actionLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr(e) de déconnecter ?");
        Image icon = new Image("file:src/main/assets/icon-no-text-white.png");
        Stage stage_alert = (Stage) alert.getDialogPane().getScene().getWindow();
        stage_alert.getIcons().add(icon);

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOui) {
            // Create a new task for loading the dashboard FXML
            Task<Parent> loadLoginTask = new Task<>() {
                @Override
                protected Parent call() throws IOException {
                    FXMLLoader fxmlLoader = new FXMLLoader(CyBooks.class.getResource("login.fxml"));
                    return fxmlLoader.load();
                }
            };
            // When the task succeeds, update the UI on the JavaFX Application Thread
            loadLoginTask.setOnSucceeded(workerStateEvent -> {
                try {
                    Parent root = loadLoginTask.getValue();
                    Stage stage = new Stage(); // Create a new stage for the dashboard
                    Scene scene = new Scene(root);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setResizable(false);
                    stage.setTitle("CyBooks - Connexion");
                    stage.getIcons().add(new Image("file:src/main/assets/icon-no-text-white.png"));
                    stage.setScene(scene);
                    stage.show();
                    // Enable dragging functionality for the stage
                    root.setOnMousePressed(mouseEvent -> {
                        xOffset = mouseEvent.getSceneX();
                        yOffset = mouseEvent.getSceneY();
                    });
                    root.setOnMouseDragged(mouseEvent -> {
                        stage.setX(mouseEvent.getScreenX() - xOffset);
                        stage.setY(mouseEvent.getScreenY() - yOffset);
                    });
                    stage.show();

                    // Close the login stage
                    Stage loginStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    loginStage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // Start the task in a new thread
            new Thread(loadLoginTask).start();
        }

    }
}
