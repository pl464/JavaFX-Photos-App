package photos.view;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import photos.view.MyAlbumsController.Album;

/**
* @author Lance Luo
* Class to represent the add tag popup.
*/
public class AddTagController {

	@FXML private Button cancelButton;
	@FXML private Button okButton;
	@FXML private MenuButton tagMenu;
	@FXML private TextField tagValue;
	
	/**
	 * The calling controller.
	 */
	PhotoDisplayController photoDisplayController;
	/**
	 * The selected menu item.
	 */
	String currTag;
	
	/**
	* Method to set the calling controller.
	* @param photoDisplayController The controller that called this class.
	*/
	public void setParentController(PhotoDisplayController photoDisplayController) {
		this.photoDisplayController = photoDisplayController;
		return;
	}
	
	/**
	* Method to show the list of tags in the menu.
	*/
	public void setTags() {
		HashMap<String, Boolean> tagnames = Controller.currUser.tagnames;
		tagnames.keySet().forEach((tag)->{
			MenuItem item = new MenuItem(tag);
			//set action of each MenuItem in the menu
			item.setOnAction(e->{
				tagMenu.setText(tag);
				currTag = tag;
			});
			tagMenu.getItems().add(item);
		});
	}
	
	/**
	* Method to call addTag in the parent controller.
	* @param e The event that triggered this method.
	*/
	@FXML
	private void addTag(ActionEvent e) {
		if (currTag == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please choose a tag name.");
			alert.showAndWait();
			return;
		}
		if (tagValue.getText().trim().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Tag value cannot be blank.");
			alert.showAndWait();
			return;
		}
		int ret = photoDisplayController.addTag(currTag, tagValue.getText().trim());
		if (ret == 1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Cannot have more than one value for this tag.");
			alert.showAndWait();
			return;
		}
		else if (ret == -1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Tag-value pair already exists.");
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
