package org.example.cybooks;
import java.io.InputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import java.io.InputStream;
public class BookAPI {
    public static List<Book> searchBooks(String apiUrl) {
        List<Book> books = new ArrayList<>();

        try {
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
                    Element recordElementData = (Element) recordDataElement.getElementsByTagName("mxc:record").item(0);

                    String isbn = "";
                    String title = "";
                    String imageUrl = "";
                    String author = "";
                    String language = "";
                    String category = "";
                    Date publicationDate = null;

                    NodeList recordNodes = recordDataElement.getElementsByTagName("mxc:record");
                    for (int k = 0; k < recordNodes.getLength(); k++) {
                        Element recordElementData1 = (Element) recordNodes.item(k);
                        imageUrl = "https://catalogue.bnf.fr/couverture?&appName=NE&idArk="+recordElementData1.getAttribute("id")+"&couverture=1";
                    }
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
                                    // Parse the date string into a Date object
                                    publicationDate = formatter.parse(content.substring(0, 8));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    if(isbn!=""){
                        Book book = new Book(isbn, imageUrl, title, author, language, category, publicationDate);
                        books.add(book);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }



}
