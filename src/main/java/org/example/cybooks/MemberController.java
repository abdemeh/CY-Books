package org.example.cybooks;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private Text member_image;
    @FXML
    private TextFlow member_image_bg;

    private Member currentMember;
    private String[] colors={"#6fcdb1","#a06fcd","#cd6f88","#6f8bcd","#919191","#cdc86f"};
    private double xOffset = 0;
    private double yOffset = 0;
    private Book currentBook;
    @FXML
    private VBox booksGrid;
    @FXML
    private ScrollPane booksScrollPane;
    private List<Book> list_borrowed_books;
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

        // MemberEditController memberEditController = loader.getController();
        // memberEditController.setMemberData(currentMember);

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
        member_state.setText(member.getState());
        member_image.setText(getInitials(member.getLastName(), member.getFirstName()));
        //member_image.setStyle("-fx-text-fill: "+ colors[(int)(Math.random()*colors.length)]);
        //member_image.setFill(Color.web(colors[(int)(Math.random()*colors.length)]));
        member_image_bg.setStyle("-fx-background-color: "+ colors[(int)(Math.random()*colors.length)]+";-fx-background-radius: 10px");
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
