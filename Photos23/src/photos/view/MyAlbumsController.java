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
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

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
	
	private ObservableList<Album> data = FXCollections.observableArrayList();
	private MultipleSelectionModel<Album> selectionModel;
	
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
	public boolean addTag(String tag, boolean single) {
		if (currUser.tagnames.containsKey(tag)) {
			return false;
		}
		currUser.tagnames.put(tag, single);
		return true;
	}
	
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
	
	@FXML private void showNewTagPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_Tag_Popup.fxml", this);
	}
	@FXML private void showNewAlbumPopup(ActionEvent e) throws Exception {
		sceneManager.openScene("New_Album_Popup.fxml", this);
	}
	@FXML private void showRenameAlbumPopup(ActionEvent e) throws Exception {
		selectionModel = albumTable.getSelectionModel();
		Album album = selectionModel.getSelectedItem();
		if (album == null) {
			return;
		}
		sceneManager.openScene("Rename_Album_Popup.fxml", this);
	}
	
	//currently DOES NOT search albums. for testing purposes, prints out tagnames and albumnames.
	@FXML private void searchAlbums(ActionEvent e) {
		currUser.tagnames.keySet().forEach((tag)->System.out.println(tag));
		currUser.albums.forEach((k,v)->System.out.println(k));
	}

	@FXML 
	private void openAlbum(ActionEvent e) throws Exception{
		selectionModel = albumTable.getSelectionModel();
		Album album = selectionModel.getSelectedItem();
		if (album == null) return;
		setCurrAlbum(album.name);
		sceneManager.switchScene("Album_Display_Window.fxml", users);
	}
	@FXML
	private void logout(ActionEvent e) throws Exception {
		sceneManager.switchScene("Login_Window.fxml", users);
	}

	public class Album {
		String name;
		int numPhotos;
		String dateRange;

		public Album(String name, int numPhotos, String dateRange) {
			this.name = name;
			this.numPhotos = numPhotos;
			this.dateRange = dateRange;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getNumPhotos() {
			return numPhotos;
		}
		public void setNumPhotos(int numPhotos) {
			this.numPhotos = numPhotos;
		}
		public String getDateRange() {
			return dateRange;
		}
		public void setDateRange(String dateRange) {
			this.dateRange = dateRange;
		}
	}
	class NameComp implements Comparator<Album>{
		@Override
		public int compare(Album a, Album b) {
			int nameComp = a.getName().compareToIgnoreCase(b.getName());
			return nameComp;
		}
	}
}
