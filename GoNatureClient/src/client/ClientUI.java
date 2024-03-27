package client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;

import common.SwitchScreen;


public class ClientUI extends Application {
    public static ClientController chat; // only one instance
    
    @FXML
    private Button closeBtn;
    
    @FXML
    private Button okBtn;

    @FXML
    private TextField enterHostIP;

    public static void main(String args[]) throws Exception { 
        launch(args);  
    } // end main


    public void start(Stage primaryStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("/client/EnterServerIp.fxml"));
    	primaryStage.initStyle(StageStyle.UNDECORATED);
    	Scene scene = new Scene(root);
    	SwitchScreen.enableDrag(root, primaryStage);
        scene.getStylesheets().add(getClass().getResource("/client/EnterServerIp.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
   	} 
   
    @FXML
    private void OkAction(ActionEvent event) throws IOException {
        String serverIp = enterHostIP.getText();
		//FXMLLoader loader = new FXMLLoader();
		if(serverIp.isEmpty())
		{
			System.out.println("You must enter an id number");
			chat= new ClientController("localhost", 5555);
		}    
		chat= new ClientController(serverIp, 5555);
		try {
			chat.accept("connect");
		}catch (Exception e){
			System.out.println("ClientUI> Failed to load client into server monitor");
		}
		SwitchScreen.changeScreen(event, "/clientGUI/LoginOrNewReservation.fxml","/resources/LoginOrNewReservation.css");
    }
    
    @FXML
    void clickOnClose(ActionEvent event) {

    }
}
