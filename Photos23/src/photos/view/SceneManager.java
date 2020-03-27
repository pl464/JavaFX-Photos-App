package photos.view;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.User;

/**
 * This class handles switching between the various windows of the application. 
 * It also attempts to act as an intermediary between different Controller instances.
 * @author patle
 *
 */
public class SceneManager {
    Stage mainStage;
    AnchorPane pane;
    HashMap<String, User> users;
    
    public SceneManager(Stage mainStage) {
        this.mainStage = mainStage;
    }
    
    /**
     * Switches the scene, controller, and stage given the name of an FXML file.
     * @param fxml
     * @throws Exception
     */
    public void switchScene(String fxml) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		pane = (AnchorPane)loader.load();
		
    	switch (fxml) {
    	case "Login_Window.fxml":
    		LoginController loginController = loader.getController();
    		loginController.setSceneManager(this);
    		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/users.dat"));
    		@SuppressWarnings("unchecked")
    		HashMap<String, User> users = (HashMap<String, User>)ois.readObject();
    		loginController.setUsers(users);
    		break;
    	case "Album_Display.fxml":
    		AlbumsController albumController = loader.getController();
    		albumController.setSceneManager(this);
    		break;
    	}
    		/*
            try {
                Pane p = loader.load();
                BaseController controller = loader.getController();
                controller.setSceneManager(this);
                return new Scene(p);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }*/
    	Scene scene = new Scene(pane);
		mainStage.setResizable(false);
        mainStage.setTitle("Login");
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.centerOnScreen();
    }
}