
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Member {
    private int idMember;
    private String firstName;
    private String lastName;
    private String address;
    private int phoneNumber;
    private String email;

    public Member(int idMember,String firstName,String lastName,String address,int phoneNumber,String email) {
        this.idMember=idMember;
        this.firstName=firstName;
        this.lastName=lastName;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.email=email;
    }

    // Getters et setters pour les attributs idMember, firstName, lastName, address, phoneNumber et email
    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void insertIntoDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db?useSSL=false", "root", "");
            String query = "INSERT INTO member (id, firstName, lastName, address, phoneNumber, email) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idMember);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, address);
            statement.setInt(5, phoneNumber);
            statement.setString(6, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Nouvel utilisateur inséré avec succès !");
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de l'utilisateur : " + e.getMessage());
        }
    }

    public static void displayAllMembers() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db?useSSL=false", "root", "");
            String query = "SELECT * FROM member";
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                int phoneNumber = resultSet.getInt("phoneNumber");
                String email = resultSet.getString("email");

                System.out.println("ID: " + id);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Address: " + address);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println("Email: " + email);
                System.out.println("------------------------");
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }


    }

    public static void displayMemberById(int memberId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db?useSSL=false", "root", "");
            String query = "SELECT * FROM member WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, memberId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String phoneNumber = resultSet.getString("phoneNumber");
                String email = resultSet.getString("email");

                System.out.println("ID: " + id);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Address: " + address);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println("Email: " + email);
            } else {
                System.out.println("Aucun utilisateur avec l'ID " + memberId + " trouvé.");
            }

            connection.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
        }
    }

   
    }


