package clientGUI;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import common.StaticClass;
import common.SwitchScreen;
import entities.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;



public class DmMenuController {
	
	    @FXML
	    private Button logOutBtn;

	    @FXML
	    private TableColumn<?, ?> maxAfterCapacityTable;

	    @FXML
	    private TableColumn<?, ?> maxAfterDwellTable;

	    @FXML
	    private TableColumn<?, ?> maxBeforeCapacityTable;

	    @FXML
	    private TableColumn<?, ?> maxBeforeDwellTable;

	    @FXML
	    private TableColumn<?, ?> noCapacityTable;

	    @FXML
	    private TableColumn<?, ?> noDwellTable;

	    @FXML
	    private TableColumn<?, ?> parkNameCapacityTable;

	    @FXML
	    private TableColumn<?, ?> parkNameDwellTable;

	    @FXML
	    private TableColumn<?, ?> yesCapacityTable;

	    @FXML
	    private TableColumn<?, ?> yesDwellTable;
	    
	    
	    public static  String[] parks;

	    
	    @FXML
	    public void initialize() {
	    	  ClientUI.chat.accept("park");
	    	  parks=new String[StaticClass.parks.size()];
	    	  for(int i=0;i<StaticClass.parks.size();i++) {
	    		  parks[i]=StaticClass.parks.get(i).getParkString();
	    	  }
	    }

	    @FXML
	    void CancelledReport(ActionEvent event) {
	    	SwitchScreen.changeScreen(event, "/clientGUI/DmCancelledReport.fxml","/resources/DmCancelledReport.css");

	    }


	    @FXML
	    void VisitReport(ActionEvent event) {
	    	SwitchScreen.changeScreen(event, "/clientGUI/DmVisitReport.fxml", "/resources/DmVisitReport.css");
	    }
	    
	    @FXML
	    void clickOnLogout(ActionEvent event) {
			try {
				ClientUI.chat.accept("logout "+StaticClass.username);
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

	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
