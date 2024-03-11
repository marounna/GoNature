package Server;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Order;
import serverGUI.ServerPortFrameController;
import Server.EchoServer;
import java.util.ArrayList;
import java.util.Vector;

import Server.EchoServer;


public class ServerUI extends Application {
    final public static int DEFAULT_PORT = 5555;
    public static void main(String args[]) throws Exception {   
        launch(args);
    } // end main

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
		ServerPortFrameController aFrame = new ServerPortFrameController(); // create StudentFrame
		 
		aFrame.start(primaryStage);
	}
 

    public static void runServer(String p) {
        int port; // Port to listen on
        port = Integer.parseInt(p);
        /*try {
            port = Integer.parseInt(p); // Set port to user-defined value or DEFAULT_PORT
        } catch (NumberFormatException e) {
            System.out.println("ERROR - Could not connect! Invalid port number provided: " + p);
            port = DEFAULT_PORT; // Set to default if the provided port number is invalid
        }*/

        EchoServer sv = new EchoServer(port);

        try {
            sv.listen(); // Start listening for connections
            //System.out.println("Server is listening on port " + port);
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients: " + ex.getMessage());
        }
    }
}
