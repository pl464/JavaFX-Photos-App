package photos.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import photos.Picture;

/**
 * The controller class for the Search Window.
 * @author Patrick Lee
 *
 */
public class SearchController extends Controller{
	
	@FXML private RadioButton dateButton;
	@FXML private RadioButton oneTagButton;
	@FXML private RadioButton twoTagButton;
	@FXML private TextField field1;
	@FXML private TextField field2;
	@FXML private RadioButton andButton;
	@FXML private RadioButton orButton;
	@FXML private Button searchButton;
	@FXML private Button createNewAlbumButton;
	@FXML private Button cancelButton;
	@FXML private Label label1;
	@FXML private Label label2;
	@FXML private MenuButton tag1Button;
	@FXML private MenuButton tag2Button;
	@FXML private ToggleGroup searchParamGroup;
	@FXML private ToggleGroup booleanGroup;
	@FXML private FlowPane previews;
	
	/**
	 * A String storing the name of the tag currently selected by the Tag1 MenuButton.
	 */
	String currTag1;
	/**
	 * A String storing the name of the tag currently selected by the Tag2 MenuButton.
	 */
	String currTag2;
	/**
	 * An ArrayList storing the file paths of the photos from the search.
	 */
	static ArrayList<String> results;
	
	/**
	 * Sets the MenuItems for the list of tags to be used in the search.
	 */
	public void setTags() {
		HashMap<String, Boolean> tagnames = Controller.currUser.tagnames;
		tagnames.keySet().forEach((tag)->{
			MenuItem item1 = new MenuItem(tag);
			MenuItem item2 = new MenuItem(tag);
			//set action of each MenuItem in the menu
			item1.setOnAction(e->{
				tag1Button.setText(tag);
				currTag1 = tag;
			});
			item2.setOnAction(e->{
				tag2Button.setText(tag);
				currTag2 = tag;
			});
			tag1Button.getItems().add(item1);
			tag2Button.getItems().add(item2);
		});
	}
	/**
	 * Searches for photos in the given scope given the parameters selected by the user.
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void search(ActionEvent e) {
		results = new ArrayList<>();
		previews.getChildren().clear();
		
		if (dateButton.isSelected()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate date1 = LocalDate.now(); //initialize dates
			LocalDate date2 = LocalDate.now();
			try {
				date1 = LocalDate.parse(field1.getText(), formatter);
				date2 = LocalDate.parse(field2.getText(), formatter);
			} catch (DateTimeParseException ex) {
				Alert alert = new Alert(AlertType.ERROR, "Incorrect Date Format (MM/dd/yyyy)", 
						ButtonType.OK);
				alert.showAndWait();
				return;
			}
			LocalDateTime LDT1 = date1.atStartOfDay();
			LocalDateTime LDT2 = date2.atTime(23, 59, 59);
			results = searchByDate(LDT1, LDT2, albumScope);
		} 
		
		else if (oneTagButton.isSelected()) {
			if (tag1Button.getText().equals("Tag1") || field1.getText().length() == 0) {
				Alert alert = new Alert(AlertType.ERROR, "Missing Tag information", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			String value = field1.getText();
			results = searchByTag(currTag1, value, albumScope);
		}

		else if (twoTagButton.isSelected()) {
			if (tag1Button.getText().equals("Tag1") || field1.getText().length() == 0 ||
					tag2Button.getText().equals("Tag2") || field2.getText().length() == 0) {
				Alert alert = new Alert(AlertType.ERROR, "Missing Tag information", ButtonType.OK);
				alert.showAndWait();
				return;
			}
			if (andButton.isSelected()) {
				results = searchByAnd(tag1Button.getText(), field1.getText(), tag2Button.getText(), field2.getText(), albumScope);
			} else if (orButton.isSelected()) {
				results = searchByOr(tag1Button.getText(), field1.getText(), tag2Button.getText(), field2.getText(), albumScope);
			}
		}
		
		if (results.size() == 0) {
			Alert alert = new Alert(AlertType.INFORMATION, "No results found.", ButtonType.OK);
			alert.showAndWait();
			return;
		}
		
		//display results in the FlowPane
		results.forEach((filePath)->{
			Image image = new Image(filePath, 112, 0, true ,false);
			ImageView newImage = new ImageView(image);
			String cap = currUser.pictures.get(filePath).caption;
			displayPhoto(cap, newImage, filePath);
		});
	}
	/**
	 * Searches for photos based off dates specified by the user.
	 * @param start e starting date specified by the user.
	 * @param end The end date specified by the user.
	 * @param single Indicates whether the search is performed on a single album or not.
	 * @return An ArrayList of file paths of the photos matching the search parameters.
	 */
	public ArrayList<String> searchByDate(LocalDateTime start, LocalDateTime end, boolean single) {
		previews.getChildren().clear();
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Picture> e : currUser.pictures.entrySet()) {
			if (single && !currUser.albums.get(currAlbum).contains(e.getKey())) {
				continue;
			}
			if (e.getValue().date.compareTo(start) >= 0 && e.getValue().date.compareTo(end) <= 0) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
	/**
	 * Searches for photos based off a single tag and tag value specified by the user.
	 * @param tag The tag specified by the user.
	 * @param val The value of the tag specified by the user.
	 * @param single Indicates whether the search is performed on a single album or not.
	 * @return An ArrayList of file paths of the photos matching the search parameters.
	 */
	public ArrayList<String> searchByTag(String tag, String val, boolean single) {
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Picture> e : currUser.pictures.entrySet()) {
			if (single && !currUser.albums.get(currAlbum).contains(e.getKey())) {
				continue;
			}
			if (e.getValue().tags.containsKey(tag) && e.getValue().tags.get(tag).contains(val)) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
	
	/**
	 * Searches for photos based off whether they match two tags and their tag values specified by the user.
	 * @param tag1 The first tag specified by the user.
	 * @param val1 The value of the first tag specified by the user.
	 * @param tag2 The second tag specified by the user.
	 * @param val2 The value of the first tag specified by the user.
	 * @param single Indicates whether the search is performed on a single album or not.
	 * @return An ArrayList of file paths of the photos matching the search parameters.
	 */
	public ArrayList<String> searchByAnd(String tag1, String val1, String tag2, String val2, boolean single) {
		
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Picture> e : currUser.pictures.entrySet()) {
			if (single && !currUser.albums.get(currAlbum).contains(e.getKey())) {
				continue;
			}
			if (e.getValue().tags.containsKey(tag1) && e.getValue().tags.containsKey(tag2) &&
					e.getValue().tags.get(tag1).contains(val1) && e.getValue().tags.get(tag2).contains(val2)) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
	
	/**
	 * Searches for photos based off whether they match either of two tags and their values specified by the user. 
	 * @param tag1 The first tag specified by the user.
	 * @param val1 The value of the first tag specified by the user.
	 * @param tag2 The second tag specified by the user.
	 * @param val2 The value of the first tag specified by the user.
	 * @param single Indicates whether the search is performed on a single album or not.
	 * @return An ArrayList of file paths of the photos matching the search parameters.
	 */
	public ArrayList<String> searchByOr(String tag1, String val1, String tag2, String val2, boolean single) {
		
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Picture> e : currUser.pictures.entrySet()) {
			if (single && !currUser.albums.get(currAlbum).contains(e.getKey())) {
				continue;
			}
			if ((e.getValue().tags.containsKey(tag1) && e.getValue().tags.get(tag1).contains(val1)) ||
					(e.getValue().tags.containsKey(tag2)) && e.getValue().tags.get(tag2).contains(val2)) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
	
	/**
	 * Displays a single photo given its caption, Image object and file path.
	 * @param cap The caption of the photo to be displayed.
	 * @param image The Image object associated with the photo.
	 * @param filePath The file path of the photo.
	 */
	public void displayPhoto(String cap, ImageView image, String filePath) {
		if (cap.length() > 12) cap = cap.substring(0, 12) + "...";
		else if (cap.length() == 0) cap = "(no caption)";
		
		Label preview = new Label(cap, image);
		preview.setContentDisplay(ContentDisplay.TOP);
		preview.setBorder(new Border(new BorderStroke(new Color(0,0,0,0), BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		previews.getChildren().add(preview);
	}
	
	/**
	 * Creates a new album from the search results with the specified album name.
	 * @param albumName The name of the album to be created.
	 * @return True if the album is created successfully, false otherwise (if the album name already existed).
	 * @throws Exception
	 */
	public boolean addAlbum(String albumName) throws Exception {
		if (currUser.albums.keySet().contains(albumName)) {
			return false;
		}
		ArrayList<String> newAlbumResults = results;
		currUser.albums.put(albumName, newAlbumResults);
		sceneManager.switchScene("My_Albums_Window.fxml", users);
		return true;
	}
	
	/**
	 * Shows the appropriate UI elements for the date search.
	 * @param e The event that triggered this method.
	 */
	@FXML 
	private void displayDateSearch(ActionEvent e) {
		label1.setVisible(true);
		label2.setVisible(true);
		tag1Button.setVisible(false);
		tag2Button.setVisible(false);
		field1.setText("Date (MM/DD/YYYY)");
		field2.setText("Date (MM/DD/YYYY)");
		field2.setVisible(true);
		andButton.setVisible(false);
		orButton.setVisible(false);
	}
	/**
	 * Shows the appropriate UI elements for the single-tag search.
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void displayOneTagSearch(ActionEvent e) {
		label1.setVisible(false);
		label2.setVisible(false);
		tag1Button.setVisible(true);
		tag2Button.setVisible(false);
		field1.setText("Tag_Value");
		field2.setVisible(false);
		andButton.setVisible(false);
		orButton.setVisible(false);
	}
	/**
	 * Shows the appropriate UI elements for the two-tag search.
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void displayTwoTagSearch(ActionEvent e) {
		label1.setVisible(false);
		label2.setVisible(false);
		tag1Button.setVisible(true);
		tag2Button.setVisible(true);
		field1.setText("Tag_Value");
		field2.setVisible(true);
		field2.setText("Tag_Value");
		andButton.setVisible(true);
		orButton.setVisible(true);
	}
	/**
	 * Shows the popup window for creating a new album.
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void displayNewAlbumPopup(ActionEvent e) throws Exception {
		if (results == null || results.size() == 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Cannot create an album without search results.");
			alert.showAndWait();
			return;
		}
		sceneManager.openScene("New_Album_Popup.fxml", this);
	}
	/**
	 * Sets a TextField to an empty string once the user clicks on it.
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void setFieldEmpty(MouseEvent e) {
		if (((TextField) e.getSource()).getText().equals("Date (MM/DD/YYYY)") ||
				((TextField) e.getSource()).getText().equals("Tag_Value")) {
			((TextField) e.getSource()).setText("");
		}
	}
	/**
	 * Switches the scene to the previous window that called it.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML
	private void goBack(ActionEvent e) throws Exception {
		if (albumScope == true) {
			sceneManager.switchScene("Album_Display_Window.fxml", users);
		} else {
			sceneManager.switchScene("My_Albums_Window.fxml", users);
		}
	}
}
