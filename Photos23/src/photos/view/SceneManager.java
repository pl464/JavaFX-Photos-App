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
 * @author Patrick
 *
 */
public class SceneManager {
	/**
	 * The main stage for the application.
	 */
    Stage mainStage;
    /**
     * The AnchorPane acting as the base element of the main stage's Scene.
     */
    AnchorPane pane;
    /**
     * The constructor for the SceneManager.
     * @param mainStage The Stage to be used for the main UI scenes.
     */
    public SceneManager(Stage mainStage) {
        this.mainStage = mainStage;
    }
    
    /**
     * Switches the scene, controller, and stage given the name of an FXML file.
     * @param fxml The FXML file of the target scene.
     * @param users The current HashMap of users' data.
     * @throws Exception
     */
    public void switchScene(String fxml, HashMap<String, User> users) throws Exception {
    	writeUsers(users); //update user data each time the main scene changes
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
	    		albumDisplayController.displayPhotos();
	    		break;
	    		
	    	case "Search_Window.fxml":
	    		SearchController searchController = loader.getController();
	    		searchController.setSceneManager(this);
	    		searchController.setTags();
	    		break;
	    		
	    	case "Photo_Display_Window.fxml":
	    		PhotoDisplayController photoDisplayController = loader.getController();
	    		photoDisplayController.setSceneManager(this);
	    		photoDisplayController.displaySingle();
	    		break;
    	}
    	Scene scene = new Scene(pane);
		mainStage.setResizable(false);
        mainStage.setTitle("Photos");
		mainStage.setScene(scene);
		mainStage.show();
		mainStage.centerOnScreen();
    }
    /**
     * Opens another scene without closing the main scene. 
     * @param fxml The FXML of the target scene.
     * @param controller The controller of the calling ("parent") scene, which remains open.
     * @throws Exception
     */
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
				if (controller.getClass().toString().equals("class photos.view.MyAlbumsController")) {
					newTagController.setParentController((MyAlbumsController)controller);
				} else {
					newTagController.setParentController((AlbumDisplayController)controller);
				}
				title = "Add Tag";
				break;
			case "New_Album_Popup.fxml":
				NewAlbumController newAlbumController = loader.getController();
				if (controller.getClass().toString().equals("class photos.view.MyAlbumsController")) {
					newAlbumController.setParentController((MyAlbumsController) controller);
				} else {
					newAlbumController.setParentController((SearchController) controller);
				}
				title = "Add Album";
				break;
			case "Rename_Album_Popup.fxml":
				RenameAlbumController renameAlbumController = loader.getController();
				renameAlbumController.setParentController((MyAlbumsController)controller);
				title = "Rename Album";
				break;
			case "New_Photo_Popup.fxml":
				NewPhotoController newPhotoController = loader.getController();
				newPhotoController.setParentController((AlbumDisplayController)controller);
				title = "Add Photo";
				break;
			case "Edit_Caption_Popup.fxml":
				EditCaptionController editCaptionController = loader.getController();
				editCaptionController.setParentController((PhotoDisplayController)controller);
				title = "Edit Caption";
				break;
			case "Add_Tag_Popup.fxml":
				AddTagController addTagController = loader.getController();
				addTagController.setParentController((PhotoDisplayController)controller);
				title = "Add Tag";
				addTagController.setTags();
				break;
			case "Copy_Photo_Popup.fxml":
				CopyPhotoController copyPhotoController = loader.getController();
				copyPhotoController.setParentController((PhotoDisplayController)controller);
				title = "Copy Photo";
				copyPhotoController.setAlbums();
				break;
			case "Move_Photo_Popup.fxml":
				MovePhotoController movePhotoController = loader.getController();
				movePhotoController.setParentController((PhotoDisplayController)controller);
				title = "Move Photo";
				movePhotoController.setAlbums();
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
    /**
     * Sets the users' data for the application to the data serialized in users.dat.
     * @return A HashMap of usernames mapping to each user's data.
     * @throws Exception
     */
    public HashMap<String, User> readUsers() throws Exception{
    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/users.dat"));
		@SuppressWarnings("unchecked")
		HashMap<String, User> users = (HashMap<String, User>)ois.readObject();
		ois.close();
		return users;
    }
    /**
     * Serializes the current users' data into the users.dat file.
     * @param users The HashMap of user data to be serialized.
     * @throws Exception
     */
    public void writeUsers(HashMap<String, User> users) throws Exception {
    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
		oos.writeObject(users);
		oos.close();
		return;
    }
    
}