package org.example.cybooks;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            // Construct the query string
            String query = "bib.title+all+\"comment se faire des amis\"+and+bib.author+all+\"Dale Carnegie\"+not+bib.doctype+all+\"g+h\"";
            //String query = "bib.title+all+\"Père riche, père pauvre\"+not+bib.doctype+all+\"g+h\"";
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            // Construct the API URL with the query string
            String apiUrl = "https://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query=" + encodedQuery + "&maximumRecords=25";

            // Call the searchBooks method with the encoded query string
            List<Book> books = BookAPI.searchBooks(apiUrl);

            // Print out the information of each book
            for (Book book : books) {
                System.out.println(book);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}