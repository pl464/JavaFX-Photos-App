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

public class EditCaptionController {
	
	@FXML private TextArea captionText;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	PhotoDisplayController photoDisplayController;
	
	public void setParentController(PhotoDisplayController photoDisplayController) {
		this.photoDisplayController = photoDisplayController;
		return;
	}
	
	@FXML
	private void editCaption(ActionEvent e) {
		photoDisplayController.editCaption(captionText.getText().trim());
		cancelButton.fire();
	}
	
	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
}
