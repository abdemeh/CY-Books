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
    public void setMemberData(Member currentMember) {
        memberEdit_id.setText(Integer.toString(currentMember.getId()));
        memberEdit_lastname.setText(currentMember.getLastName());
        memberEdit_firstname.setText(currentMember.getFirstName());
        memberEdit_email.setText(currentMember.getEmail());
        memberEdit_phone.setText(currentMember.getPhone());

        Date birthday = currentMember.getBirthday(); // Assuming this returns a Date
        LocalDate localDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        memberEdit_birthday.setValue(localDate);

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
}
