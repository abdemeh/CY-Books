package org.example.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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
    private Text dashboardTitle;
    @FXML
    private Pane paneHome;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageTopBooks1.setFill(new ImagePattern(new Image("https://m.media-amazon.com/images/I/61wQHWtxCOL._SL1200_.jpg")));
        imageTopBooks2.setFill(new ImagePattern(new Image("https://m.media-amazon.com/images/I/61zCGGM++LL._SL1500_.jpg")));
        imageTopBooks3.setFill(new ImagePattern(new Image("https://m.media-amazon.com/images/I/61fVpDvDTGL._SL1400_.jpg")));
        imageTopBooks4.setFill(new ImagePattern(new Image("https://m.media-amazon.com/images/I/91tYV+R03kL._SL1500_.jpg")));
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
    public void actionLogout(ActionEvent event) {
        System.out.println("Logout!!!");
    }
}
