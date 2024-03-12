package clientGUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NewReservationController {

	    @FXML
	    private Button BackBtn;

	    @FXML
	    private DatePicker Date;

	    @FXML
	    private Button NextBtn;

	    @FXML
	    private TextField NumberOfVisitors;

	    @FXML
	    private ComboBox<String> TimeCombo;

	    @FXML
	    private ComboBox<String> ParkNameCombo;

	    @FXML
	    void ClickOnBack(ActionEvent event) throws IOException {
			FXMLLoader loader = new FXMLLoader();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			loader = new FXMLLoader(getClass().getResource("/clientGUI/EnterIDForReservatiovController.fxml"));
			Pane root = loader.load();
	        Scene scene = new Scene(root);
	        //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
	    }

	    @FXML
	    void ClickOnNext(ActionEvent event) {
	    	
	    	
	    	
	    	

	    }
	    
	    @FXML
	    private void initialize() {
	        TimeCombo.getItems().addAll("8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00",
	        		"17:00","18:00","19:00");
	        ParkNameCombo.getItems().addAll("Safari", "Gan-Tanahi", "Ya'ar-Hakofim", "Hay-Park","Mitspe-Tat-Yami");
	    }
	
	
	
}
