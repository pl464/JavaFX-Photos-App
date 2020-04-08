package photos.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class AlbumDisplayController extends Controller{
	@FXML
	private Label albumName;
	@FXML
	private Button backButton;
	@FXML
	private Button logoutButton;
	@FXML
	private Button searchButton;
	@FXML
	private Button newTagButton;
	@FXML 
	private FlowPane previews;
	
	public void addToPane() {
		String s = getClass().getName();
		int i = s.lastIndexOf(".");
		if(i > -1) s = s.substring(i + 1);
		s = s + ".class";
		System.out.println("name " +s);
		Object testPath = this.getClass().getResource(s);
		System.out.println(testPath);
		albumName.setText(currAlbum);
		Image image = new Image("file:/../data/blathers.jpg");
		Image image2 = new Image("file:/../data/kkslider.jpg");
		ImageView newImage = new ImageView(image);
		ImageView newImage2 = new ImageView(image2);
		previews.getChildren().add(newImage);
		previews.getChildren().add(newImage2);
	}
}
