package org.example.cybooks;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
    Text memberEditMessage;

    public void setMemberData(Member currentMember) {
        memberEdit_id.setText(Integer.toString(currentMember.getId()));
        memberEdit_lastname.setText(currentMember.getLastName());
        memberEdit_firstname.setText(currentMember.getFirstName());
        memberEdit_email.setText(currentMember.getEmail());
        memberEdit_phone.setText(currentMember.getPhone());

        java.sql.Date sqlDateBirthday = currentMember.getBirthday();
        java.time.LocalDate localDateBirthday = sqlDateBirthday.toLocalDate();
        memberEdit_birthday.setValue(localDateBirthday);

        if(currentMember.getSex()=="M"){
            memberEdit_sex_male.setSelected(true);
        }else{
            memberEdit_sex_female.setSelected(true);
        }
    }
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void memberEdit(){
        MemberDAO memberDAO = new MemberDAO();
        String sex_value = "M";
        if (memberEdit_sex_female.isSelected()) {
            sex_value = "F";
        }
        Member member = new Member(Integer.parseInt(memberEdit_id.getText()),memberEdit_firstname.getText(),memberEdit_lastname.getText(),memberEdit_email.getText(),
        java.sql.Date.valueOf(LocalDate.now()),"Actif",java.sql.Date.valueOf(memberEdit_birthday.getValue()),memberEdit_phone.getText(),sex_value);
        memberDAO.updateMember(member);
        memberEditMessage.setText("Modifications enregistrées.");
        // Notify DashboardController to update members
        DashboardController dashboardController = ControllerManager.getDashboardController();
        if (dashboardController != null) {
            dashboardController.updateMembersFromDatabase();
        }

    }
}
