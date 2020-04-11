package photos.view;

import java.io.File;
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

public class AlbumDisplayController extends Controller {
	@FXML private Label albumName;
	@FXML private Button backButton;
	@FXML private Button logoutButton;
	@FXML private Button searchButton;
	@FXML private Button newTagButton;
	@FXML public Button addPhotoButton;
	@FXML private FlowPane previews;
	@FXML private Button viewButton;
	@FXML private Button removeButton;
	
	//field acting as the selection model
	public static Label selected; 
	
	public void displayPhotos() {
		previews.getChildren().clear();
		previews.setOnMouseClicked(e->{
			viewButton.requestFocus();
		});
		currUser.albums.get(currAlbum).forEach((k)->{
			Image image = new Image(k, 166, 166, false ,false);
			ImageView newImage = new ImageView(image);
			
			String cap = currUser.pictures.get(k).caption;
			displayPhoto(cap, newImage, k);
		});
	}
	
	public void displayPhoto(String cap, ImageView image, String filePath) {
		if (cap.length() > 26) cap = cap.substring(0, 26) + "...";
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
	
	public boolean addNewPhoto(File file) {
		//first, add to the current user's album, if it doesn't exist already
		if (currUser.albums.get(currAlbum).contains(file.toURI().toString())) {
			return false;
		}
		currUser.albums.get(currAlbum).add(file.toURI().toString());
		Image image = new Image(file.toURI().toString(), 166, 166, false ,false); 
		ImageView newImage = new ImageView(image);
		displayPhoto("", newImage, file.toURI().toString());
				
		//then, add to the current user's pictures if it doesn't exist already
		if (currUser.pictures.keySet().contains(file.toURI().toString())) {
			return true;
		}
		currUser.pictures.put(file.toURI().toString(), new Picture(file));
		return true;
	}
	
	public void searchAlbum() {
	}
	
	public void addTag(String tag) {
		currUser.tagnames.add(tag);
	}
	
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
	@FXML
	private void goBack(ActionEvent e) throws Exception {
		sceneManager.switchScene("My_Albums_Window.fxml", users);
	}

	@FXML 
	private void showNewPhotoPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_Photo_Popup.fxml", this);
	}

	@FXML private void showNewTagPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_Tag_Popup.fxml", this);
	}
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml", users);
	}
}
