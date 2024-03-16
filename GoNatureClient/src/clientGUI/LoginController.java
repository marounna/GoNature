package clientGUI;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import entities.Park;
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

//commit by Adar 15/3 time 9.20
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
    
    public static boolean isexist =false;
    public static boolean islogged =false;
    public static String typeacc;
    public static ArrayList<Park> parks = new ArrayList<>();

    @FXML //return to the previous screen
    void ClickOnBackBtn(ActionEvent event) throws IOException {
		try {

	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      		  SwitchScreen.changeScreen(stage,"/clientGUI/LoginOrNewReservation.fxml"
      				  ,"/clientGUI/LoginOrNewReservation.css");

			  } catch (Exception e) {
			      e.printStackTrace();}
    }//login into the user and move to the user menu screen
    @FXML
    void ClickOnLoginBtn(ActionEvent event) throws IOException {
    	String username=userID.getText();
    	String pass=password.getText();
    	String message="userExist "+username + " " + pass;
		try {
			ClientUI.chat.accept(message);
			System.out.println("LoginController> userExist request Sent to server");
		}catch (Exception e){
			System.out.println("LoginController> User does not exist");
		}
		System.out.println("check before ifexist login controller");
		if (isexist) {

			isexist=false;
			try {
				String loginMessage= "login " + username +" "+ pass ;
			ClientUI.chat.accept(loginMessage);//Send Msg TO Server
			System.out.println("LoginController> login request Sent to server");
			}catch(Exception e) {
				System.out.println("LoginController> Login Failed");
			}
		}	
		if(!islogged) {
			if (isexist){
				isexist=false;
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        System.out.println(typeacc);
		        switch(ChatClient.typeacc) {
		        	case "customer":
		        	case "guide":
		        		SwitchScreen.changeScreen(stage,"/clientGUI/UserMenuController.fxml"
		        				,"/clientGUI/UserMenuController.css");
		        		break;
		        	case "park manager" :
		        		SwitchScreen.changeScreen(stage,"/clientGUI/ParkManagerMenuController.fxml"
		        				,"/clientGUI/ParkManagerMenuController.css");
		        		break;
		        	case "department manager":
		        		SwitchScreen.changeScreen(stage,"/clientGUI/DepartmentManagerMenuController.fxml"
		        				,"/clientGUI/DepartmentManagerMenuController.css");
		        		break;
		        	case "service employee":
		        		SwitchScreen.changeScreen(stage,"/clientGUI/ServiceEmployeeMenuController.fxml"
		        				,"/clientGUI/ServiceEmployeeMenuController.css");
		        		break;
		        	case "park employee":
		        		SwitchScreen.changeScreen(stage,"/clientGUI/EmployeeMenuController.fxml"
		        				,"/clientGUI/EmployeeMenuController.css");
		        		break;
		        }
	        }
		}		
		else {
			if(islogged) { 
				islogged=false;
				userNotExist.setText("User already logged in");}
			else 
				userNotExist.setText("User details are not valid");
		}
    }
    
}

