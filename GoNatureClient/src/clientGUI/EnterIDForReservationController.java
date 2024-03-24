package clientGUI;

import client.ChatClient;
import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EnterIDForReservationController {
	

    @FXML
    private TextField idField;

    @FXML
    private Button BackBtn;

    @FXML
    private Button NextBtn;
    //go back to the previous screen
    @FXML
    void ClickBackBtn(ActionEvent event) {
		try {
	          SwitchScreen.changeScreen(event,"/clientGUI/LoginOrNewReservation.fxml"
	        		  ,"/resources/LoginOrNewReservation.css");

			  } catch (Exception e) {
			      e.printStackTrace();}
    }
    //move to new reservation screen, saving userId on table external_users in db
    @FXML
    void ClickNextBtn(ActionEvent event) {
    	String userId=idField.getText();
		StaticClass.typeacc="guest";
    	try {
    		StaticClass.reservationtype="customer";
			StaticClass.discounttype="personal";
    		String id=idField.getText();
    		ClientUI.chat.accept("checkExternalUser "+ id);
    		if(StaticClass.externaluser.equals("0")) {
    			ClientUI.chat.accept("addExternalUser "+id);
    		}
    		if(StaticClass.addexternaluser==1||StaticClass.externaluser.equals("1")) {
    			StaticClass.userid=userId;
    			  SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
    					  ,"/resources/UserMenuController.css");
    		}
	  	  } catch (Exception e) {
		      e.printStackTrace();}
    	
    }
	
	
	
}
