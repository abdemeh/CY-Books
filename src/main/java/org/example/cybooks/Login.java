package org.example.cybooks;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Login extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setTitle("CyBooks - Login");
        stage.getIcons().add(new Image("file:assets/icon-no-text-white.png"));
        stage.setScene(scene);
        stage.show();

        // Allowing the window to be dragged
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public static void main(String[] args) {
        launch(args); // Passing arguments to the launch method
    }

    // Method to close the window
    public void closeWindow(ActionEvent event) {
        // Get the reference to the stage
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }

}
