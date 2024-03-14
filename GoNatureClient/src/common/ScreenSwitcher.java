package common;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScreenSwitcher {
		
	public void changeScreen(ActionEvent event, String path) {
		    try {
			      Parent root = (Parent)FXMLLoader.load(getClass().getResource(path));
			      Scene scene = new Scene(root);
			      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			        stage.setScene(scene);
			        stage.show();

			   } catch (Exception e) {
			       e.printStackTrace();
		    } 
	}
	
	public static void Alert(String Title,String HeaderText) {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.NONE);
		alert.setTitle(Title);
		alert.setAlertType(AlertType.INFORMATION);
		alert.setHeaderText(HeaderText);
		//alert.setContentText(msg);
		alert.showAndWait();
	}
	public static void Alert(String Title,String HeaderText,String ContentText) {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(AlertType.NONE);
		alert.setTitle(Title);
		alert.setAlertType(AlertType.INFORMATION);
		alert.setHeaderText(HeaderText);
		alert.setContentText(ContentText); 
		alert.showAndWait();
	}
}
