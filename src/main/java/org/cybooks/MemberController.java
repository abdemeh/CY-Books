package org.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javafx.scene.control.Alert.AlertType;

import java.util.Optional;

/**
 * Controller class for managing member details.
 */
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
    @FXML
    private Button btnDeleteUser;
    private Member currentMember;
    private double xOffset = 0;
    private double yOffset = 0;
    private Book currentBook;
    @FXML
    private VBox booksGrid;
    @FXML
    private ScrollPane booksScrollPane;
    private List<Book> list_borrowed_books;

    /**
     * Deletes the current member from the system.
     *
     * @throws IOException If an error occurs during member deletion.
     */
    @FXML
    private void deleteMember() throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        Image icon = new Image("file:src/main/assets/icon-no-text-white.png");
        Stage stage_alert = (Stage) alert.getDialogPane().getScene().getWindow();
        stage_alert.getIcons().add(icon);
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr que vous voulez supprimer " + currentMember.getLastName() + " " + currentMember.getFirstName() + "?");

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        // Handle close request
        stage_alert.setOnCloseRequest(windowEvent -> {
            // Choose default option if user closes the dialog
            ButtonType result = alert.getResult();
            if (result == null) {
                alert.setResult(buttonTypeNon);
            }
        });

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeOui) {
            MemberDAO.deleteMember(currentMember.getId());
            DashboardController dashboardController = ControllerManager.getDashboardController();
            if (dashboardController != null) {
                dashboardController.updateMembersFromDatabase();
            }
        }
    }


    /**
     * Opens the member editing window.
     *
     * @throws IOException If an error occurs while opening the member edit window.
     */
    @FXML
    private void openMemberEdit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("member_edit.fxml"));
        Parent root = loader.load();

        MemberEditController memberEditController = loader.getController();
        memberEditController.setMemberData(currentMember);

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
     * Opens the member borrowing window.
     *
     * @throws IOException If an error occurs while opening the member borrow window.
     */
    @FXML
    private void openMemberBorrow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("member_borrow.fxml"));
        Parent root = loader.load();

        MemberBorrowController memberBorrowController = loader.getController();
        memberBorrowController.setMemberData(currentMember);

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
     * Sets the data for the current member.
     *
     * @param member The member whose data is to be set.
     */
    public void setData(Member member) {
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        currentMember = member;

        member_name.setText(member.getLastName() + " " + member.getFirstName());
        member_id.setText(Integer.toString(member.getId()));
        member_email.setText(member.getEmail());
        member_added.setText(df.format(member.getInscriptionDate()));
        member_phone.setText(member.getPhone());
        member_state.setText(member.getState());
        if (member.getState().equals("Actif")) {
            member_state.setFill(Color.web("#6fcd7f"));
        } else if (member.getState().equals("Bloqué") || member.getState().equals("Suspendu")) {
            member_state.setFill(Color.web("#cc7070"));
        } else if (member.getState().equals("Inactif")) {
            member_state.setFill(Color.web("#ccc670"));
        }
        if(LoanDAO.hasLoan(member.getId())){
            btnDeleteUser.setDisable(true);
        }
        member_image.setText(getInitials(member.getLastName(), member.getFirstName()));
    }

    /**
     * Generates initials based on the first and last name.
     *
     * @param lastname  The last name of the member.
     * @param firstname The first name of the member.
     * @return The initials of the member.
     */
    public static String getInitials(String lastname, String firstname) {
        String initials = "";
        initials += Character.toUpperCase(lastname.charAt(0));
        initials += Character.toUpperCase(firstname.charAt(0));
        return initials.toUpperCase();
    }

    /**
     * Closes the current window.
     *
     * @param event The ActionEvent triggered by the close button.
     */
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
