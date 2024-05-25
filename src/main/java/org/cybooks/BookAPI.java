package org.cybooks;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class BookAPI {
    public static List<Book> searchBooks(String s_isbn, String s_title, String s_author, int s_max_records) {
        List<Book> books = new ArrayList<>();
        String query_isbn="";
        String query_title="";
        String query_author="";
        try {
            if (s_isbn != null && !s_isbn.trim().isEmpty()) {
                query_isbn = "bib.isbn all \"" + s_isbn + "\" and ";
            }
            if (s_title != null && !s_title.trim().isEmpty()) {
                query_title = "bib.title all \"" + s_title + "\" and ";
            }
            if (s_author != null && !s_author.trim().isEmpty()) {
                query_author = "bib.author all \"" + s_author + "\" and ";
            }
            String query = query_isbn + query_title + query_author;
            if (query.endsWith(" and ")) {
                query = query.substring(0, query.length() - 5);
            }
            String encodedQuery = URLEncoder.encode(query+"not bib.doctype all \"g h\"", StandardCharsets.UTF_8);
            String apiUrl = "https://catalogue.bnf.fr/api/SRU?version=1.2&operation=searchRetrieve&query=" + encodedQuery + "&maximumRecords=" + s_max_records;
            System.out.println("API URL: " + apiUrl);
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HTTP error code: " + responseCode);
            }

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder xmlResponse = new StringBuilder();
            while (scanner.hasNextLine()) {
                xmlResponse.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xmlResponse.toString())));
            doc.getDocumentElement().normalize();

            NodeList recordList = doc.getElementsByTagName("srw:record");
            for (int i = 0; i < recordList.getLength(); i++) {
                Element recordElement = (Element) recordList.item(i);
                NodeList recordDataList = recordElement.getElementsByTagName("srw:recordData");

                for (int j = 0; j < recordDataList.getLength(); j++) {
                    Element recordDataElement = (Element) recordDataList.item(j);
                    Element recordElementData = null;
                    NodeList recordNodes = recordDataElement.getElementsByTagName("mxc:record");
                    if (recordNodes.getLength() > 0) {
                        recordElementData = (Element) recordNodes.item(0);
                    }

                    if (recordElementData != null) {
                        String isbn = "";
                        String title = "";
                        String imageUrl = "";
                        String author = "";
                        String language = "";
                        String category = "";
                        Date publicationDate = null;

                        imageUrl = "https://catalogue.bnf.fr/couverture?&appName=NE&idArk="+recordElementData.getAttribute("id")+"&couverture=1";

                        NodeList datafields = recordElementData.getElementsByTagName("mxc:datafield");
                        for (int k = 0; k < datafields.getLength(); k++) {
                            Element datafield = (Element) datafields.item(k);
                            String tag = datafield.getAttribute("tag");
                            NodeList subfields = datafield.getElementsByTagName("mxc:subfield");

                            for (int l = 0; l < subfields.getLength(); l++) {
                                Element subfield = (Element) subfields.item(l);
                                String code = subfield.getAttribute("code");
                                String content = subfield.getTextContent();
                                if (tag.equals("010") && code.equals("a")) {
                                    isbn = content;
                                    //imageUrl = "https://covers.openlibrary.org/b/isbn/"+isbn+"-L.jpg";
                                } else if (tag.equals("200") && code.equals("a")) {
                                    title = content;
                                } else if (tag.equals("200") && code.equals("f")) {
                                    author = content;
                                } else if (tag.equals("020") && code.equals("a")) {
                                    language = content;
                                } else if (tag.equals("686") && code.equals("a")) {
                                    category = content;
                                } else if (tag.equals("100") && code.equals("a")) {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                                    try {
                                        publicationDate = formatter.parse(content.substring(0, 8));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        Book book = new Book(isbn, imageUrl, title, author, language, category, publicationDate);
                        books.add(book);
                    }
                }
            }
            for (Book b : books) {
                System.out.println(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }
    public static Book searchBook(String s_isbn) {
        List<Book> books = searchBooks(s_isbn, "", "", 1);
        return books.getFirst();
    }
}
