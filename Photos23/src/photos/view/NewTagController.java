package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class NewTagController {
	MyAlbumsController myAlbumsController; //the calling controller 
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
	
	public void setParentController(MyAlbumsController albumsController) {
		this.myAlbumsController = albumsController;
		return;
	}
	public void setParentController(AlbumDisplayController albumDisplayController) {
		this.albumDisplayController = albumDisplayController;
		return;
	}
	
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
	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
}
