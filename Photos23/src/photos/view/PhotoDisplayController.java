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

public class PhotoDisplayController extends Controller {
	
	@FXML private ImageView photo;
	@FXML private Label photoDate;
	@FXML private Label photoCaption;
	@FXML private Label photoTags;
	@FXML private Button backButton;
	@FXML private Button logoutButton;
	@FXML private Button previousButton;
	@FXML private Button nextButton;
	@FXML private Button captionButton;
	@FXML private Button tagsButton;
	@FXML private Button copyButton;
	@FXML private Button moveButton;
	
	public void displaySingle() {
		
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
		
	}
	
	@FXML
	private void displayNext(ActionEvent e) {
		
	}
	
	@FXML
	private void showEditCaptionPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Edit_Caption_Popup.fxml", this);
	}
	
	@FXML
	private void showEditTagsPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Edit_Tags_Popup.fxml", this);
	}
	
	@FXML
	private void showCopyPhotoPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Copy_Photo_Popup.fxml", this);
	}
	
	@FXML
	private void showMovePhotoPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("Move_Photo_Popup.fxml", this);
	}
}
