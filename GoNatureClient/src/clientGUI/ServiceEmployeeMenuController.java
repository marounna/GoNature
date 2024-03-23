package clientGUI;

import java.io.IOException;

import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class ServiceEmployeeMenuController {
	 	@FXML
	    private Button DefineGuide;
	    
	    @FXML
	    private Button LogOutBtn;

	    @FXML
	    void clickOnLogout(ActionEvent event) throws IOException {
	    	String message="logout "+StaticClass.username;
			try {
				ClientUI.chat.accept(message);
				System.out.println("UserMenuController> request Sent to server");
			}catch (Exception e){
				System.out.println("UserMenuController> Logout failed");
			}
			if(StaticClass.islogout) {
				StaticClass.islogout=false;
	      		SwitchScreen.changeScreen(event,"/clientGUI/LoginController.fxml","/resources/LoginController.css");
			}
	    }
	    
	    @FXML
	    void defineGuideRole(ActionEvent event) throws IOException {
	    	try {
	      		  SwitchScreen.changeScreen(event,"/clientGUI/RegisterGuideRoleByServiceEmployeeController.fxml"
	      				  ,"/clientGUI/RegisterGuideRoleByServiceEmployeeController.css");

				  } catch (Exception e) {
				      e.printStackTrace();}
	    	
	    	
	    }

}