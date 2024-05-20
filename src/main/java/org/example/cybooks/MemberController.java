package org.example.cybooks;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MemberController {
    @FXML
    private Text member_name;
    @FXML
    private Text member_email;
    @FXML
    private Text member_added;
    @FXML
    private Text member_state;
    @FXML
    private Text member_id;
    @FXML
    private Text member_image;
    @FXML
    private TextFlow member_image_bg;
    private String[] colors={"#6fcdb1","#a06fcd","#cd6f88","#6f8bcd","#919191","#cdc86f"};
    public void setData(Member member){
        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        member_name.setText(member.getName());
        member_id.setText(Integer.toString(member.getId()));
        member_email.setText(member.getEmail());
        member_added.setText(df.format(member.getInscriptionDate()));
        member_state.setText(member.getState());
        member_image.setText(getInitials(member.getName()));
        //member_image.setStyle("-fx-text-fill: "+ colors[(int)(Math.random()*colors.length)]);
        //member_image.setFill(Color.web(colors[(int)(Math.random()*colors.length)]));
        member_image_bg.setStyle("-fx-background-color: "+ colors[(int)(Math.random()*colors.length)]+";-fx-background-radius: 10px");
    }
    public static String getInitials(String name) {
        String[] nameParts = name.split(" ");
        String initials = "";
        for (int i = 0; i < Math.min(nameParts.length, 2); i++) {
            initials += nameParts[i].charAt(0);
        }
        return initials.toUpperCase();
    }
}
