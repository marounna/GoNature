package clientGUI;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
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

      		  SwitchScreen.changeScreen(event,"/clientGUI/LoginOrNewReservation.fxml"
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
		/*if (!StaticClass.isexist)
			existlabel.setString("Username does not exist");*///need to take care of when user enter not valid user details--------------------------------------------------------------------------------------
		
		
		
		
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
		if(!StaticClass.islogged&&StaticClass.isexist) {
			if (StaticClass.isexist){
				StaticClass.isexist=false;
		        System.out.println(StaticClass.typeacc);
		        switch(StaticClass.typeacc) {
		        	case "customer":
		        	case "guide":
		        		if(StaticClass.typeacc.equals("customer")) {
		        			StaticClass.reservationtype="customer";
		        			StaticClass.discounttype="personal";
		        		}
		        		
		        		else { 
		        			StaticClass.reservationtype="guide";
		        			StaticClass.discounttype="guide";
		        		
		        		}
		        		SwitchScreen.changeScreen(event,"/clientGUI/UserMenuController.fxml"
		        				,"/resources/UserMenuController.css");
		        		break;
		        	case "park manager" :
		        		System.out.println("this is login controller park manager switch!\nuser name = " + StaticClass.username);
		        		SwitchScreen.changeScreen(event,"/clientGUI/ParkManagerMenuController.fxml","/resources/ParkManagerMenuController.css");
		        		System.out.println("this is login controller park manager switch!2\nuser name = " + StaticClass.username);
		        		break;
		        	case "department manager":
		        		SwitchScreen.changeScreen(event,"/clientGUI/DmMenuController.fxml"
		        				,"/resources/DmMenuController.css");
		        		break;
		        	case "service employee":
		        		SwitchScreen.changeScreen(event,"/clientGUI/ServiceEmployeeMenuController.fxml"
		        				,"/resources/ServiceEmployeeMenuController.css");
		        		break;
		        	case "park employee":
		        		SwitchScreen.changeScreen(event,"/clientGUI/EmployeeMenuController.fxml"
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

