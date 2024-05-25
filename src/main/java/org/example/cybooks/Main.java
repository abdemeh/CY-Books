package org.example.cybooks;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            // Construct the query string
            //String query = "bib.title+all+\"comment se faire des amis\"+and+bib.author+all+\"Dale Carnegie\"+not+bib.doctype+all+\"g+h\"";
            //String query = "bib.title+all+\"Père riche, père pauvre\"+not+bib.doctype+all+\"g+h\"";
            //String query = "bib.isbn+all+\"978-2-84592-459-8\"+not+bib.doctype+all+\"g+h\"";
            //String encodedQuery = URLEncoder.encode(query, "UTF-8");
            // Construct the API URL with the query string
            //String apiUrl = "https://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query=" + encodedQuery + "&maximumRecords=25";

            // Call the searchBooks method with the encoded query string
            //List<Book> books = BookAPI.searchBooks("978-2-253-25777-6","","",50);
            Book b = BookAPI.searchBook("978-2-253-25777-6");
            System.out.println(b.getimageUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try (Connection connection = Database.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1");
            if (resultSet.next()) {
                System.out.println("Database connection successful.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}