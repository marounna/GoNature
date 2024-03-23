package common;

import javax.print.DocFlavor.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SwitchScreen {
	//moving between screens
	public static void changeScreen(ActionEvent event,String path,String css ) {
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    try {
	        // Load the FXML file
	        Parent root = FXMLLoader.load(SwitchScreen.class.getResource(path));
	        // Create a new scene with the loaded content
	        Scene scene = new Scene(root);
	        // Set the new scene to the provided stage
	        stage.setScene(scene);

	        java.net.URL cssResource = SwitchScreen.class.getResource(css);
	        if (cssResource != null) {
	            scene.getStylesheets().add(cssResource.toExternalForm());
	        } else {
	            System.out.println("CSS file not found: " + css);
	        }
	        enableDrag(root, stage);

	        // Show the updated stage
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Error switching screens: " + e.getMessage());
	    }
    }
	
    public static void enableDrag(Parent root, Stage stage) {
        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];

        root.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });
    }
	
	
	
}
/*public class SwitchScreen {
    // Moving between screens
    public static void changeScreen(ActionEvent event, String path, String css, boolean undecorated) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the stage style only if it is not visible yet
        if (!stage.isShowing() && undecorated) {
            stage.initStyle(StageStyle.UNDECORATED);
        }

        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(SwitchScreen.class.getResource(path));
            Scene scene = new Scene(root);

            // Apply CSS if it exists
            if (css != null && !css.isEmpty()) {
                java.net.URL cssResource = SwitchScreen.class.getResource(css);
                if (cssResource != null) {
                    scene.getStylesheets().add(cssResource.toExternalForm());
                } else {
                    System.out.println("CSS file not found: " + css);
                }
            }

            // Set the new scene to the provided stage
            stage.setScene(scene);

            // Show the updated stage
            if (!stage.isShowing()) {
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error switching screens: " + e.getMessage());
        }
    }
}*/


