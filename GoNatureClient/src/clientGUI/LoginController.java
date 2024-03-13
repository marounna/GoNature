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
    
    public static boolean flag =false;
    public static boolean islogged =false;
    public static String typeacc;
    @FXML //return to the previous screen
    void ClickOnBackBtn(ActionEvent event) throws IOException {
		try {
			  FXMLLoader loader = new FXMLLoader();
	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			  loader.setLocation(getClass().getResource("/clientGUI/LoginOrNewReservation.fxml")); // Update the path to your FXML
			  Parent previousScreen = loader.load();
			  Scene scene = new Scene(previousScreen);
			  //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
			  stage.setScene(scene);
			  stage.show();
			  } catch (Exception e) {
			      e.printStackTrace();}
    }//login into the user and move to the user menu screen
    @FXML
    void ClickOnLoginBtn(ActionEvent event) throws IOException {
    	String username=userID.getText();
    	String pass=password.getText();
		FXMLLoader loader = new FXMLLoader();
    	String message="2 userExist "+username + " " + pass;
		try {
			ClientUI.chat.accept(message);
			System.out.println("LoginController> request Sent to server");
		}catch (Exception e){
			System.out.println("LoginController> User does not exist");
		}
		if (flag) {
			flag=false;
			try {
				String loginMessage= 2+" login " + username +" "+ pass ;
			ClientUI.chat.accept(loginMessage);//Send Msg TO Server
			System.out.println("LoginController> request Sent to server");
			}catch(Exception e) {
				System.out.println("LoginController> Login Failed");
			}
		}	
		System.out.println(flag);
		if(!islogged) {
			if (flag){
				flag=false;
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        switch(typeacc) {
		        	case "customer":
		        		loader = new FXMLLoader(getClass().getResource("/clientGUI/UserMenuController.fxml"));
		        	case "park manager" :
		        		loader = new FXMLLoader(getClass().getResource("/clientGUI/ParkManagerMenuController.fxml"));
		        	case "department manager":
		        		loader = new FXMLLoader(getClass().getResource("/clientGUI/DepartmentManagerMenuController.fxml"));
		        	case "guide":
		        		loader = new FXMLLoader(getClass().getResource("/clientGUI/GuideMenuController.fxml"));
		        	case "service employee":
		        		loader = new FXMLLoader(getClass().getResource("/clientGUI/EmployeeController.fxml"));

				Pane root = loader.load();
		        Scene scene = new Scene(root);
		        //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
		        stage.setScene(scene);
		        stage.show();
		        }
			}
		}
		else {
			if(islogged) { 
				islogged=false;
				userNotExist.setText("Already logged in");}
			else 
				userNotExist.setText("User details are not valid");

		}
    }
}

