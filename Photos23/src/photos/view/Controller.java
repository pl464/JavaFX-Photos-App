package photos.view;

import java.util.HashMap;

import photos.User;

/**
 * Superclass of controllers for the main windows. Allows photo library data to be stored in a centralized
 * location, and allows SceneManager to open secondary windows ("pop-ups") by referencing a general controller
 * as the parent controller, rather than requiring separate methods.
 * 
 * @author Patrick Lee
 *
 */
public abstract class Controller {
	/**
	 * The SceneManager object for switching to/opening other scenes, and passing control to other controllers. 
	 */
	SceneManager sceneManager;
	/**
	 * HashMap of users' data to be accessed by different controllers.
	 */
	public static HashMap<String, User> users;
	/**
	 * The User object of the current user logged in.
	 */
	public static User currUser;
	/**
	 * A String indicating which album the current User is interacting with.
	 */
	public static String currAlbum;
	/**
	 * A String indicating which photo the current User is interacting with (its file path).
	 */
	public static String currPhoto;
	/**
	 * Indicates whether the user has most recently opened the Album Display Window (as opposed to the
	 * MyAlbums Window) for searching and transitioning purposes. Set to true if the User was previously
	 * on the Album Display Window, and false otherwise.
	 */
	public static boolean albumScope; //true if searching at the album level, false if searching all albums
	/**
	 * Sets the SceneManager for this controller.
	 * @param sceneManager The SceneManager to be set.
	 */
	public void setSceneManager(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}

	/**
	 * Allows user information to be passed into this Controller instance from the SceneManager.
	 * @param users The list of users.
	 */
	public void setUsers (HashMap<String, User> userData){
		users = userData;
		return;
	}
	/**
	 * Sets the current user.
	 * @param user The User to be set.
	 */
	public void setCurrUser (User user) {
		currUser = user;
	}
	/**
	 * Sets the current album.
	 * @param albumName The name of the album to be set.
	 */
	public void setCurrAlbum (String albumName) {
		currAlbum = albumName;
	}
	/**
	 * Sets the current photo.
	 * @param photoPath The file path of the photo to be set.
	 */
	public void setCurrPhoto (String photoPath) {
		currPhoto = photoPath;
	}
	/**
	 * Sets the album scope.
	 * @param b The boolean to be set. True if the user is at the Album Display Level, false otherwise.
	 */
	public void setAlbumScope (boolean b) {
		albumScope = b;
	}
}
