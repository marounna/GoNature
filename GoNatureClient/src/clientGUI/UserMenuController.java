package clientGUI;

import java.io.IOException;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserMenuController {
	
	    public static String username;

		@FXML
	    private TableView<String> TableField;

	    @FXML
	    private Button logoutBtn;

	    @FXML
	    private Button newReservationBtn;

	    @FXML
	    private Button ordersWaitingListBtn;
	    
	    public static boolean islogout =false;

	    @FXML //user logs out, moving to login screen
	    void ClickOnLogOut(ActionEvent event) throws IOException {
	    	String message="logout "+username;
			try {
				ClientUI.chat.accept(message);
				System.out.println("UserMenuController> request Sent to server");
			}catch (Exception e){
				System.out.println("UserMenuController> Logout failed");
			}
			if(islogout) {
				islogout=false;
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        		SwitchScreen.changeScreen(stage,"/clientGUI/LoginController.fxml"
        				,"/clientGUI/LoginController.css");
			}
	    }

	    @FXML //moving to new reservation screen
	    void ClickOnNewReservation(ActionEvent event) throws IOException {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        System.out.println("----------test---- "+ ChatClient.typeacc);
	        switch (ChatClient.typeacc) {
	        	case "customer":
	        		SwitchScreen.changeScreen(stage,"/clientGUI/NewReservationForUserController.fxml"
	        				,"/clientGUI/NewReservationForUserController.css");
	    	        break;
	        	case "guide":
	        		SwitchScreen.changeScreen(stage,"/clientGUI/NewReservationForGuideController.fxml"
	        				,"/clientGUI/NewReservationForGuideController.css");
	    	        break;
	        }	
	    }

	    @FXML
	    void ClickOnOrdersOnWaitingList(ActionEvent event) {

	    }
}
