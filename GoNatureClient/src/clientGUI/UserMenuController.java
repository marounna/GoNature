package clientGUI;

import java.io.IOException;

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
	    public static String type;

	    @FXML
	    void ClickOnLogOut(ActionEvent event) throws IOException {
			FXMLLoader loader = new FXMLLoader();
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
		        loader.setLocation(getClass().getResource("/clientGUI/LoginController.fxml")); 
			    Parent previousScreen = loader.load();
			    Scene scene = new Scene(previousScreen);
			    stage.setScene(scene);
			    // Show the updated stage
			    stage.show();
			}
	    }

	    @FXML
	    void ClickOnNewReservation(ActionEvent event) throws IOException {
			FXMLLoader loader = new FXMLLoader();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        switch (type) {
	        	case "customer":
	    	        loader.setLocation(getClass().getResource("/clientGUI/NewReservationForUserController.fxml")); 
	    	        break;
	        	case "guide":
	    	        loader.setLocation(getClass().getResource("/clientGUI/NewReservationForGuideController.fxml")); 
	    	        break;
	        }
		    Parent previousScreen = loader.load();
		    Scene scene = new Scene(previousScreen);
		    stage.setScene(scene);
		    // Show the updated stage
		    stage.show();
	    	
	    }

	    @FXML
	    void ClickOnOrdersOnWaitingList(ActionEvent event) {

	    }
}
