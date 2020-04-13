package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
* @author Lance Luo
* @author Patrick Lee
* Class to represent the new album popup.
*/
public class NewAlbumController {
	
	@FXML private TextField albumName;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	/**
	 * The calling controller if called from the albums window.
	 */
	MyAlbumsController myAlbumsController;
	/**
	 * The calling controller if called from the search window.
	 */
	SearchController searchController;
	
	/**
	* Method to set the calling controller if called from the albums window.
	* @param albumsController The controller that called this class.
	*/
	public void setParentController(MyAlbumsController albumsController) {
		this.myAlbumsController = albumsController;
		return;
	}
	
	/**
	* Method to set the calling controller if called from the search window.
	* @param searchController The controller that called this class.
	*/
	public void setParentController(SearchController searchController) {
		this.searchController = searchController;
		return;
	}
	
	/**
	* Method to call addAlbum in the parent controller.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void addAlbum(ActionEvent e) throws Exception {
		if (albumName.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Album name cannot be blank.");
			alert.showAndWait();
			return;
		}
		if (searchController != null && searchController.addAlbum(albumName.getText().trim()) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Album by that name already exists.");
			alert.showAndWait();
			return;
		}
		else if (myAlbumsController != null && myAlbumsController.addAlbum(albumName.getText().trim()) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Album by that name already exists.");
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
