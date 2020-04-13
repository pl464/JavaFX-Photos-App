package photos.view;

/**
* @author Lance Luo
* @author Patrick Lee
* Class to represent the new user popup.
*/
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NewUserController {
	
	@FXML private TextField username;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	/**
	 * The calling controller.
	 */
	AdminController adminController;
	
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
