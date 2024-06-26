package org.cybooks;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the login view.
 */
public class LoginController implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Text textLoginMessage;
    @FXML
    private TextField textLoginEmail;
    @FXML
    private TextField textLoginPassword;

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textLoginMessage.setText("");
    }

    /**
     * Handles the login button action event.
     *
     * @param event The action event triggered by clicking the login button.
     */
    public void login(ActionEvent event) {
        if(textLoginEmail.getText().trim().equals("")){
            textLoginMessage.setFill(Color.web("#cc7070"));
            textLoginMessage.setText("Veuillez entrer un email!");
        }else{
            Admin admin = AdminDAO.getAdmin(textLoginEmail.getText(), textLoginPassword.getText());
            if (admin != null) {
                // Your login logic goes here
                textLoginMessage.setFill(Color.web("#434343"));
                textLoginMessage.setText("Connexion...");
                AdminContext.setCurrentAdmin(admin);
                // Create a new task for loading the dashboard FXML
                Task<Parent> loadDashboardTask = new Task<>() {
                    @Override
                    protected Parent call() throws IOException {
                        FXMLLoader fxmlLoader = new FXMLLoader(CyBooks.class.getResource("dashboard.fxml"));
                        return fxmlLoader.load();
                    }
                };
                // When the task succeeds, update the UI on the JavaFX Application Thread
                loadDashboardTask.setOnSucceeded(workerStateEvent -> {
                    try {
                        Parent root = loadDashboardTask.getValue();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage(); // Create a new stage for the dashboard
                        stage.setResizable(false);
                        stage.initStyle(StageStyle.UNDECORATED);
                        stage.setTitle("CyBooks - Dashboard");
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
                        Stage loginStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        loginStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                // Start the task in a new thread
                new Thread(loadDashboardTask).start();
            } else {
                textLoginMessage.setFill(Color.web("#cc7070"));
                textLoginMessage.setText("Email ou mot de passe incorrect!");
            }
        }
    }

    /**
     * Handles the action event for opening the catalogue.
     *
     * @param event The action event triggered by clicking the catalogue link.
     */
    public void openCatalogue(ActionEvent event) {
        System.out.println("Open Catalogue method called");
        System.out.println("Loading book_catalogue.fxml from: " + CyBooks.class.getResource("book_catalogue.fxml")); // Add this line

        // Create a task for loading the catalogue FXML
        Task<Parent> loadCatalogueTask = new Task<>() {
            @Override
            protected Parent call() throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(CyBooks.class.getResource("book_catalogue.fxml"));
                return fxmlLoader.load();
            }
        };

        // When the task succeeds, update the UI on the JavaFX Application Thread
        loadCatalogueTask.setOnSucceeded(workerStateEvent -> {
            try {
                Parent root = loadCatalogueTask.getValue();
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
                Stage loginStage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
                loginStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Start the task in a new thread
        new Thread(loadCatalogueTask).start();
    }

    /**
     * Closes the current window.
     *
     * @param event The action event triggered by clicking the close button.
     */
    public void closeWindow(ActionEvent event) {
        // Get the reference to the stage
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }
}
