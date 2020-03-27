package photos.view;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AlbumsController {
	SceneManager sceneManager;
	
	public void setSceneManager(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}
	
	@FXML private Button logoutButton;
	
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml");
	}
}
