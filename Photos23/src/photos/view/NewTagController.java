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

/**
* @author Lance Luo
* @author Patrick Lee
* Class to represent the new tag popup.
*/
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

	/**
	 * Sets the parent controller to be referenced once this window is done.
	 * @param albumsController The controller to be set.
	 */
	@FXML private TextField tagname;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	@FXML private ToggleGroup tagTypeGroup;
	@FXML private RadioButton oneButton;
	@FXML private RadioButton severalButton;
	
	/**
	* Method to set the calling controller if called from the albums window.
	* @param albumsController The controller that called this class.
	*/
	public void setParentController(MyAlbumsController albumsController) {
		this.myAlbumsController = albumsController;
		return;
	}
	
	/**
	* Method to set the calling controller if called from the album display window.
	* @param albumDisplayController The controller that called this class.
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
		if (myAlbumsController != null && myAlbumsController.addTag(tagname.getText().trim(), oneButton.isSelected()) == false) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Tag by that name already exists.");
				alert.showAndWait();
				return;
		}
		else if (albumDisplayController != null && albumDisplayController.addTag(tagname.getText().trim(), oneButton.isSelected()) == false) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Tag by that name already exists.");
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
