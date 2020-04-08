package photos.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
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
    
    public SceneManager(Stage mainStage) {
        this.mainStage = mainStage;
    }
    
    /**
     * Switches the scene, controller, and stage given the name of an FXML file.
     * @param fxml
     * @throws Exception
     */
    public void switchScene(String fxml, HashMap<String, User> users) throws Exception {
    	//writeUsers(users); //update user data each time the main scene changes
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		pane = (AnchorPane)loader.load();
		
    	switch (fxml) {
	    	case "Login_Window.fxml":
	    		LoginController loginController = loader.getController();
	    		loginController.setSceneManager(this);
	    		loginController.setUsers(readUsers());
	    		break;
    		
	    	case "My_Albums_Window.fxml":
	    		MyAlbumsController albumController = loader.getController();
	    		albumController.setSceneManager(this);
	    		albumController.displayAlbums();
	    		break;

	    	case "Admin_Window.fxml":
	    		AdminController adminController = loader.getController();
	    		adminController.setSceneManager(this);
	    		adminController.setUsers(readUsers());
	    		adminController.displayUsers(readUsers());
	    		break;
	    		
	    	case "Album_Display_Window.fxml":
	    		AlbumDisplayController albumDisplayController = loader.getController();
	    		albumDisplayController.setSceneManager(this);
    	}
    	Scene scene = new Scene(pane);
		mainStage.setResizable(false);
        mainStage.setTitle("Login");
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.centerOnScreen();
    }
    
    public void openScene(String fxml, Controller controller) throws Exception {
    	String title = "";
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(fxml));
		AnchorPane pane = (AnchorPane)loader.load();
		
		switch (fxml) {
			case "New_User_Popup.fxml":
				NewUserController newUserController = loader.getController();
				newUserController.setParentController((AdminController)controller);
				title = "Add User";
				break;
			case "New_Tag_Popup.fxml":
				NewTagController newTagController = loader.getController();
				newTagController.setParentController((MyAlbumsController)controller);
				title = "Add Tag";
				break;
			case "New_Album_Popup.fxml":
				NewAlbumController newAlbumController = loader.getController();
				newAlbumController.setParentController((MyAlbumsController)controller);
				title = "Add Album";
				break;
			case "Rename_Album_Popup.fxml":
				RenameAlbumController renameAlbumController = loader.getController();
				renameAlbumController.setParentController((MyAlbumsController)controller);
				title = "Rename Album";
				break;
		}
		
		Scene scene = new Scene(pane);
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
        window.setTitle(title);
		window.setScene(scene);
		window.show();
		window.centerOnScreen();
    }
    
    public HashMap<String, User> readUsers() throws Exception{
    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/users.dat"));
		@SuppressWarnings("unchecked")
		HashMap<String, User> users = (HashMap<String, User>)ois.readObject();
		ois.close();
		return users;
    }
    
    public void writeUsers(HashMap<String, User> users) throws Exception {
    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
		oos.writeObject(users);
		oos.close();
		return;
    }
    
}