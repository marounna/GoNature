/*package clientGUI;

import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServiceEmployeeMenuController {

    @FXML
    private Button defineGuideBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    void clickOnDefineGuide(ActionEvent event) {
		  SwitchScreen.changeScreen(event,"/clientGUI/RegisterGuideRoleByServiceEmployeeController.fxml"
  				  ,"/clientGUI/RegisterGuideRoleByServiceEmployeeController.css");
    }

    @FXML
    void clickOnLogout(ActionEvent event) {
		try {
			ClientUI.chat.accept("logout "+StaticClass.username);
			System.out.println("UserMenuController> request Sent to server");
		}catch (Exception e){
			System.out.println("UserMenuController> Logout failed");
		}
		if(StaticClass.islogout) {
			StaticClass.islogout=false;
      		SwitchScreen.changeScreen(event,"/clientGUI/LoginController.fxml","/resources/LoginController.css");
		}
    }

}
	*/

package clientGUI;

import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServiceEmployeeMenuController {

    @FXML
    private Button defineGuideBtn;

    @FXML
    private Button logoutBtn;
    

    @FXML
    void clickOnDefineGuide(ActionEvent event) {
		  SwitchScreen.changeScreen(event,"/clientGUI/RegisterGuideRoleByServiceEmployeeController.fxml"
  				  ,"/resources/RegisterGuideRoleByServiceEmployee.css");
    }

    @FXML
    void clickOnLogout(ActionEvent event) {
		try {
			ClientUI.chat.accept("logout "+StaticClass.username);
			System.out.println("UserMenuController> request Sent to server");
		}catch (Exception e){
			System.out.println("UserMenuController> Logout failed");
		}
		if(StaticClass.islogout) {
			StaticClass.islogout=false;
			StaticClass.typeacc="";
      		SwitchScreen.changeScreen(event,"/clientGUI/LoginController.fxml","/resources/LoginController.css");
		}
    }
    

}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
