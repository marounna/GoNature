package clientGUI;

import java.io.IOException;

import client.ClientUI;
import common.SwitchScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterGuideRoleByServiceEmployeeController {

    @FXML
    private Button backBtn;

    @FXML
    private TextField idField;

    @FXML
    private Button updateBtn;

    @FXML
    void ClickOnBack(ActionEvent event)  throws IOException {
    	try {
    		  SwitchScreen.changeScreen(event,"/clientGUI/ServiceEmployeeMenuController.fxml"
    				  ,"/resources/ServiceEmployeeMenuController.css");

			  } catch (Exception e) {
			      e.printStackTrace();}
    }
    
    
    @FXML
    void ClickOnUpdate(ActionEvent event) throws IOException {
    	
    	String id = idField.getText();
    	if(id!=null&&!id.isEmpty()) {
    		String message = "updateGuideRole " + id;
    		try {
    			ClientUI.chat.accept(message);
    			System.out.println("LoginController> userExist request Sent to server");
    		}catch (Exception e){
    			System.out.println("LoginController> User does not exist");
    			e.printStackTrace();
    		}
    	}
    	else { 
            System.out.println("Invalid guide ID"); // Handle invalid input
        }
    }

}