package photos.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	    stage.close();
	}
	@FXML
	private void keyPressed(KeyEvent keyEvent) {
	    if (keyEvent.getCode() == KeyCode.ENTER) {
	        okButton.fire();
	    }
	}
}
