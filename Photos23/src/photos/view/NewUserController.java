package photos.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserController {
	AdminController adminController; //the calling controller 
	
	@FXML
	private TextField username;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	public void setParentController(AdminController adminController) {
		this.adminController = adminController;
		return;
	}
	@FXML
	private void addUser(ActionEvent e) throws Exception {
		adminController.addUser(username.getText());
		cancelButton.fire();
	}
	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    // do what you have to do
	    stage.close();
	}
}
