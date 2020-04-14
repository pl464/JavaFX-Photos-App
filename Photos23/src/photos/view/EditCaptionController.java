package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
* @author Lance Luo
* Class to represent the edit caption popup.
*/
public class EditCaptionController {
	
	@FXML private TextField captionText;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	/**
	 * The calling controller.
	 */
	PhotoDisplayController photoDisplayController;
	
	/**
	* Method to set the calling controller.
	* @param photoDisplayController The controller that called this class.
	*/
	public void setParentController(PhotoDisplayController photoDisplayController) {
		this.photoDisplayController = photoDisplayController;
		return;
	}
	
	/**
	* Method to call editCaption in the parent controller.
	* @param e The event that triggered this method.
	*/
	@FXML
	private void editCaption(ActionEvent e) {
		photoDisplayController.editCaption(captionText.getText().trim());
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
