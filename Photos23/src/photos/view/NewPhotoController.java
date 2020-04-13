package photos.view;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photos.Picture;

public class NewPhotoController {

	@FXML private Button browseButton;
	@FXML private TextField filePath;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	AlbumDisplayController albumDisplayController;
	File currPicture;
	
	public void setParentController(AlbumDisplayController albumDisplayController) {
		this.albumDisplayController = albumDisplayController;
		this.filePath.setEditable(false);
		return;
	}
	
	@FXML
	private void chooseFile(ActionEvent e) throws MalformedURLException {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg, *.png)", "*.jpg", ".png");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(browseButton.getScene().getWindow());
		if (file == null) return;
		currPicture = file;
		filePath.setText(file.getAbsolutePath());
	}
	
	@FXML
	private void createPicture(ActionEvent e) {
		if (currPicture == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please choose a file.");
			alert.showAndWait();
			return;
		}
		if (albumDisplayController.addNewPhoto(currPicture) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error: This picture is already in the album!");
			alert.showAndWait();
			return;
		}
		cancelButton.fire();
	}

	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
	@FXML
	private void keyPressed(KeyEvent keyEvent) {
	    if (keyEvent.getCode() == KeyCode.ENTER) {
	        okButton.fire();
	    }
	}
}
