package photos.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The controller class for the My Albums window (which displays the name of the albums of the current user).
 * @author Patrick Lee
 *
 */
public class MyAlbumsController extends Controller{
	@FXML private Button logoutButton;
	@FXML private Button searchButton;
	@FXML private Button newTagButton;
	@FXML private Button createButton;
	@FXML private Button deleteButton;
	@FXML private Button renameButton;
	@FXML private Button openButton;
	@FXML private TableView<Album> albumTable;
	@FXML private TableColumn<Album, String> nameColumn;
	@FXML private TableColumn<Album, Integer> numPhotosColumn;
	@FXML private TableColumn<Album, String> dateRangeColumn;
	
	/**
	 * The ObservableList used by the TableView displaying album information.
	 */
	private ObservableList<Album> data = FXCollections.observableArrayList();
	/**
	 * The SelectionModel used by the TableView displaying album information.
	 */
	private MultipleSelectionModel<Album> selectionModel;
	
	/**
	 * Displays the current user's albums and album information. 
	 */
	public void displayAlbums() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		numPhotosColumn.setCellValueFactory(new PropertyValueFactory<>("numPhotos"));
		dateRangeColumn.setCellValueFactory(new PropertyValueFactory<>("dateRange"));
		
		//for each album, create a new Album to show in the TableView
		currUser.albums.forEach((albumName,photoList)->{
			Integer numPhotos = photoList.size();
			String dateRange = "No photos";
			//get date range
			if (numPhotos != 0) {
				LocalDateTime leastDate = currUser.pictures.get(photoList.get(0)).date;
				LocalDateTime greatestDate = currUser.pictures.get(photoList.get(0)).date;
			    for (String s : photoList) {
			    	LocalDateTime date = currUser.pictures.get(s).date;
			    	if (date.compareTo(leastDate) < 0) {
			    		leastDate = date;
			    	} else if (date.compareTo(greatestDate) > 0) {
			    		greatestDate = date;
			    	}
			    }
			    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			    String date1 = leastDate.format(format);
			    String date2 = greatestDate.format(format);
			    dateRange = date1 + " - " + date2;
			}
			Album album = new Album(albumName, numPhotos, dateRange);
			data.add(album);
		});
		Collections.sort(data, new NameComp());
		albumTable.setItems(data);
	}
	/**
	 * Adds a tag to the current user's list of tags.
	 * @param tag The tag to be added.
	 * @param single Indicates if the tag is single-value. True if the tag is single-value, and false otherwise.
	 * @return True if the tag was added successfully, false otherwise (if the tag already existed).
	 */
	public boolean addTag(String tag, boolean single) {
		if (currUser.tagnames.containsKey(tag)) {
			return false;
		}
		currUser.tagnames.put(tag, single);
		return true;
	}
	/**
	 * Adds an album to the current user's list of albums, and displays the change.
	 * @param albumName The name of the album to be added.
	 * @return True if the album was added successfully, false otherwise (if the album already existed).
	 */
	public boolean addAlbum(String albumName) {
		if (currUser.albums.keySet().contains(albumName)) {
			return false;
		}
		currUser.albums.put(albumName, new ArrayList<String>());
		//create a new Album for displaying purposes only
		Album newAlbum = new Album(albumName, 0, "No photos");
		data.add(newAlbum);
		Collections.sort(data, new NameComp());
		albumTable.setItems(data);
		return true;
	}
	/**
	 * Renames the selected album, and displays the change.
	 * @param newAlbumName The new name.
	 * @return True if the album was renamed successfully, false otherwise.
	 */
	public boolean renameAlbum(String newAlbumName) {
		if (currUser.albums.keySet().contains(newAlbumName)) {
			return false;
		}
		selectionModel = albumTable.getSelectionModel();
		Album album = selectionModel.getSelectedItem();
		//edit the TableView
		Album newAlbum = new Album(newAlbumName, album.numPhotos, album.dateRange);
		data.remove(album);
		data.add(newAlbum);
		//edit the User's albums
		ArrayList<String> photos = currUser.albums.get(album.name);
		currUser.albums.remove(album.name);
		currUser.albums.put(newAlbumName, photos);
		//display
		Collections.sort(data, new NameComp());
		albumTable.setItems(data);
		return true;
	}
	/**
	 * Removes the selected album from the list of albums, and displays the change.
	 * @param e The event that triggered this method.
	 */
	@FXML
	private void removeAlbum(ActionEvent e) {
		selectionModel = albumTable.getSelectionModel();
		Album album = selectionModel.getSelectedItem();
		if (album == null) {
			return;
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Are you sure you want to delete the selected album?", 
					ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> confirm = alert.showAndWait();
			if (confirm.get() == ButtonType.NO) return; 
		}
		currUser.albums.remove(album.name);
		data.remove(album);
		albumTable.setItems(data);
	}
	/**
	 * Displays the "popup" window for adding a new tag.
	 * @param e
	 * @throws Exception
	 */
	@FXML private void showNewTagPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_Tag_Popup.fxml", this);
	}
	/**
	 * Displays the "popup" window for adding a new window.
	 * @param e
	 * @throws Exception
	 */
	@FXML private void showNewAlbumPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_Album_Popup.fxml", this);
	}
	/**
	 * Displays the "popup" window for renaming an album.
	 * @param e
	 * @throws Exception
	 */
	@FXML private void showRenameAlbumPopup(ActionEvent e) throws Exception {
		selectionModel = albumTable.getSelectionModel();
		Album album = selectionModel.getSelectedItem();
		if (album == null) {
			return;
		}
		sceneManager.openScene("Rename_Album_Popup.fxml", this);
	}
	/**
	 * Switches the scene to the Search Album window, and passes control to it.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML private void searchAlbums(ActionEvent e) throws Exception {
		setAlbumScope(false);
		sceneManager.switchScene("Search_Window.fxml", users);
	}
	/**
	 * Switches the scene to the Album Display window, and passes control to it.
	 * @param e The event that triggered this method.
	 * @throws Exception
	 */
	@FXML 
	private void openAlbum(ActionEvent e) throws Exception{
		selectionModel = albumTable.getSelectionModel();
		Album album = selectionModel.getSelectedItem();
		if (album == null) return;
		setCurrAlbum(album.name);
		sceneManager.switchScene("Album_Display_Window.fxml", users);
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
	/**
	 * An Album class used to display album information in the TableView.
	 * @author Patrick Lee
	 *
	 */
	public class Album {
		String name;
		int numPhotos;
		String dateRange;
		/**
		 * Constructor for an Album object.
		 * @param name The name of the album.
		 * @param numPhotos The number of photos in this album.
		 * @param dateRange The range of dates of photos in this album.
		 */
		public Album(String name, int numPhotos, String dateRange) {
			this.name = name;
			this.numPhotos = numPhotos;
			this.dateRange = dateRange;
		}
		/**
		 * Gets the name of the album.
		 * @return The name of the album.
		 */
		public String getName() {
			return name;
		}
		/**
		 * Sets the name of the album.
		 * @param name The name of the album.
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * Gets the number of photos in this album.
		 * @return The number of photos in the album.
		 */
		public int getNumPhotos() {
			return numPhotos;
		}
		/**
		 * Sets the number of photos in this album.
		 * @param numPhotos The number of photos in the album.
		 */
		public void setNumPhotos(int numPhotos) {
			this.numPhotos = numPhotos;
		}
		/**
		 * Gets the date range of this album.
		 * @return The date range of this album.
		 */
		public String getDateRange() {
			return dateRange;
		}
		/**
		 * Sets the date range of this album.
		 * @param dateRange The date range of this album.
		 */
		public void setDateRange(String dateRange) {
			this.dateRange = dateRange;
		}
	}
	/**
	 * The Comparator used to sort the TableView and preserve the overall ordering when albums are changed.
	 * @author Patrick Lee
	 *
	 */
	class NameComp implements Comparator<Album>{
		@Override
		public int compare(Album a, Album b) {
			int nameComp = a.getName().compareToIgnoreCase(b.getName());
			return nameComp;
		}
	}
}
