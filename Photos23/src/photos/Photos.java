package photos;

//import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.view.Controller;

public class Photos extends Application {
	Stage stage;
	Controller controller;
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("view/Login_Window.fxml"));
		Scene scene = new Scene(root);
		
		stage.setResizable(false);
		
		stage.setTitle("Photo Library");
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
