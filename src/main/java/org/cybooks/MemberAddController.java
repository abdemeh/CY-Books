package org.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class for adding a new member.
 */
public class MemberAddController {
    @FXML
    private TextField memberAdd_lastname;
    @FXML
    private TextField memberAdd_firstname;
    @FXML
    private TextField memberAdd_email;
    @FXML
    private TextField memberAdd_phone;
    @FXML
    private DatePicker memberAdd_birthday;
    @FXML
    private RadioButton memberAdd_sex_male;
    @FXML
    private RadioButton memberAdd_sex_female;
    @FXML
    private Text memberAddMessage;

    /**
     * Closes the window.
     *
     * @param event The ActionEvent triggered by the close button.
     */
    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    /**
     * Checks if the given string represents a valid email address.
     *
     * @param email the string to be checked for validity as an email address
     * @return {@code true} if the string is a valid email address, {@code false} otherwise
     */
    public static boolean isEmail(String email) {
        // Regular expression for a simple email pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    /**
     * Handles adding a new member.
     */
    public void memberAdd() {
        if(memberAdd_lastname.getText().trim().equals("")
                || memberAdd_firstname.getText().trim().equals("")
                || memberAdd_email.getText().trim().equals("")
                || memberAdd_phone.getText().trim().equals("")
                || memberAdd_birthday.getValue()==null){
            memberAddMessage.setFill(Color.web("#cc7070"));
            memberAddMessage.setText("Veuillez remplir tous les champs requis.");
        } else if (!isEmail(memberAdd_email.getText())) {
            memberAddMessage.setFill(Color.web("#cc7070"));
            memberAddMessage.setText("Veuillez entrer un email valide.");
        } else{
            String sex_value = "M";
            if (memberAdd_sex_female.isSelected()) {
                sex_value = "F";
            }
            Member member = new Member(1, memberAdd_firstname.getText(), memberAdd_lastname.getText(), memberAdd_email.getText(), java.sql.Date.valueOf(LocalDate.now()), "Actif", java.sql.Date.valueOf(memberAdd_birthday.getValue()), memberAdd_phone.getText(), sex_value, Date.valueOf("2024-05-25"));

            try {
                MemberDAO.addMember(member);
                memberAdd_lastname.setText("");
                memberAdd_firstname.setText("");
                memberAdd_email.setText("");
                memberAdd_phone.setText("");
                memberAdd_birthday.setValue(null);
                memberAddMessage.setText("");
                memberAdd_sex_male.setSelected(true);
                memberAdd_sex_female.setSelected(false);

                memberAddMessage.setFill(Color.web("#846fcd"));
                memberAddMessage.setText("Membre ajouté avec succès.");
            }catch (Exception e) {
                memberAddMessage.setFill(Color.web("#cc7070"));
                memberAddMessage.setText("Erreur dans l'ajout.");
            }

            // Notify DashboardController to update members
            DashboardController dashboardController = ControllerManager.getDashboardController();
            if (dashboardController != null) {
                dashboardController.updateMembersFromDatabase();
            }
        }
    }
}
