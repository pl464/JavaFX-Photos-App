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
/**
 * The controller class for Admin_Window.fxml.
 * @author Patrick Lee
 *
 */
public class AdminController extends Controller{
	@FXML private Button logoutButton;
	@FXML private Button addButton;
	@FXML private Button removeButton;
	@FXML private ListView<String> userList;
	@FXML private TextField username;
	@FXML private Button okButton;
	@FXML private Button cancelButton;
	
	/**
	 * The ObservableList storing the Strings in the userList. 
	 */
	private ObservableList<String> data = FXCollections.observableArrayList();
	/**
	 * The SelectionModel for the userList.
	 */
	private MultipleSelectionModel<String> selectionModel;
	/**
	 * Populates the ListView with existing usernames. 
	 * @param users The HashMap of users containing usernames to be displayed.
	 */
	public void displayUsers(HashMap<String, User> users) {
		users.forEach((k,v)->{
			data.add(k);
		});
		userList.getItems().setAll(data);
		return;
	}
	/**
	 * Adds a new User to the list of users with the given username, and displays this in the ListView.
	 * @param username The username of the user to be added.
	 * @return True if the user was added successfully, false otherwise (if the username already existed).
	 * @throws Exception
	 */
	public boolean addUser(String username) throws Exception {
		if (users.keySet().contains(username) || username.equals("admin")) {
			return false;
		}
		users.put(username, new User());
		data.add(username);
		userList.getItems().setAll(data);
		return true;
	}
	/**
	 * Displays the "popup" UI for adding a new user.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML
	private void addPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_User_Popup.fxml", this);
	}
	/**
	 * Removes the selected user from the list of Users, and displays this in the ListView.
	 * @param e The event that triggered this method.
	 */
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
	/**
	 * Switches the main scene to the Login Window.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml", users);
	}
	
}
