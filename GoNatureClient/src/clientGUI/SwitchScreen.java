package clientGUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchScreen {
	//moving between screens
	public static void changeScreen(Stage stage,String path,String css) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(SwitchScreen.class.getResource(path));
            // Create a new scene with the loaded content
            Scene scene = new Scene(root);
            // Set the new scene to the provided stage
            stage.setScene(scene);
            //scene.getStylesheets().add(getClass().getResource(css).toExternalForm());

            // Show the updated stage
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error switching screens: " + e.getMessage());
        }
    }
        
}

