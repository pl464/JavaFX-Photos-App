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
	
	@FXML
	private TextField username;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	/**
	 * Sets the parent controller to be referenced once this window is done.
	 * @param adminController
	 */
	public void setParentController(AdminController adminController) {
		this.adminController = adminController;
		return;
	}
	/**
	 * Handles the calling of addUser() in the parent controller.
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
	 * Closes this scene and returns control to the parent scene. 
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
}
