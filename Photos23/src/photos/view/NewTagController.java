package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NewTagController {
	AlbumsController albumsController; //the calling controller 
	
	@FXML
	private TextField tagname;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	public void setParentController(AlbumsController albumsController) {
		this.albumsController = albumsController;
		return;
	}
	@FXML
	private void addTag(ActionEvent e) throws Exception {
		albumsController.addTag(tagname.getText());
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
