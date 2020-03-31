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

public class Photos extends Application {
	Stage mainStage;
	SceneManager sceneManager;
	static HashMap<String, User> users;
	AnchorPane pane;
	
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
        mainStage.setTitle("Login");
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.centerOnScreen();
	}

	@Override
	public void stop() throws Exception {
		System.out.println("I have stopped");
		sceneManager.writeUsers(Controller.users);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
