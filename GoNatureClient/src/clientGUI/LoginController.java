package clientGUI;

import java.io.IOException;
import java.net.URL;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {
	
    @FXML
    private TextField password;

    @FXML
    private TextField userID;
	
    @FXML
    private Button BackBtn;

    @FXML
    private Button LoginBtn;

    @FXML //return to the previous screen
    void ClickOnBackBtn(ActionEvent event) throws IOException {
		try {
			  FXMLLoader loader = new FXMLLoader();
	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			  loader.setLocation(getClass().getResource("/clientGUI/LoginOrNewReservation.fxml")); // Update the path to your FXML
			  Parent previousScreen = loader.load();
			  Scene scene = new Scene(previousScreen);
			  stage.setScene(scene);
			  // Show the updated stage
			  stage.show();
			  } catch (Exception e) {
			      e.printStackTrace();}
    }

    @FXML
    void ClickOnLoginBtn(ActionEvent event) {
    	String userId=userID.getText();
    	String pass=password.getText();
    	String message="2 Login"+userId + " " + pass;
		try {
			ClientUI.chat.accept(message);
		}catch (Exception e){
			System.out.println("LoginController> User is not exist");
		}
		
		
		
		
    }

}
