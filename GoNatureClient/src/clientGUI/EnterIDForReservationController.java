package clientGUI;

import client.ChatClient;
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
	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	          SwitchScreen.changeScreen(stage,"/resources/LoginOrNewReservation.fxml"
	        		  ,"/resources/LoginOrNewReservation.css");

			  } catch (Exception e) {
			      e.printStackTrace();}
    }
    //move to new reservation screen, saving userId on table external_users in db
    @FXML
    void ClickNextBtn(ActionEvent event) {
    	String userId=idField.getText();
    	try {
		  ChatClient.typeacc="guest";
          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		  SwitchScreen.changeScreen(stage,"/resources/NewReservationForUserController.fxml"
				  ,"/resources/NewReservationForUserController.fxml");
	  	  } catch (Exception e) {
		      e.printStackTrace();}
    	
    }
	
	
	
}
