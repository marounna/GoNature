package clientGUI;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.StaticClass;
import entities.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class LoginController {
    @FXML
    private Label userNotExist;
	
    @FXML
    private PasswordField password;

    @FXML
    private TextField userID;
	
    @FXML
    private Button BackBtn;

    @FXML
    private Button LoginBtn;
    

    @FXML //return to the previous screen
    void ClickOnBackBtn(ActionEvent event) throws IOException {
		try {

	          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      		  SwitchScreen.changeScreen(stage,"/clientGUI/LoginOrNewReservation.fxml"
      				  ,"/resources/LoginOrNewReservation.css");

			  } catch (Exception e) {
			      e.printStackTrace();}
    }//login into the user and move to the user menu screen
    @FXML
    void ClickOnLoginBtn(ActionEvent event) throws IOException {
    	StaticClass.available=0;
    	String username=userID.getText();
    	String pass=password.getText();
    	String message="userExist "+username + " " + pass;
		try {
			ClientUI.chat.accept(message);
			System.out.println("LoginController> userExist request Sent to server");
		}catch (Exception e){
			System.out.println("LoginController> User does not exist");
		}
		System.out.println("check before if exist login controller");
		if (StaticClass.isexist) {
			StaticClass.username=username;
			System.out.println("this is loginController> username is: "+StaticClass.username);
			StaticClass.isexist=false;
			try {
				String loginMessage= "login " + username +" "+ pass ;
			ClientUI.chat.accept(loginMessage);//Send Msg TO Server
			System.out.println("LoginController> login request Sent to server");
			}catch(Exception e) {
				System.out.println("LoginController> Login Failed");
			}
		}	
		if(!StaticClass.islogged) {
			if (StaticClass.isexist){
				StaticClass.isexist=false;
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        System.out.println(StaticClass.typeacc);
		        switch(StaticClass.typeacc) {
		        	case "customer":
		        	case "guide":
		        		if(StaticClass.typeacc.equals("customer"))
		        			StaticClass.reservationtype="customer";
		        		else { StaticClass.reservationtype="guide";}
		        		SwitchScreen.changeScreen(stage,"/clientGUI/UserMenuController.fxml"
		        				,"/resources/UserMenuController.css");
		        		break;
		        	case "park manager" :
		        		SwitchScreen.changeScreen(stage,"/clientGUI/ParkManagerMenuController.fxml"
		        				,"/resources/ParkManagerMenuController.css");
		        		break;
		        	case "department manager":
		        		SwitchScreen.changeScreen(stage,"/clientGUI/DepartmentManagerMenuController.fxml"
		        				,"/resources/DepartmentManagerMenuController.css");
		        		break;
		        	case "service employee":
		        		SwitchScreen.changeScreen(stage,"/clientGUI/ServiceEmployeeMenuController.fxml"
		        				,"/resources/ServiceEmployeeMenuController.css");
		        		break;
		        	case "park employee":
		        		SwitchScreen.changeScreen(stage,"/clientGUI/EmployeeMenuController.fxml"
		        				,"/resources/EmployeeMenuController.css");
		        		break;
		        }
	        }
		}		
		else {
			if(StaticClass.islogged) { 
				StaticClass.islogged=false;
				userNotExist.setText("User already logged in");}
			else 
				userNotExist.setText("User details are not valid");
		}
    }
    
}

