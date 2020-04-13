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

public class NewAlbumController {
	MyAlbumsController myAlbumsController;
	SearchController searchController;
	
	@FXML
	private TextField albumName;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	public void setParentController(MyAlbumsController albumsController) {
		this.myAlbumsController = albumsController;
		return;
	}
	
	public void setParentController(SearchController searchController) {
		this.searchController = searchController;
		return;
	}
	
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
