package org.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;

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

    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void memberAdd() {
        String sex_value = "M";
        if (memberAdd_sex_female.isSelected()) {
            sex_value = "F";
        }
        Member member = new Member(1, memberAdd_firstname.getText(), memberAdd_lastname.getText(), memberAdd_email.getText(), java.sql.Date.valueOf(LocalDate.now()), "Actif", java.sql.Date.valueOf(memberAdd_birthday.getValue()), memberAdd_phone.getText(), sex_value);
        MemberDAO.addMember(member);

        memberAdd_lastname.setText("");
        memberAdd_firstname.setText("");
        memberAdd_email.setText("");
        memberAdd_phone.setText("");
        memberAdd_birthday.setValue(null);
        memberAddMessage.setText("");
        memberAdd_sex_male.setSelected(true);
        memberAdd_sex_female.setSelected(false);

        memberAddMessage.setText("Membre ajouté avec succès.");

        // Notify DashboardController to update members
        DashboardController dashboardController = ControllerManager.getDashboardController();
        if (dashboardController != null) {
            dashboardController.updateMembersFromDatabase();
        }
    }
}
