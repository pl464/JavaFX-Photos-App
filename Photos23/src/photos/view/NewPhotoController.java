package photos.view;

import java.io.File;
import java.net.MalformedURLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The controller class for the New Photo Popup window. 
 * @author Patrick Lee
 *
 */
public class NewPhotoController {
	
	@FXML private Button browseButton;
	@FXML private TextField filePath;
	@FXML private Button okButton;
	@FXML private Button cancelButton;

	/**
	 * The calling controller.
	 */
	AlbumDisplayController albumDisplayController;
	/**
	 * The selected photo file.
	 */
	File currPicture;
	
	/**
	 * Sets the parent controller to be referenced once this window is done.
	 * @param albumDisplayController The controller to be set.
	 */
	public void setParentController(AlbumDisplayController albumDisplayController) {
		this.albumDisplayController = albumDisplayController;
		this.filePath.setEditable(false);
		return;
	}
	/**
	 * Sets the currently selected File to the file chosen using the FileChooser in this window.
	 * @param e The event that triggered this event.
	 * @throws Exception
	 */
	@FXML
	private void chooseFile(ActionEvent e) throws Exception {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg, *.png)", "*.jpg", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(browseButton.getScene().getWindow());
		if (file == null) return;
		currPicture = file;
		filePath.setText(file.getAbsolutePath());
	}
	/**
	 * Handles the calling of addNewPhoto() in the parent controller.
	 * @param e The event that triggered this event.
	 */
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

	/**
	* Method to close the popup.
	* @param e The event that triggered this method.
	*/
	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
}
