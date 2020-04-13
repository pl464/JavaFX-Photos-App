package photos.view;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	public static String currName = "";
	public static String currVal = "";
	
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
	
	@FXML
	private void goBack(ActionEvent e) throws Exception {
		sceneManager.switchScene("Album_Display_Window.fxml", users);
	}
	
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml", users);
	}
	
	@FXML
	private void displayPrevious(ActionEvent e) {
		int index = currUser.albums.get(currAlbum).indexOf(currPhoto);
		if (index == 0) {
			return;
		}
		currPhoto = currUser.albums.get(currAlbum).get(index - 1);
		displaySingle();
	}
	
	@FXML
	private void displayNext(ActionEvent e) {
		int index = currUser.albums.get(currAlbum).indexOf(currPhoto);
		if (index == (currUser.albums.get(currAlbum).size() - 1)) {
			return;
		}
		currPhoto = currUser.albums.get(currAlbum).get(index + 1);
		displaySingle();
	}
	
	public void editCaption(String s) {
		currUser.pictures.get(currPhoto).caption = s;
		displaySingle();
	}
	
	@FXML
	private void showEditCaptionPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Edit_Caption_Popup.fxml", this);
	}
	
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
	
	@FXML
	private void showAddTagPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Add_Tag_Popup.fxml", this);
	}
	
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
	
	public boolean copyPhoto(String otherAlbum) throws Exception {
		if (currUser.albums.get(otherAlbum).contains(currPhoto)) {
			return false;
		}
		currUser.albums.get(otherAlbum).add(currPhoto);
		setCurrAlbum(otherAlbum);
		sceneManager.switchScene("Album_Display_Window.fxml", users);
		return true;
	}
	
	@FXML
	private void showCopyPhotoPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Copy_Photo_Popup.fxml", this);
	}
	
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
	
	@FXML
	private void showMovePhotoPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Move_Photo_Popup.fxml", this);
	}
}
