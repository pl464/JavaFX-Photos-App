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

/**
* @author Lance Luo
* Class to represent the move photo popup.
*/
public class MovePhotoController {

	@FXML private MenuButton albumMenu;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	/**
	 * The calling controller.
	 */
	PhotoDisplayController photoDisplayController;
	/**
	 * The selected menu item.
	 */
	String chosenAlbum;
	
	/**
	* Method to set the calling controller.
	* @param photoDisplayController The controller that called this class.
	*/
	public void setParentController(PhotoDisplayController photoDisplayController) {
		this.photoDisplayController = photoDisplayController;
		return;
	}
	
	/**
	* Method to show the list of albums in the menu.
	*/
	public void setAlbums() {
		for (String albumName : Controller.currUser.albums.keySet()) {
			MenuItem item = new MenuItem(albumName);
			item.setOnAction(e->{
				albumMenu.setText(albumName);
				chosenAlbum = albumName;
			});
			albumMenu.getItems().add(item);
		}
	}
	
	/**
	* Method to call movePhoto in the parent controller.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void movePhoto(ActionEvent e) throws Exception {
		if (chosenAlbum == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please choose an album.");
			alert.showAndWait();
			return;
		}
		if (photoDisplayController.movePhoto(chosenAlbum) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Chosen album already has this photo.");
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
