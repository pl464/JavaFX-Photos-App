package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * The controller class for the Login Window. 
 * @author Patrick Lee
 *
 */
public class LoginController extends Controller{

	@FXML private Button loginButton;
	@FXML private TextField username;
	
	/**
	 * Logs the user in, using the username from the TextField. If successful, passes control to the
	 * MyAlbums window of the user.
	 * @param e The event that triggered this event.
	 * @throws Exception
	 */
	@FXML
	private void login(ActionEvent e) throws Exception {
		if (username.getText().equals("admin")){
			sceneManager.switchScene("Admin_Window.fxml", users);
			return;
		}
		if (users.containsKey(username.getText())) {
			setCurrUser(users.get(username.getText()));
			sceneManager.switchScene("My_Albums_Window.fxml", users);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Username not found.");
			alert.showAndWait();
		}
	}
}
