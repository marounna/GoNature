package clientGUI;

import java.io.IOException;

import client.ChatClient;
import client.ClientUI;
import common.StaticClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserMenuController {

		@FXML
	    private TableView<String> TableField;

	    @FXML
	    private Button logoutBtn;

	    @FXML
	    private Button newReservationBtn;

	    @FXML
	    private Button ordersWaitingListBtn;
	    

	    @FXML //user logs out, moving to login screen
	    void ClickOnLogOut(ActionEvent event) throws IOException {
	    	String message="logout "+StaticClass.username;
			try {
				ClientUI.chat.accept(message);
				System.out.println("UserMenuController> request Sent to server");
			}catch (Exception e){
				System.out.println("UserMenuController> Logout failed");
			}
			if(StaticClass.islogout) {
				StaticClass.islogout=false;
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        		SwitchScreen.changeScreen(stage,"/resources/LoginController.fxml"
        				,"/resources/LoginController.css");
			}
	    }

	    @FXML //moving to new reservation screen
	    void ClickOnNewReservation(ActionEvent event) throws IOException {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        System.out.println("----------test---- "+ StaticClass.typeacc);
	        switch (StaticClass.typeacc) {
	        	case "customer":
	        		SwitchScreen.changeScreen(stage,"/resources/NewReservationForUserController.fxml"
	        				,"/resources/NewReservationForUserController.css");
	    	        break;
	        	case "guide":
	        		SwitchScreen.changeScreen(stage,"/resources/NewReservationForGuideController.fxml"
	        				,"/resources/NewReservationForGuideController.css");
	    	        break;
	        }	
	    }

	    @FXML
	    void ClickOnOrdersOnWaitingList(ActionEvent event) {

	    }
}
