package org.example.cybooks;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.text.Text;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.scene.image.ImageView;

public class Dashboard extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Text dashboardTitle;
    @FXML
    private Pane paneHome;
    @FXML
    private Rectangle imageTopBooks1;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CyBooks.class.getResource("dashboard.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("CyBooks - Tableau de bord");
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
        launch();
    }
    public void closeWindow(ActionEvent event) {
        // Get the reference to the stage
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }
    public void actionHome(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Tableau de bord");
        paneHome.setVisible(true);
        Image img=new Image("https://images.epagine.fr/551/9782892259551_1_75.jpg");
        imageTopBooks1.setFill(new ImagePattern(img));
    }
    public void actionUsers(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Utilisateurs");
        paneHome.setVisible(false);
    }
    public void actionBooks(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Livres");
        paneHome.setVisible(false);
    }
    public void actionSettings(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Paramètres");
        paneHome.setVisible(false);
    }
    public void actionProfile(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Mon profil");
        paneHome.setVisible(false);
    }
}