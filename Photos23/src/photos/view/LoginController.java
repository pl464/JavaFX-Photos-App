package photos.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController extends Controller{
	@FXML 
	private Button loginButton;
	@FXML 
	private TextField username;
	
	@FXML
	private void login(ActionEvent e) throws Exception {
		if (username.getText().equals("admin")){
			sceneManager.switchScene("Admin_Window.fxml", users);
			return;
		}
		if (users.containsKey(username.getText())) {
			setCurrUser(users.get(username.getText()));
			sceneManager.switchScene("Albums_Window.fxml", users);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Username not found.");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void keyPressed(KeyEvent keyEvent) {
	    if (keyEvent.getCode() == KeyCode.ENTER) {
	        loginButton.fire();
	    }
	}
	
}
