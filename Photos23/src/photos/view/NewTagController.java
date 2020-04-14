package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewTagController {
	/**
	 * The parent (calling) controller to be referenced once this window is done. If the My Albums Window
	 * is the parent controller, this controller will be set to the myAlbumsController.
	 */
	MyAlbumsController myAlbumsController; 
	/**
	 * The parent (calling) controller to be referenced once this window is done. If the Album Display Window
	 * is the parent controller, this controller will be set to the albumDisplayController.
	 */
	AlbumDisplayController albumDisplayController;
	
	@FXML
	private TextField tagname;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	@FXML
	private ToggleGroup tagTypeGroup;
	@FXML
	private RadioButton oneButton;
	@FXML
	private RadioButton severalButton;
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
	 * @param albumsController The controller to be set.
	 */
	public void setParentController(AlbumDisplayController albumDisplayController) {
		this.albumDisplayController = albumDisplayController;
		return;
	}
	/**
	 * Handles the calling of addTag() in the parent controller.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML
	private void addTag(ActionEvent e) throws Exception {
		if (tagname.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Tag name cannot be blank.");
			alert.showAndWait();
			return;
		}
		if (myAlbumsController != null) {
			if (myAlbumsController.addTag(tagname.getText(), oneButton.isSelected()) == false) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Tag by that name already exists.");
				alert.showAndWait();
				return;
			}
		}
		else {
			if (albumDisplayController.addTag(tagname.getText(), oneButton.isSelected()) == false) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Tag by that name already exists.");
				alert.showAndWait();
				return;
			}
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
