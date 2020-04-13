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
	//@FXML private Label photoTags;
	@FXML private Button backButton;
	@FXML private Button logoutButton;
	@FXML private Button previousButton;
	@FXML private Button nextButton;
	@FXML private Button captionButton;
	//@FXML private Button tagsButton;
	@FXML private Button addButton;
	@FXML private Button deleteButton;
	@FXML private Button copyButton;
	@FXML private Button moveButton;
	
	//field acting as the selection model
	public static Label selected; 
	
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
		
		if (currUser.pictures.get(currPhoto).tags.isEmpty() == false) {
			//photoTags.setText("(no tags)");
		}
		//else {
			String temp = "";
			/*
			currUser.pictures.get(currPhoto).tags.put("testname", new ArrayList<String>());
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval2");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval3");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval4");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval5");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval6");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval7");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval8");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval9");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval10");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval11");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval12");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval13");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval14");
			currUser.pictures.get(currPhoto).tags.get("testname").add("testval15");
			*/
			for (String name : currUser.pictures.get(currPhoto).tags.keySet()) {
				for (String val : currUser.pictures.get(currPhoto).tags.get(name)) {
					temp = temp.concat("," + name + ": " + val);
					displayTag(name + ": " + val);
				}
			}
			//temp = temp.substring(2);
			//photoTags.setText(temp);
		//}
	}
	
	public void displayTag(String s) {
		Label pair = new Label(s);
		pair.setContentDisplay(ContentDisplay.TOP);
		pair.setOnMouseClicked(e->{
			tagPairs.getChildren().forEach((c)->{
				((Label)c).setBorder(new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			});
			tagPairs.setBorder(new Border(new BorderStroke(Color.SKYBLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
			selected = pair;
			//selected.setUserData(filePath);
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
	
	public void addTag() {
		
	}
	
	public void removeTag() {
		
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
