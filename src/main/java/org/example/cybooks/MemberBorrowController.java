package org.example.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class MemberBorrowController implements Initializable {
    private Book currentBook;
    @FXML
    private VBox booksGrid;
    @FXML
    private ScrollPane booksScrollPane;
    private List<Book> list_borrowed_books;
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private List <Book> getBorrowedBooks(int id_user){
        List <Book> list_books = new ArrayList<>();
        list_books.add(new Book("978-2-84492-827-6", "https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb451421765&couverture=1","L'instant présent", "Guillaume Musso", "FR", "803", new Date(978307200L * 1000)));
        list_books.add(new Book("978-2-7096-6323-6", "https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb45722987n&couverture=1","Léonard de Vinci", "Serge Bramly", "FR", "920", new Date(978307200L * 1000)));
        list_books.add(new Book("979-10-929-2814-3", "https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb44436642c&couverture=1","Power, les 48 lois du pouvoir", "Robert Greene", "FR", "150", new Date(978307200L * 1000)));
        list_books.add(new Book("978-2-84592-459-8", "https://catalogue.bnf.fr/couverture?&appName=NE&idArk=ark:/12148/cb43566942v&couverture=1","Comment se faire des amis à l'ère numérique", "Dale Carnegie & associés", "FR", "150", new Date(978307200L * 1000)));
        return list_books;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        booksScrollPane.setFitToWidth(true);
        list_borrowed_books=new ArrayList<>(getBorrowedBooks(1));
        try{
            for (int i=0;i<list_borrowed_books.size();i++){
                FXMLLoader fxmlLoader=new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                AnchorPane bookPane=fxmlLoader.load();
                BookController cardController2 = fxmlLoader.getController();
                cardController2.setData(list_borrowed_books.get(i));
                booksGrid.getChildren().add(bookPane);
            }
        }catch (
                IOException e){
            e.printStackTrace();
        }
    }
}
