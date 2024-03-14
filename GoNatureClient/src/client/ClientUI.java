package client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;

import common.ScreenSwitcher;


public class ClientUI extends Application {
    public static ClientController chat; // only one instance
    
    @FXML
    private Button okBtn;

    @FXML
    private TextField enterHostIP;

    public static void main(String args[]) throws Exception { 
        launch(args);  
    } // end main


    public void start(Stage primaryStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("/client/EnterServerIp.fxml"));
    	Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/client/EnterServerIp.css").toExternalForm());
        primaryStage.setTitle("Connect to server!");
        primaryStage.setScene(scene);
        primaryStage.show();
   	} 
   
    @FXML
    private void OkAction(ActionEvent event) throws IOException {
        String serverIp = enterHostIP.getText();
		FXMLLoader loader = new FXMLLoader();
		if(serverIp.isEmpty())
		{
			ScreenSwitcher.Alert("Input Request", "You must enter the host ip","In order to connect you have to enter ip address of the host!");
			System.out.println("You must enter the host ip");
			//chat= new ClientController("localhost", 5555);
		} 
		else {
		try {
			chat= new ClientController(serverIp, 5555);
			chat.accept("1");
		}catch (Exception e){
			System.out.println("ClientUI> Failed to load client into server monitor");
		}

        String newScreenPath="/clientGUI/LoginOrNewReservation.fxml";
        new ScreenSwitcher().changeScreen(event, newScreenPath);
		}

    }
}
