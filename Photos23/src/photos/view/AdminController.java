package photos.view;
import photos.User;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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

	public void addUser(String username) throws Exception {
		users.put(username, new User());
		data.add(username);
		userList.getItems().setAll(data);
		
		//ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
		//oos.writeObject(users);
		//oos.close();
	}
	
	@FXML
	private void addPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_User_Popup.fxml", this);
	}
	
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml", users);
	}
	
	
}
