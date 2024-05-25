package org.cybooks;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for managing the book catalogue interface.
 */
public class BookCatalogueController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;

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
    private VBox booksVbox;

    /**
     * Closes the current window and opens the login window.
     *
     * @param event the ActionEvent triggering the action
     */
    public void closeWindow(ActionEvent event) {
        Task<Parent> loadLogin = new Task<>() {
            @Override
            protected Parent call() throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(CyBooks.class.getResource("login.fxml"));
                return fxmlLoader.load();
            }
        };

        // When the task succeeds, update the UI on the JavaFX Application Thread
        loadLogin.setOnSucceeded(workerStateEvent -> {
            try {
                Parent root = loadLogin.getValue();
                Scene scene = new Scene(root);
                Stage stage = new Stage(); // Create a new stage for the catalogue
                stage.setResizable(false);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("CyBooks - Catalogue");
                stage.getIcons().add(new Image("file:src/main/assets/icon-no-text-white.png"));
                stage.setScene(scene);

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
                Stage catalogueStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                catalogueStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start the task in a new thread
        new Thread(loadLogin).start();
    }

    /**
     * Initializes the controller.
     *
     * @param url            the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Performs a search for books based on the input criteria.
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
     * Updates the display with the search results.
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
}
