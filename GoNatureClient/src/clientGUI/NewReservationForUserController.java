package clientGUI;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NewReservationForUserController {

    @FXML
    private Button backBtn;

    @FXML
    private DatePicker date;

    @FXML
    private Button paymentBtn;

    @FXML
    private TextField numberOfVisitors;

    @FXML
    private ComboBox<String> parkNameCombo;

    @FXML
    private TextField textPhone;

    @FXML
    private ComboBox<String> timeCombo;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textFirstName;

    @FXML
    private TextField textLastName;
    
    public static int numberofvisitors;
    public static int flagC=0;
    public static String orderdetails="";
    public static ArrayList<String> parknames = new ArrayList<>();

	    @FXML
	    void ClickOnBack(ActionEvent event) throws IOException {
	    	flagC=0;
	    	FXMLLoader loader = new FXMLLoader();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        loader = new FXMLLoader(getClass().getResource("/clientGUI/UserMenuController.fxml"));
			Pane root = loader.load();
	        Scene scene = new Scene(root);
	        //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
	    }

	    @FXML
	    void ClickForPayment(ActionEvent event) throws IOException {
	    	numberofvisitors=Integer.parseInt(numberOfVisitors.getText());
	    	orderdetails+="Park name: "+ parkNameCombo.getValue().toString();
	    	orderdetails+="\nNumber of visitors: "+numberofvisitors;
	    	orderdetails+="\nDate: "+ date.getValue().toString();
	    	orderdetails+="\nTime: "+ timeCombo.getValue().toString();
	    	orderdetails+="\nFirst name: "+ textFirstName.getText();
	    	orderdetails+="\nLast name: "+ textLastName.getText();
	    	orderdetails+="\nTelephone: "+ textPhone.getText();
	        orderdetails+="\nEmail: " +textEmail.getText();
	    	FXMLLoader loader = new FXMLLoader();
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        loader = new FXMLLoader(getClass().getResource("/clientGUI/PaymentController.fxml"));
			Pane root = loader.load();
	        Scene scene = new Scene(root);
	        //scene.getStylesheets().add(getClass().getResource("/clientGUI/OrderFrame.css").toExternalForm());
	        stage.setScene(scene);
	        stage.show();
	    }
	    
	    @FXML
	    private void initialize() {
	    	flagC=1;
	        timeCombo.getItems().addAll("8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00");
	    	ClientUI.chat.accept("parkNames ");
        	parkNameCombo.getItems().addAll(parknames);
        	parknames.clear();
        }
	    
}