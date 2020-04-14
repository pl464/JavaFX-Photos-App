package photos.view;

import java.io.File;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import photos.Picture;

/**
 * The controller class for Album_Display_Window.fxml.
 * @author Patrick Lee
 *
 */
public class AlbumDisplayController extends Controller {
	@FXML private Label albumName;
	@FXML private Button backButton;
	@FXML private Button logoutButton;
	@FXML private Button searchButton;
	@FXML private Button newTagButton;
	@FXML private Button addPhotoButton;
	@FXML private FlowPane previews;
	@FXML private Button viewButton;
	@FXML private Button removeButton;
	
	/**
	 * A field to keep track of the currently selected image/caption preview.
	 */
	public static Label selected; 
	/**
	 * Displays a preview of the photo and its caption of each photo in this album.
	 */
	public void displayPhotos() {
		albumName.setText(currAlbum);
		selected = null;
		previews.getChildren().clear();
		previews.setOnMouseClicked(e->{
			viewButton.requestFocus();
		});
		currUser.albums.get(currAlbum).forEach((k)->{
			Image image = new Image(k, 112, 0, true, false);
			ImageView newImage = new ImageView(image);
			
			String cap = currUser.pictures.get(k).caption;
			displayPhoto(cap, newImage, k);
		});
	}
	/**
	 * Displays the preview of a single photo and its caption and sets its behavior on being clicked.
	 * @param cap The caption of the photo.
	 * @param image The photo itself.
	 * @param filePath A string of the file path of the photo.
	 */
	public void displayPhoto(String cap, ImageView image, String filePath) {
		if (cap.length() > 12) cap = cap.substring(0, 12) + "...";
		else if (cap.length() == 0) cap = "(no caption)";
		
		Label preview = new Label(cap, image);
		preview.setContentDisplay(ContentDisplay.TOP);
		preview.setOnMouseClicked(e->{
			previews.getChildren().forEach((c)->{
				((Label)c).setBorder(new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			});
			preview.setBorder(new Border(new BorderStroke(Color.SKYBLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			selected = preview;
			selected.setUserData(filePath);
		});
		preview.setBorder(new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		previews.getChildren().add(preview);
	}
	/**
	 * Adds a new photo to the album given its file path, and displays its preview in this window.
	 * @param file The File of the photo to be added.
	 * @return True if the photo was added successfully, false otherwise.
	 */
	public boolean addNewPhoto(File file) {
		//first, add to the current user's album, if it doesn't exist already
		if (currUser.albums.get(currAlbum).contains(file.toURI().toString())) {
			return false;
		}
		currUser.albums.get(currAlbum).add(file.toURI().toString());
		Image image = new Image(file.toURI().toString(), 112, 0, true, false);
		ImageView newImage = new ImageView(image);
		displayPhoto("", newImage, file.toURI().toString());
				
		//then, add to the current user's pictures if it doesn't exist already
		if (currUser.pictures.keySet().contains(file.toURI().toString())) {
			return true;
		}
		currUser.pictures.put(file.toURI().toString(), new Picture(file));
		return true;
	}
	/**
	 * Triggers the search window for this album.
	 * @throws Exception
	 */
	public void searchAlbum() throws Exception {
		setAlbumScope(true);
		sceneManager.switchScene("Search_Window.fxml", users);
	}
	/**
	 * Adds a tag to the user's list of tags.
	 * @param tag The name of the tag.
	 * @param single Indicates whether or not the tag can only have a single value associated with it.
	 * @return True if the tag was added successfully, false otherwise (if the tag already existed)
	 */
	public boolean addTag(String tag, boolean single) {
		if (currUser.tagnames.containsKey(tag)) {
			return false;
		}
		currUser.tagnames.put(tag, single);
		return true;
	}
	/**
	 * Removes the selected photo from this album, and displays the change in the window.
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void removePhoto(ActionEvent e) {
		if (selected == null) {
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Are you sure you want to delete the selected photo?", 
				ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> confirm = alert.showAndWait();
		if (confirm.get() == ButtonType.NO) return; 
		
		currUser.albums.get(currAlbum).remove(selected.getUserData());
		displayPhotos();
		selected = null;
	}
	/**
	 * Switches the scene back to the MyAlbums window.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML
	private void goBack(ActionEvent e) throws Exception {
		sceneManager.switchScene("My_Albums_Window.fxml", users);
	}
	/**
	 * Displays the "popup" UI for adding a new photo.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML 
	private void showNewPhotoPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_Photo_Popup.fxml", this);
	}
	/**
	 * Displays the "popup" UI for adding a new tag.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML
	private void showNewTagPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_Tag_Popup.fxml", this);
	}
	/**
	 * Switches the scene to the Photo Display window with the currently selected photo.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML
	private void openPhoto(ActionEvent e) throws Exception {
		if (selected == null) {
			return;
		}
		setCurrPhoto((String) selected.getUserData());
		sceneManager.switchScene("Photo_Display_Window.fxml", users);
	}
	/**
	 * Switches the main scene to the Login Window.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml", users);
	}
}
