package org.cybooks;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import static java.util.Date.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            returnToMainMenu(scanner);
        }
    }

    public static void returnToMainMenu(Scanner scanner) throws SQLException {
        boolean authenticated = false;

        while (!authenticated) {
            System.out.println("*****************************************");
            System.out.println("*     Bienvenue dans l'application      *");
            System.out.println("*                CyBooks                *");
            System.out.println("*****************************************");
            System.out.println();
            System.out.println("Veuillez choisir votre mode de connexion :");
            System.out.println("1. Administrateur");
            System.out.println("2. Utilisateur");
            System.out.println("0. Quitter");
            System.out.print("Tapez 1 pour Administrateur, 2 pour Utilisateur, ou 0 pour Quitter : ");

            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne laissée par nextInt()

            if (choix == 0) {
                System.out.println("Merci d'avoir utilisé CyBooks. À bientôt !");
                System.exit(0);
            } else if (choix == 2) {
                continue; // Relancer le programme
            }

            System.out.print("Entrez votre email : ");
            String email = scanner.nextLine();

            System.out.print("Entrez votre mot de passe : ");
            String password = scanner.nextLine();

            switch (choix) {
                case 1:
                    authenticated = authenticate(email, password, "admin");
                    if (authenticated) {
                        System.out.println("Connexion réussie en tant qu'Administrateur.");
                        adminMenu(scanner);
                    } else {
                        System.out.println("Échec de la connexion. Veuillez vérifier vos informations.");
                    }
                    break;
                case 2:
                    authenticated = authenticate(email, password, "user") ||
                            authenticate(email, password, "admin");
                    if (authenticated) {
                        System.out.println("Connexion réussie en tant qu'Utilisateur.");
                        userMenu(scanner);
                    } else {
                        System.out.println("Échec de la connexion. Veuillez vérifier vos informations.");
                    }
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer 1, 2 ou 0.");
                    break;
            }
        }
    }

    public static boolean authenticate(String email, String password, String user_type) {
        boolean isAuthenticated = false;
        try {
            Connection connection = Database.getConnection();
            String query = "SELECT * FROM users WHERE email = ? AND password = ? AND user_type = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, user_type);
            ResultSet resultSet = statement.executeQuery();
            isAuthenticated = resultSet.next();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
        }
        return isAuthenticated;
    }

    public static void userMenu(Scanner scanner) {
        boolean backToMainMenu = false;
        while (!backToMainMenu) {
            System.out.println("Bienvenue dans le menu utilisateur.");
            System.out.println("1. Voir notre catalogue");
            System.out.println("0. Retour au menu principal");
            System.out.print("Tapez 1 pour voir le catalogue ou 0 pour revenir au menu principal : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne laissée par nextInt()

            switch (choix) {
                case 1:
                    System.out.print("Entrez l'ISBN du livre (ou 0 si inconnu) : ");
                    String isbn = scanner.nextLine();
                    if (isbn.equals("0")) isbn = "";

                    System.out.print("Entrez le titre du livre (ou 0 si inconnu) : ");
                    String title = scanner.nextLine();
                    if (title.equals("0")) title = "";

                    System.out.print("Entrez l'auteur du livre (ou 0 si inconnu) : ");
                    String author = scanner.nextLine();
                    if (author.equals("0")) author = "";

                    System.out.print("Entrez le nombre de résultats souhaités : ");
                    int maxRecords = scanner.nextInt();
                    scanner.nextLine(); // Consomme la nouvelle ligne laissée par nextInt()

                    List<Book> books = BookAPI.searchBooks(isbn, title, author, maxRecords);
                    for (Book book : books) {
                        System.out.println(book);
                    }
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }
        }
    }

    public static void adminMenu(Scanner scanner) throws SQLException {
        boolean backToMainMenu = false;
        while (!backToMainMenu) {
            System.out.println("Bienvenue dans le menu administrateur.");
            System.out.println("1. Gérer les utilisateurs");
            System.out.println("2. Gérer les emprunts");
            System.out.println("0. Retour au menu principal");
            System.out.print("Tapez 1 pour gérer les utilisateurs, 2 pour gérer les emprunts ou 0 pour revenir au menu principal : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne laissée par nextInt()

            switch (choix) {
                case 1:
                    manageUsers(scanner);
                    break;
                case 2:
                    manageLoans(scanner);
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println("Choix invalide.");
                    break;
            }
        }
    }

    public static void manageUsers(Scanner scanner) throws SQLException {
        boolean backToAdminMenu = false;
        while (!backToAdminMenu) {
            System.out.println("Gestion des utilisateurs :");
            System.out.println("1. Voir notre catalogue");
            System.out.println("2. Ajouter un nouveau membre");
            System.out.println("3. Mettre à jour les infos d'un membre");
            System.out.println("4. Supprimer un utilisateur");
            System.out.println("5. Afficher tous les utilisateurs");
            System.out.println("6. Afficher un utilisateur en particulier");
            System.out.println("0. Retour au menu administrateur");
            System.out.print("Tapez votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne laissée par nextInt()
            // Établissez la connexion à votre base de données
            Connection connection = Database.getConnection();

            switch (choix) {
                case 1: //voir le catalogue
                    userMenu(scanner); // Réutiliser le menu utilisateur pour voir le catalogue
                    break;
                case 2: // ajouter un nouveau membre
                    addNewMember(scanner); //ajouter nouveau membre
                    break;
                case 3: // mettre à jour un membre
                    try {
                        updateMember(connection, scanner); // mettre a jour un membre
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case 4: //supprimer un membre
                    try {
                        deleteMember(connection, scanner); // mettre a jour un membre
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case 5: //afficher tous les membres
                    List<Member> members = MemberDAO.getAllMembers();
                    for (Member member : members) {
                        member.toString();
                    }
                    break;

                case 6: // afficher un utilisateur en particulier
                    getMemberById(scanner);
                    break;
                case 0:
                    backToAdminMenu = true;
                    break;

                default:
                    System.out.println("Choix invalide.");
                    break;
            }
        }
    }

    public static void manageLoans(Scanner scanner) throws SQLException {
        boolean backToAdminMenu = false;
        while (!backToAdminMenu) {
            System.out.println("Gestion des emprunts :");
            System.out.println("1. Afficher tous les emprunts");
            System.out.println("2. Afficher les emprunts d'un utilisateur");
            System.out.println("3. Supprimer un emprunt");
            System.out.println("4. Ajouter un emprunt");
            System.out.println("5. Afficher les livres les plus empruntés");
            System.out.println("0. Retour au menu administrateur");
            System.out.print("Tapez votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne laissée par nextInt()

            // Établissez la connexion à votre base de données
            try (Connection connection = Database.getConnection()){
                switch (choix) {
                    case 1:
                        List<Loan> loans = LoanDAO.getAllLoans();
                        for (Loan l : loans) {
                            l.toString();
                        }

                        break;
                    case 2:
                        getUserLoans(scanner);
                        break;
                    case 3:
                        deleteLoan(scanner);
                        break;
                    case 4:
                        addLoan(scanner);
                        break;
                    case 5:
                        LoanDAO.getTopFourBooks();
                        break;
                    case 0:
                        backToAdminMenu = true;
                        break;
                    default:
                        System.out.println("Choix invalide, veuillez réessayer.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void addNewMember(Scanner scanner) {
        System.out.print("Entrez le prénom : ");
        String firstname = scanner.nextLine();

        System.out.print("Entrez le nom de famille : ");
        String lastname = scanner.nextLine();

        System.out.print("Entrez l'email : ");
        String email = scanner.nextLine();

        Date inscriptionDate = Date.valueOf(java.time.LocalDate.now());
        String state = "actif";

        System.out.print("Entrez la date de naissance (yyyy-MM-dd) : ");
        String birthday = scanner.nextLine();
        Date birthdayStr = null;
        try {
            birthdayStr = java.sql.Date.valueOf(birthday);
        } catch (Exception e) {
            System.err.println("Format de date invalide.");
            return;
        }

        System.out.print("Entrez le numéro de téléphone : ");
        String phone = scanner.nextLine();

        System.out.print("Entrez le sexe (M/F) : ");
        String sex = scanner.nextLine();

        Member newMember = new Member(0, lastname, firstname, email, (java.sql.Date) inscriptionDate, state, (java.sql.Date) birthdayStr, phone, sex, java.sql.Date.valueOf("2024-01-01"));
        MemberDAO.addMember(newMember);


    }

    public static void updateMember(Connection connection, Scanner scanner) throws SQLException, ParseException {
        System.out.print("Entrez l'email du membre à mettre à jour : ");
        String email = scanner.nextLine();

        // Rechercher le membre correspondant
        String selectQuery = "SELECT * FROM users WHERE email = ?";
        Member member = null;

        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, email);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id_user");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String memberEmail = resultSet.getString("email");
                Date inscriptionDate = resultSet.getDate("inscriptionDate");
                String state = resultSet.getString("state");
                Date birthday = resultSet.getDate("birthday");
                String phone = resultSet.getString("phone");
                String sex = resultSet.getString("sex");
                Date block_till = resultSet.getDate("block_till");

                member = new Member(id, lastName, firstName, memberEmail, (java.sql.Date) inscriptionDate, state, (java.sql.Date) birthday, phone, sex, block_till);
            } else {
                System.out.println("Aucun membre trouvé avec cet email.");
                return;
            }
        }

        // Demander les nouvelles valeurs
        System.out.print("Entrez le nouveau nom de famille (ou tapez 0 pour ne pas modifier) : ");
        String lastName = scanner.nextLine();
        if (!lastName.equals("0")) {
            member.setLastName(lastName);
        }

        System.out.print("Entrez le nouveau prénom (ou tapez 0 pour ne pas modifier) : ");
        String firstName = scanner.nextLine();
        if (!firstName.equals("0")) {
            member.setFirstName(firstName);
        }

        System.out.print("Entrez le nouvel email (ou tapez 0 pour ne pas modifier) : ");
        String newEmail = scanner.nextLine();
        if (!newEmail.equals("0")) {
            member.setEmail(newEmail);
        }

        System.out.print("Entrez la nouvelle date d'inscription (yyyy-MM-dd) (ou tapez 0 pour ne pas modifier) : ");
        String inscriptionStr = scanner.nextLine();
        if (!inscriptionStr.equals("0")) {
            java.sql.Date inscriptionDate = java.sql.Date.valueOf(inscriptionStr);
            member.setInscriptionDate(inscriptionDate);
        }


        System.out.print("Entrez le nouvel état (ou tapez 0 pour ne pas modifier) : ");
        String state = scanner.nextLine();
        if (!state.equals("0")) {
            member.setState(state);
        }

        System.out.print("Entrez la nouvelle date de naissance (yyyy-mm-dd) (ou tapez 0 pour ne pas modifier) : ");
        String birthdayStr = scanner.nextLine();
        if (!birthdayStr.equals("0")) {
            java.sql.Date birthday = java.sql.Date.valueOf(birthdayStr);
            member.setInscriptionDate(birthday);

            member.setBirthday(birthday);
        }
        System.out.print("Entrez le nouveau numéro de téléphone (ou tapez 0 pour ne pas modifier) : ");
        String phone = scanner.nextLine();
        if (!phone.equals("0")) {
            member.setPhone(phone);
        }

        System.out.print("Entrez le nouveau sexe (ou tapez 0 pour ne pas modifier) : ");
        String sex = scanner.nextLine();
        if (!sex.equals("0")) {
            member.setSex(sex);
        }

        // Mettre à jour le membre dans la base de données en utilisant MemberD
        MemberDAO.updateMember(member);
        System.out.println("Membre mis à jour avec succès !");

    }

    public static void deleteMember(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Entrez l'email du membre à supprimer : ");
        String email = scanner.nextLine();

        int memberId = -1; // Initialisation à une valeur qui indique qu'aucun membre correspondant n'a été trouvé

        String query = "SELECT id_user FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                memberId = resultSet.getInt("id_uer");
            } else {
                System.out.println("Aucun membre trouvé avec l'email spécifié.");
            }

        }
        MemberDAO.deleteMember(memberId);
    }

    public static void getMemberById(Scanner scanner) {
        System.out.print("Veuillez entrer votre email: ");
        String email = scanner.nextLine();

        String query = "SELECT id_user FROM users WHERE email = ?";

        // Établir la connexion à la base de données
        int id = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id_user");
                System.out.println("Votre ID utilisateur est : " + id);
                System.out.println(MemberDAO.getMemberById(id).toString());

            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getUserLoans(Scanner scanner) {
        System.out.print("Veuillez entrer votre email: ");
        String email = scanner.nextLine();

        String query = "SELECT id_user FROM users WHERE email = ?";

        // Établir la connexion à la base de données
        int id = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id_user");
                System.out.println("Votre ID utilisateur est : " + id);
                System.out.println(LoanDAO.getLoans(id).toString());

            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void deleteLoan(Scanner scanner) {
        System.out.print("Veuillez entrer l'id de l'emprunt à supprimer: ");
        int id_loan = Integer.parseInt(scanner.nextLine());
        LoanDAO.deleteLoan(id_loan);
    }

    public static void addLoan(Scanner scanner) throws SQLException {
        // Demander l'email de l'utilisateur
        System.out.print("Veuillez entrer votre email : ");
        String email = scanner.nextLine();

        // Demander les informations sur le livre
        System.out.println("Entrez les informations sur le livre (appuyez sur 0 si inconnu) :");
        System.out.print("ISBN : ");
        String isbn = scanner.nextLine();
        System.out.print("Titre : ");
        String title = scanner.nextLine();
        System.out.print("Auteur : ");
        String author = scanner.nextLine();
        System.out.print("Nombre de résultats à afficher : ");
        int maxRecords = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne laissée par nextInt()

        // Rechercher les livres correspondants aux critères
        List<Book> books = BookAPI.searchBooks(isbn.equals("0") ? null : isbn, title.equals("0") ? null : title,
                author.equals("0") ? null : author, maxRecords);
        // Demander à l'utilisateur de choisir un livre parmi les résultats
        System.out.print("Choisissez un livre (entrez un numéro) : ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consommer la nouvelle ligne laissée par nextInt()

        // Vérifier si le choix est valide
        if (choice < 1 || choice > books.size()) {
            System.out.println("Choix invalide.");
            return;
        }

        // Récupérer l'ISBN du livre choisi
        String chosenIsbn = books.get(choice - 1).getIsbn();
        String query = "SELECT id_user FROM users WHERE email = ?";

        // Établir la connexion à la base de données
        int id = 0;
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id_user");

            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Créer un nouvel objet Loan avec les informations nécessaires
        LoanDAO.addNewLoan(chosenIsbn,id);


    }


}






