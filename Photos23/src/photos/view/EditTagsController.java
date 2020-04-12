package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import photos.view.MyAlbumsController.Album;

public class EditTagsController {

	@FXML private TableView<String> tagsTable;
	@FXML private TableColumn<String, String> nameColumn;
	@FXML private TableColumn<String, String> valueColumn;
	@FXML private Button doneButton;
	@FXML private Button addButton;
	@FXML private Button removeButton;
	@FXML private MenuButton tagNameButton;
	@FXML private TextField tagValue;
	
	PhotoDisplayController photoDisplayController;
	
	public void setParentController(PhotoDisplayController photoDisplayController) {
		this.photoDisplayController = photoDisplayController;
		return;
	}
	
	@FXML
	private void addTag(ActionEvent e) {
		
	}
	
	@FXML
	private void removeTag(ActionEvent e) {
		
	}
	
	@FXML
	private void closePopup(ActionEvent e) {
		Stage stage = (Stage) doneButton.getScene().getWindow();
	    stage.close();
	}
}
