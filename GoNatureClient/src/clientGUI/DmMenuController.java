package clientGUI;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import entities.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import logic.Message;

public class DmMenuController {
	
	public static  String[] parks;

    
    @FXML
    public void initialize() {
    	getParks();
    }

    private void getParks() {
    	  ClientUI.chat.accept("park");
    	  parks=new String[StaticClass.parks.size()];
    	  for(int i=0;i<StaticClass.parks.size();i++) {
    		  parks[i]=StaticClass.parks.get(i).getParkString();
    	  }
    	  
	}

	@FXML //user logs out, moving to login screen
    void ClickOnLogOutBtn(ActionEvent event) throws IOException {
    	String message="logout "+StaticClass.username;
		try {
			ClientUI.chat.accept(message);
			System.out.println("DmMenuController> Logout request Sent to server");
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("error"+e.getMessage());
		}
		if(StaticClass.islogout) {
			StaticClass.islogout=false;
	        
    		SwitchScreen.changeScreen(event,"/clientGUI/LoginController.fxml", "/resources/LoginController.css");
		}
    }
    
	
    @FXML
    void VisitReport(ActionEvent event) {
    	SwitchScreen.changeScreen(event, "/clientGUI/DmVisitReport.fxml", "/resources/DmVisitReport.css");
    }
    
    @FXML
    void CancelledReport(ActionEvent event) {
    	SwitchScreen.changeScreen(event, "/clientGUI/DmCancelledReport.fxml","/resources/DmCancelledReport.css");
    }
}