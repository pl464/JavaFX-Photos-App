package photos.view;
import photos.User;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {
	HashMap<String, User> users;
	SceneManager sceneManager;
	
	public void setSceneManager(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}
	
	/**
	 * Allows user information to be passed into this Controller class from the SceneManager.
	 * @param users
	 */
	public void setUsers (HashMap<String, User> users){
		this.users = users;
		return;
	}

	@FXML 
	private Button loginButton;
	@FXML 
	private TextField username;
	
	@FXML
	private void login(ActionEvent e) throws Exception {
		if (users.containsKey(username.getText())) {
			sceneManager.switchScene("Album_Display.fxml");
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
