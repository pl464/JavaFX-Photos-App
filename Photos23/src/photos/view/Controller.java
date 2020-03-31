package photos.view;

import java.util.HashMap;
import photos.User;

/**
 * Superclass of controllers for the main windows. Allows photo library data to be stored in a centralized
 * location, and allows SceneManager to open secondary windows ("pop-ups") by referencing a general controller
 * as the parent controller, rather than requiring separate methods.
 * 
 * @author patle
 *
 */
public abstract class Controller {
	SceneManager sceneManager;
	public static HashMap<String, User> users;
	
	public void setSceneManager(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}

	/**
	 * Allows user information to be passed into this Controller class from the SceneManager.
	 * @param users
	 */
	public void setUsers (HashMap<String, User> userData){
		users = userData;
		return;
	}
}
