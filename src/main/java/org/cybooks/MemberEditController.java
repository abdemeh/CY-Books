package org.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class for editing member information.
 */
public class MemberEditController {

    @FXML
    private Text memberEdit_id;
    @FXML
    private TextField memberEdit_lastname;
    @FXML
    private TextField memberEdit_firstname;
    @FXML
    private TextField memberEdit_email;
    @FXML
    private TextField memberEdit_phone;
    @FXML
    private DatePicker memberEdit_birthday;
    @FXML
    private RadioButton memberEdit_sex_male;
    @FXML
    private RadioButton memberEdit_sex_female;
    @FXML
    private Text memberEditMessage;

    /**
     * Sets the data of the member to be edited.
     *
     * @param currentMember The Member object whose data will be displayed for editing.
     */
    public void setMemberData(Member currentMember) {
        memberEdit_id.setText(Integer.toString(currentMember.getId()));
        memberEdit_lastname.setText(currentMember.getLastName());
        memberEdit_firstname.setText(currentMember.getFirstName());
        memberEdit_email.setText(currentMember.getEmail());
        memberEdit_phone.setText(currentMember.getPhone());

        java.sql.Date sqlDateBirthday = currentMember.getBirthday();
        java.time.LocalDate localDateBirthday = sqlDateBirthday.toLocalDate();
        memberEdit_birthday.setValue(localDateBirthday);

        if (currentMember.getSex().equals("M")) {
            memberEdit_sex_male.setSelected(true);
        } else {
            memberEdit_sex_female.setSelected(true);
        }
    }

    /**
     * Closes the edit member window.
     *
     * @param event The ActionEvent triggered by clicking the close button.
     */
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
     * Updates the member information based on the edited data.
     */
    public void memberEdit() {
        if(memberEdit_lastname.getText().trim().equals("")
                || memberEdit_firstname.getText().trim().equals("")
                || memberEdit_email.getText().trim().equals("")
                || memberEdit_phone.getText().trim().equals("")
                || memberEdit_birthday.getValue()==null){
            memberEditMessage.setFill(Color.web("#cc7070"));
            memberEditMessage.setText("Veuillez remplir tous les champs requis.");
        } else if (!isEmail(memberEdit_email.getText())) {
            memberEditMessage.setFill(Color.web("#cc7070"));
            memberEditMessage.setText("Veuillez entrer un email valide.");
        } else{
            String sexValue = "M";
            if (memberEdit_sex_female.isSelected()) {
                sexValue = "F";
            }
            Member temp_member = MemberDAO.getMemberById(Integer.parseInt(memberEdit_id.getText()));
            System.out.println(temp_member);
            System.out.println(memberEdit_id.getText());
            Member member = new Member(Integer.parseInt(memberEdit_id.getText()),
                    memberEdit_lastname.getText(),
                    memberEdit_firstname.getText(),
                    memberEdit_email.getText(),
                    temp_member.getInscriptionDate(),
                    temp_member.getState(), java.sql.Date.valueOf(memberEdit_birthday.getValue()),
                    memberEdit_phone.getText(),
                    sexValue,
                    temp_member.getBlock_till());
            try {
                MemberDAO.updateMember(member);
            }catch (Exception e) {
                memberEditMessage.setFill(Color.web("#cc7070"));
                memberEditMessage.setText("Erreur dans la modification.");
            }
            memberEditMessage.setFill(Color.web("#846fcd"));
            memberEditMessage.setText("Modifications enregistrées.");
            // Notify DashboardController to update members
            DashboardController dashboardController = ControllerManager.getDashboardController();
            if (dashboardController != null) {
                dashboardController.updateMembersFromDatabase();
            }
        }
    }
}
