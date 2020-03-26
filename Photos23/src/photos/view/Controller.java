package photos.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	Stage stage;
	Parent root;
	
	@FXML private Button loginButton;
	@FXML private TextField username;
	
	@FXML
	private void login(ActionEvent e) throws IOException {
		if (username.getText().equals("sample")) {
			stage = (Stage) loginButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("Album_Display.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Your Albums");
			stage.setScene(scene);
			stage.show();
			stage.centerOnScreen();
		}
	}
}
