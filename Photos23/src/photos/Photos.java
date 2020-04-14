package photos;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.view.Controller;
import photos.view.LoginController;
import photos.view.SceneManager;

/**
 * Main class for starting and stopping the Photos application.
 * @author Patrick Lee
 *
 */
public class Photos extends Application {
	/**
	 * The main stage for this application.
	 */
	Stage mainStage;
	/**
	 * The SceneManager for this application, which handles switching between scenes.
	 */
	static SceneManager sceneManager;
	/**
	 * A HashMap to store all user information in the application.
	 */
	static HashMap<String, User> users;
	/**
	 * The AnchorPane acting as the base element of the main stage's Scene.
	 */
	AnchorPane pane;
	
	/**
	 * Starts the application by setting the UI to the login window.
	 * @param stage The stage that will act as the main stage of the application
	 */
	@Override
	public void start(Stage stage) throws Exception {
		sceneManager = new SceneManager(stage);
		users = sceneManager.readUsers();
		this.mainStage = stage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("view/Login_Window.fxml"));
		pane = (AnchorPane)loader.load();
		LoginController loginController = loader.getController();
		loginController.setSceneManager(sceneManager);
		loginController.setUsers(users);
		
		Scene scene = new Scene(pane);
		mainStage.setResizable(false);
        mainStage.setTitle("Photos");
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.centerOnScreen();
	}

	/**
	 * Ends the application with a write to the save file.
	 * @throws Exception
	 */
	@Override
	public void stop() throws Exception {
		sceneManager.writeUsers(Controller.users);
	}
	
	/**
	 * Main method for the application.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
