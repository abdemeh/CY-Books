package org.example.cybooks;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Alert.AlertType;

import java.util.Optional;
public class MemberController {
    @FXML
    private Text member_name;
    @FXML
    private Text member_email;
    @FXML
    private Text member_added;
    @FXML
    private Text member_state;
    @FXML
    private Text member_id;
    @FXML
    private Text member_phone;
    @FXML
    private Text member_image;
    @FXML
    private TextFlow member_image_bg;

    private Member currentMember;
    private double xOffset = 0;
    private double yOffset = 0;
    private Book currentBook;
    @FXML
    private VBox booksGrid;
    @FXML
    private ScrollPane booksScrollPane;
    private List<Book> list_borrowed_books;
    @FXML
    private void deleteMember() throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText("Etes-vous sûr que vous voulez supprimer "+currentMember.getLastName()+" "+currentMember.getFirstName()+"?");

        /* Get the button types
        ButtonType okButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Set the button types with custom text
        alert.getButtonTypes().setAll(okButton, cancelButton);*/

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            MemberDAO.deleteMember(currentMember.getId());
            // Notify DashboardController to update members
            DashboardController dashboardController = ControllerManager.getDashboardController();
            if (dashboardController != null) {
                dashboardController.updateMembersFromDatabase();
            }
        }
    }
    @FXML
    private void openMemberEdit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("member_edit.fxml"));
        Parent root = loader.load();

        MemberEditController memberEditController = loader.getController();
        memberEditController.setMemberData(currentMember);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:assets/icon-no-text-white.png"));
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
    @FXML
    private void openMemberBorrow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("member_borrow.fxml"));

        Parent root = loader.load();

        MemberBorrowController memberBorrowController = loader.getController();
        memberBorrowController.setMemberData(currentMember);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:assets/icon-no-text-white.png"));
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
    public void setData(Member member){
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        currentMember = member;

        member_name.setText(member.getLastName()+" "+member.getFirstName());
        member_id.setText(Integer.toString(member.getId()));
        member_email.setText(member.getEmail());
        member_added.setText(df.format(member.getInscriptionDate()));
        member_phone.setText(member.getPhone());
        member_state.setText(member.getState());
        if ("Actif".equals(member.getState())) {
            member_state.setStyle("-fx-fill: #6fcd7f;");
        } else if ("Bloqué".equals(member.getState()) || "Suspendu".equals(member.getState())) {
            member_state.setStyle("-fx-fill: #cc7070;");
        } else if ("Expiré".equals(member.getState()) || "Inactif".equals(member.getState())) {
            member_state.setStyle("-fx-fill: #ccc670;");
        }
        member_image.setText(getInitials(member.getLastName(), member.getFirstName()));
    }
    public static String getInitials(String lastname, String firstname) {
        String initials = "";
        initials += Character.toUpperCase(lastname.charAt(0));
        initials += Character.toUpperCase(firstname.charAt(0));
        return initials.toUpperCase();
    }
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
