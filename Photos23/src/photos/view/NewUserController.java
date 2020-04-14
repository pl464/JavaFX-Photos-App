package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * The controller class of the New User Pop window.
 * @author Patrick Lee
 *
 */
public class NewUserController {
	/**
	 * The parent (calling) controller to be referenced once this window is done.
	 */
	AdminController adminController; //the calling controller 
	
	@FXML private TextField username;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	/**
	* Method to set the calling controller.
	* @param adminController The controller that called this class.
	*/
	public void setParentController(AdminController adminController) {
		this.adminController = adminController;
		return;
	}
	
	/**
	* Method to call addUser in the parent controller.
	* @param e The event that triggered this method.
	* @throws Exception
	*/
	@FXML
	private void addUser(ActionEvent e) throws Exception {
		if (username.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Username cannot be blank.");
			alert.showAndWait();
			return;
		}
		if (adminController.addUser(username.getText().trim()) == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("User by that name already exists.");
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
