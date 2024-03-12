package clientGUI;

import java.io.IOException;

import java.net.URL;

import client.ChatClient;
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
import javafx.scene.control.Label;


public class LoginController {
    @FXML
    private Label userNotExist;
	
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
    void ClickOnLoginBtn(ActionEvent event) throws IOException {
    	String userId=userID.getText();
    	String pass=password.getText();
		FXMLLoader loader = new FXMLLoader();
    	String message="2 userExist "+userId + " " + pass;
		try {
			ClientUI.chat.accept(message);
			System.out.println("LoginController> request Sent to server");
		}catch (Exception e){
			System.out.println("LoginController> User does not exist");
		}
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		loader = new FXMLLoader(getClass().getResource("/clientGUI/UserMenuController.fxml"));
		try {
			String loginMessage= 2+" login " + userId ;
		ClientUI.chat.accept(loginMessage);//Send Msg TO Server
		System.out.println("LoginController> request Sent to server");
		}catch(Exception e) {
			System.out.println("LoginController> Login Failed");
			userNotExist.setText("User details are not valid");
		}
		Pane root = loader.load();
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
		
	
    }

}
