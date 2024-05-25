package org.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;

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
     * Updates the member information based on the edited data.
     */
    public void memberEdit() {
        String sexValue = "M";
        if (memberEdit_sex_female.isSelected()) {
            sexValue = "F";
        }
        Member member = new Member(Integer.parseInt(memberEdit_id.getText()), memberEdit_firstname.getText(),
                memberEdit_lastname.getText(), memberEdit_email.getText(), java.sql.Date.valueOf(LocalDate.now()),
                "Actif", java.sql.Date.valueOf(memberEdit_birthday.getValue()), memberEdit_phone.getText(), sexValue);
        MemberDAO.updateMember(member);
        memberEditMessage.setText("Modifications enregistrées.");
        // Notify DashboardController to update members
        DashboardController dashboardController = ControllerManager.getDashboardController();
        if (dashboardController != null) {
            dashboardController.updateMembersFromDatabase();
        }
    }
}
