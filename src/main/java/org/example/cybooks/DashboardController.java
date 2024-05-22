package org.example.cybooks;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    Button btnSearchBook;
    @FXML
    Text textSearchBookResultats;
    @FXML
    TextField textSearchBookMaxRes;
    @FXML
    TextField textSearchBookISBN;
    @FXML
    TextField textSearchBookTitre;
    @FXML
    TextField textSearchBookAuteur;
    @FXML
    Text textSearchBookMessage;

    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageTopBooks1.setFill(new ImagePattern(new Image("https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb45378014r&couverture=1")));
        imageTopBooks2.setFill(new ImagePattern(new Image("https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb46997632p&couverture=1")));
        imageTopBooks3.setFill(new ImagePattern(new Image("https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb47414381z&couverture=1")));
        imageTopBooks4.setFill(new ImagePattern(new Image("https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb473992503&couverture=1")));
        dashboardTitle.setText("Accueil");
        paneHome.setVisible(true);
        paneUsers.setVisible(false);
        paneBooks.setVisible(false);

        textSearchBookMessage.setText("");

        usersScrollPane.setFitToWidth(true);
        booksScrollPane.setFitToWidth(true);

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
        list_books=new ArrayList<>(BookAPI.searchBooks("","","",25));
        updateBooks(list_books);
    }
    private void updateBooks(List<Book>list_books){
        Platform.runLater(() -> {
            try{
                booksVbox.getChildren().clear();
                for (int i=0;i<list_books.size();i++){
                    FXMLLoader fxmlLoader=new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                    AnchorPane dashboardPane=fxmlLoader.load();
                    BookController bookController = fxmlLoader.getController();
                    bookController.setData(list_books.get(i));
                    booksVbox.getChildren().add(dashboardPane);
                }
                textSearchBookResultats.setText("Résultats: "+Integer.toString(list_books.size()));
                if(list_books.isEmpty()){
                    textSearchBookMessage.setText("Aucune résultat est trouvé.");
                }else{
                    textSearchBookMessage.setText("");
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
    public void searchBook(){
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
    private List <Member> getMembersFromDatabase(){
        List <Member> list_members = new ArrayList<>();
        list_members.add(new Member(122, "Kone", "Mohamed Lamine", "mohamed@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+33766587466", "M"));
        list_members.add(new Member(123, "El Mahdaoui", "Abdellatif", "elmahdaoui@gmail.com", new Date(1716163200L * 1000), "Active", new Date(1014940800L * 1000), "+33678447732", "M"));
        list_members.add(new Member(124, "Zhang", "Clement", "zhang@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+33743334433", "M"));
        list_members.add(new Member(124, "Azmi", "Morkos", "azmi@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+33712221255", "M"));
        list_members.add(new Member(125, "Johnson", "Alice", "alice.johnson@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678901", "F"));
        list_members.add(new Member(126, "Smith", "Bob", "bob.smith@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678902", "M"));
        list_members.add(new Member(127, "Brown", "Charlie", "charlie.brown@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678903", "M"));
        list_members.add(new Member(128, "Wilson", "David", "david.wilson@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678904", "M"));
        list_members.add(new Member(129, "Green", "Eva", "eva.green@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678905", "F"));
        list_members.add(new Member(130, "White", "Fiona", "fiona.white@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678906", "F"));
        list_members.add(new Member(131, "Black", "George", "george.black@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678907", "M"));
        list_members.add(new Member(132, "Scott", "Hannah", "hannah.scott@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678908", "F"));
        list_members.add(new Member(133, "Brown", "Ian", "ian.brown@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678909", "M"));
        list_members.add(new Member(134, "Adams", "Jessica", "jessica.adams@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678910", "F"));
        list_members.add(new Member(135, "Lewis", "Kevin", "kevin.lewis@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678911", "M"));
        list_members.add(new Member(136, "Clark", "Laura", "laura.clark@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678912", "F"));list_members.add(new Member(136, "Clark", "Laura", "laura.clark@gmail.com", new Date(1716163200L * 1000), "Active", new Date(978307200L * 1000), "+12345678912", "F"));
        return list_members;
    }
    @FXML
    private void openMemberAdd() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("member_add.fxml"));
        Parent root = loader.load();



        //MemberEditController memberEditController = loader.getController();
        //memberEditController.setMemberData(currentMember);

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
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void actionHome(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Accueil");
        paneHome.setVisible(true);
        paneUsers.setVisible(false);
        paneBooks.setVisible(false);
    }
    public void actionUsers(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Utilisateurs");
        paneUsers.setVisible(true);
        paneHome.setVisible(false);
        paneBooks.setVisible(false);
    }
    public void actionBooks(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Livres");
        paneBooks.setVisible(true);
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
    }
    public void actionSettings(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Paramètres");
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
        paneBooks.setVisible(false);
    }
    public void actionProfile(ActionEvent event) {
        // Get the reference to the stage
        dashboardTitle.setText("Mon profil");
        paneHome.setVisible(false);
        paneUsers.setVisible(false);
        paneBooks.setVisible(false);
    }
    public void actionLogout(ActionEvent event) {
        System.out.println("Logout!!!");
    }
}
