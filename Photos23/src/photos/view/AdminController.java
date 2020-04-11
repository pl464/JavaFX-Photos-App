package photos.view;
import photos.User;
import java.util.HashMap;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AdminController extends Controller{
	@FXML
	private Button logoutButton;
	@FXML
	private Button addButton;
	@FXML
	private Button removeButton;
	@FXML
	private ListView<String> userList;
	@FXML
	private TextField username;
	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;
	
	private ObservableList<String> data = FXCollections.observableArrayList();
	private MultipleSelectionModel<String> selectionModel;
	
	/**
	 * Populates the ListView with existing usernames. 
	 * @param users
	 */
	public void displayUsers(HashMap<String, User> users) {
		users.forEach((k,v)->{
			data.add(k);
		});
		userList.getItems().setAll(data);
		return;
	}

	public boolean addUser(String username) throws Exception {
		if (userList.getItems().contains(username) || username.equals("admin")) {
			return false;
		}
		users.put(username, new User());
		data.add(username);
		userList.getItems().setAll(data);
		return true;
	}
	
	@FXML
	private void addPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_User_Popup.fxml", this);
	}
	
	@FXML
	private void removeUser(ActionEvent e) {
		selectionModel = userList.getSelectionModel();
		String username = selectionModel.getSelectedItem();
		if (username == null) {
			return;
		} else if (username.equals("stock")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Cannot delete the stock user.");
			alert.showAndWait();
			return;
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Are you sure you want to delete the selected user?", 
					ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> confirm = alert.showAndWait();
			if (confirm.get() == ButtonType.NO) return; 
		}
		users.remove(username);
		data.remove(username);
		userList.getItems().setAll(data);
	}
	
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml", users);
	}
	
}
