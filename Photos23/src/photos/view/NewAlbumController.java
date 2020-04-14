package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * The controller class for the New Album Popup window.
 * @author Patrick Lee
 *
 */
public class NewAlbumController {
	/**
	 * The parent (calling) controller to be referenced once this window is done. If the My Albums Window
	 * is the parent controller, this controller will be set to the myAlbumsController.
	 */
	MyAlbumsController myAlbumsController;
	/**
	 * The parent (calling) controller to be referenced once this window is done. If the Search Window
	 * is the parent controller, this controller will be set to the searchController.
	 */
	SearchController searchController;

	@FXML
	private TextField albumName;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	/**
	 * Sets the parent controller to be referenced once this window is done.
	 * @param albumsController The controller to be set.
	 */
	public void setParentController(MyAlbumsController albumsController) {
		this.myAlbumsController = albumsController;
		return;
	}
	/**
	 * Sets the parent controller to be referenced once this window is done.
	 * @param searchController The controller to be set.
	 */
	public void setParentController(SearchController searchController) {
		this.searchController = searchController;
		return;
	}
	/**
	 * Handles the calling of addAlbum() in the parent controller.
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
	 * Closes this scene and returns control to the parent scene. 
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
}
