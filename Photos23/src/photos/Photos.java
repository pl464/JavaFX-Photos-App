package photos;

import javafx.application.Application;
import javafx.stage.Stage;
import photos.view.SceneManager;

public class Photos extends Application {
	Stage mainStage;
	
	@Override
	public void start(Stage stage) throws Exception {
		SceneManager sceneManager = new SceneManager(stage);
		sceneManager.switchScene("Login_Window.fxml");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	/*
	@Override
	public void stop() throws Exception {
		ois.close();
	}
	*/
}
