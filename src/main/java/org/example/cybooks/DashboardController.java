package org.example.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @FXML
    private Pane paneUsers;
    @FXML
    private VBox membersVbox;
    private List<Member> list_members;
    @FXML
    private Text textSommeUtilisateurs;
    @FXML
    private ScrollPane usersScrollPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageTopBooks1.setFill(new ImagePattern(new Image("https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb45378014r&couverture=1")));
        imageTopBooks2.setFill(new ImagePattern(new Image("https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb46997632p&couverture=1")));
        imageTopBooks3.setFill(new ImagePattern(new Image("https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb47414381z&couverture=1")));
        imageTopBooks4.setFill(new ImagePattern(new Image("https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb473992503&couverture=1")));
        dashboardTitle.setText("Accueil");
        paneHome.setVisible(true);
        paneUsers.setVisible(false);

        usersScrollPane.setFitToWidth(true);

        list_members=new ArrayList<>(getMembersFromDatabase());
        try{
            for (int i=0;i<list_members.size();i++){
                FXMLLoader fxmlLoader=new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("member.fxml"));
                AnchorPane dashboardPane=fxmlLoader.load();
                MemberController cardController = fxmlLoader.getController();
                cardController.setData(list_members.get(i));
                membersVbox.getChildren().add(dashboardPane);
            }
            textSommeUtilisateurs.setText(Integer.toString(list_members.size()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private List <Member> getMembersFromDatabase(){
        List <Member> list_members = new ArrayList<>();
        list_members.add(new Member(122,"Kone Mohamed Lamine", "mohamed@gmail.com", new Date(1716163200), "Active", new Date(978307200), "+33766587466", "M"));
        list_members.add(new Member(123,"El-Mahdaoui Abdellatif", "elmahdaoui@gmail.com", new Date(1716163200), "Active", new Date(1014940800), "+33678447732", "M"));
        list_members.add(new Member(124,"Zhang Clement", "zhang@gmail.com", new Date(1716163200), "Active", new Date(978307200), "+33743334433", "M"));
        list_members.add(new Member(124,"Azmi Morkos", "azmi@gmail.com", new Date(1716163200), "Active", new Date(978307200), "+33712221255", "M"));
        list_members.add(new Member(125, "Alice Johnson", "alice.johnson@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678901", "F"));
        list_members.add(new Member(126, "Bob Smith", "bob.smith@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678902", "M"));
        list_members.add(new Member(127, "Charlie Brown", "charlie.brown@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678903", "M"));
        list_members.add(new Member(128, "David Wilson", "david.wilson@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678904", "M"));
        list_members.add(new Member(129, "Eva Green", "eva.green@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678905", "F"));
        list_members.add(new Member(130, "Fiona White", "fiona.white@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678906", "F"));
        list_members.add(new Member(131, "George Black", "george.black@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678907", "M"));
        list_members.add(new Member(132, "Hannah Scott", "hannah.scott@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678908", "F"));
        list_members.add(new Member(133, "Ian Brown", "ian.brown@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678909", "M"));
        list_members.add(new Member(134, "Jessica Adams", "jessica.adams@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678910", "F"));
        list_members.add(new Member(135, "Kevin Lewis", "kevin.lewis@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678911", "M"));
        list_members.add(new Member(136, "Laura Clark", "laura.clark@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678912", "F"));
        return list_members;
    }
    public void closeWindow(ActionEvent event) {
        // Get the reference to the stage
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();
    }
    public void actionHome(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Accueil");
        paneHome.setVisible(true);
        paneUsers.setVisible(false);
    }
    public void actionUsers(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Utilisateurs");
        paneUsers.setVisible(true);
        paneHome.setVisible(false);
    }
    public void actionBooks(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Livres");
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
    }
    public void actionSettings(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Paramètres");
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
    }
    public void actionProfile(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Mon profil");
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
    }
    public void actionLogout(ActionEvent event) {
        System.out.println("Logout!!!");
    }
}
