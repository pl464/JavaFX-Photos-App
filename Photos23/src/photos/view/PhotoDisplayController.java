package photos.view;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

/**
* Class to represent the photo display window.
* @author Lance Luo
*/
public class PhotoDisplayController extends Controller {
	
	@FXML private ImageView photoImage;
	@FXML private FlowPane tagPairs;
	@FXML private Label photoDate;
	@FXML private Label photoCaption;
	@FXML private Button backButton;
	@FXML private Button logoutButton;
	@FXML private Button previousButton;
	@FXML private Button nextButton;
	@FXML private Button captionButton;
	@FXML private Button addButton;
	@FXML private Button deleteButton;
	@FXML private Button copyButton;
	@FXML private Button moveButton;
	
	/**
	 * The selected tag name.
	 */
	static String currName = "";
	/**
	 * The selected tag value.
	 */
	static String currVal = "";
	
	/**
	* Method to display a photo along with its caption and tags.
	*/
	public void displaySingle() {
		tagPairs.getChildren().clear();
		tagPairs.setOnMouseClicked(e->{
			deleteButton.requestFocus();
		});
		
		Image image = new Image(currPhoto);
		photoImage.setImage(image);
		photoDate.setText(currUser.pictures.get(currPhoto).date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		
		if (currUser.pictures.get(currPhoto).caption.trim().isEmpty()) {
			photoCaption.setText("(no caption)");
		}
		else {
			photoCaption.setText(currUser.pictures.get(currPhoto).caption);
		}
		
		for (String name : currUser.pictures.get(currPhoto).tags.keySet()) {
			for (String val : currUser.pictures.get(currPhoto).tags.get(name)) {
				displayTag(name, val);
			}
		}
	}
	
	/**
	* Method to display a tag and select it if clicked.
	* @param name The tag name to display.
	* @param val The tag value to display.
	*/
	public void displayTag(String name, String val) {
		Label pair = new Label(name + ": " + val);
		pair.setContentDisplay(ContentDisplay.TOP);
		pair.setOnMouseClicked(e->{
			tagPairs.getChildren().forEach((c)->{
				((Label)c).setBorder(new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			});
			pair.setBorder(new Border(new BorderStroke(Color.SKYBLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			currName = name;
			currVal = val;
		});
		pair.setBorder(new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		tagPairs.getChildren().add(pair);
	}
	
	/**
	* Method to return to the previous screen.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void goBack(ActionEvent e) throws Exception {
		sceneManager.switchScene("Album_Display_Window.fxml", users);
	}
	
	/**
	* Method to return to the login screen.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml", users);
	}
	
	/**
	* Method to display the previous photo in this album.
	* @param e The event that triggered this method.
	*/
	@FXML
	private void displayPrevious(ActionEvent e) {
		int index = currUser.albums.get(currAlbum).indexOf(currPhoto);
		if (index == 0) {
			return;
		}
		currPhoto = currUser.albums.get(currAlbum).get(index - 1);
		displaySingle();
	}
	
	/**
	* Method to display the next photo in this album.
	* @param e The event that triggered this method.
	*/
	@FXML
	private void displayNext(ActionEvent e) {
		int index = currUser.albums.get(currAlbum).indexOf(currPhoto);
		if (index == (currUser.albums.get(currAlbum).size() - 1)) {
			return;
		}
		currPhoto = currUser.albums.get(currAlbum).get(index + 1);
		displaySingle();
	}
	
	/**
	* Method to edit a photo's caption.
	* @param cap The new caption.
	*/
	public void editCaption(String cap) {
		currUser.pictures.get(currPhoto).caption = cap;
		displaySingle();
	}
	
	/**
	* Method to show the edit caption popup.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void showEditCaptionPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Edit_Caption_Popup.fxml", this);
	}
	
	/**
	* Method to add a tag to this photo.
	* @param tagName The tag name to add.
	* @param tagVal The tag value to add.
	* @return -1 if tag-value pair exists, 1 if tag type is single and another value exists, 0 if successful
	*/
	public int addTag(String tagName, String tagVal) {
		if (currUser.pictures.get(currPhoto).tags.containsKey(tagName)) { //if photo already uses this tag name
			if (currUser.tagnames.get(tagName)) { //if tag type allows only one value per photo
				return 1;
			}
			if (currUser.pictures.get(currPhoto).tags.get(tagName).contains(tagVal)) { //if photo already has this tag-value pair
				return -1;
			}
			currUser.pictures.get(currPhoto).tags.get(tagName).add(tagVal);
			displaySingle();
			return 0;
		}
		currUser.pictures.get(currPhoto).tags.put(tagName, new ArrayList<String>());
		currUser.pictures.get(currPhoto).tags.get(tagName).add(tagVal);
		displaySingle();
		return 0;
	}
	
	/**
	* Method to show the add tag popup.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void showAddTagPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Add_Tag_Popup.fxml", this);
	}
	
	/**
	* Method to delete a tag from this photo.
	* @param e The event that triggered this method.
	*/
	@FXML
	private void deleteTag(ActionEvent e) {
		if (currName.trim().isEmpty()) {
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Are you sure you want to delete the selected tag?", 
				ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> confirm = alert.showAndWait();
		if (confirm.get() == ButtonType.NO) return; 
		
		currUser.pictures.get(currPhoto).tags.get(currName).remove(currVal);
		if (currUser.pictures.get(currPhoto).tags.get(currName).isEmpty()) {
			currUser.pictures.get(currPhoto).tags.remove(currName);
		}
		displaySingle();
		currName = "";
		currVal = "";
	}
	
	/**
	* Method to copy the photo to another album.
	* @param otherAlbum The album that the photo will be copied to.
	* @return False if other album already contains the photo, true otherwise.
	* @throws Exception
	*/
	public boolean copyPhoto(String otherAlbum) throws Exception {
		if (currUser.albums.get(otherAlbum).contains(currPhoto)) {
			return false;
		}
		currUser.albums.get(otherAlbum).add(currPhoto);
		setCurrAlbum(otherAlbum);
		sceneManager.switchScene("Album_Display_Window.fxml", users);
		return true;
	}
	
	/**
	* Method to show the copy photo popup.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void showCopyPhotoPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Copy_Photo_Popup.fxml", this);
	}
	
	/**
	* Method to move the photo to another album.
	* @param otherAlbum The album that the photo will be moved to.
	* @return False if other album already contains the photo, true otherwise.
	* @throws Exception
	*/
	public boolean movePhoto(String otherAlbum) throws Exception {
		if (currUser.albums.get(otherAlbum).contains(currPhoto)) {
			return false;
		}
		currUser.albums.get(otherAlbum).add(currPhoto);
		currUser.albums.get(currAlbum).remove(currPhoto);
		setCurrAlbum(otherAlbum);
		sceneManager.switchScene("Album_Display_Window.fxml", users);
		return true;
	}
	
	/**
	* Method to show the move photo popup.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void showMovePhotoPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Move_Photo_Popup.fxml", this);
	}
}
