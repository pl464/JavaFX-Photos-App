package photos.view;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photos.Picture;

public class MovePhotoController {

	@FXML private MenuButton chooseButton;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	PhotoDisplayController photoDisplayController;
	
	public void setParentController(PhotoDisplayController photoDisplayController) {
		this.photoDisplayController = photoDisplayController;
		return;
	}
	
	@FXML
	private void movePhoto(ActionEvent e) {
		
	}
	
	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
}
