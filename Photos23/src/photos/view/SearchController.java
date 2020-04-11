package photos.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

import photos.Picture;

public class SearchController extends Controller{
	
	public ArrayList<String> searchByDate(LocalDateTime start, LocalDateTime end, boolean single) {
		
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
	
	public ArrayList<String> searchByAnd(String tag1, String val1, String tag2, String val2, boolean single) {
		
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Picture> e : currUser.pictures.entrySet()) {
			if (single && !currUser.albums.get(currAlbum).contains(e.getKey())) {
				continue;
			}
			if ((e.getValue().tags.containsKey(tag1) && e.getValue().tags.get(tag1).contains(val1)) && (e.getValue().tags.containsKey(tag2) && e.getValue().tags.get(tag2).contains(val2))) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
	
	public ArrayList<String> searchByOr(String tag1, String val1, String tag2, String val2, boolean single) {
		
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Picture> e : currUser.pictures.entrySet()) {
			if (single && !currUser.albums.get(currAlbum).contains(e.getKey())) {
				continue;
			}
			if ((e.getValue().tags.containsKey(tag1) && e.getValue().tags.get(tag1).contains(val1)) || (e.getValue().tags.containsKey(tag2) && e.getValue().tags.get(tag2).contains(val2))) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
}
