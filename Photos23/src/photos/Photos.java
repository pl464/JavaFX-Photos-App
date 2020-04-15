package photos;
import java.io.File;
import java.util.ArrayList;
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
		
		if (users.containsKey("stock") == false) {
			users.put("stock", new User());
			users.get("stock").albums.put("stock", new ArrayList<String>());
			String currDir = System.getProperty("user.dir");
			String a = currDir + File.separator + "data" + File.separator + "blathers.jpg";
			String b = currDir + File.separator + "data" + File.separator + "isabelle.jpg";
			String c = currDir + File.separator + "data" + File.separator + "kkslider.jpg";
			String d = currDir + File.separator + "data" + File.separator + "mrresetti.jpg";
			String e = currDir + File.separator + "data" + File.separator + "tomnook.jpg";
			File a1 = new File(a);
			File b1 = new File(b);
			File c1 = new File(c);
			File d1 = new File(d);
			File e1 = new File(e);
			users.get("stock").albums.get("stock").add(a1.toURI().toString());
			users.get("stock").albums.get("stock").add(b1.toURI().toString());
			users.get("stock").albums.get("stock").add(c1.toURI().toString());
			users.get("stock").albums.get("stock").add(d1.toURI().toString());
			users.get("stock").albums.get("stock").add(e1.toURI().toString());
			users.get("stock").pictures.put(a1.toURI().toString(), new Picture(a1));
			users.get("stock").pictures.put(b1.toURI().toString(), new Picture(b1));
			users.get("stock").pictures.put(c1.toURI().toString(), new Picture(c1));
			users.get("stock").pictures.put(d1.toURI().toString(), new Picture(d1));
			users.get("stock").pictures.put(e1.toURI().toString(), new Picture(e1));
			sceneManager.writeUsers(users);
		}
		
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
