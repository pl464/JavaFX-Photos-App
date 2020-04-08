package photos.view;

public class SearchController extends Controller{

	public ArrayList<String> searchByDate(LocalDateTime start, LocalDateTime end) {
		
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Image> e : currUser.images.entrySet()) {
			if (e.getValue().date.compareTo(start) >= 0 && e.getValue().date.compareTo(end) <= 0) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}

	public ArrayList<String> searchByTag(String tag, String val) {
		
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Image> e : currUser.images.entrySet()) {
			if (e.getValue().tags.get(tag).contains(val)) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
	
	public ArrayList<String> searchByAnd(String tag1, String val1, String tag2, String val2) {
		
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Image> e : currUser.images.entrySet()) {
			if (e.getValue().tags.get(tag1).contains(val1) && e.getValue().tags.get(tag2).contains(val2)) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
	
	public ArrayList<String> searchByOr(String tag1, String val1, String tag2, String val2) {
		
		ArrayList<String> paths = new ArrayList<String>();
		for (Map.Entry<String, Image> e : currUser.images.entrySet()) {
			if (e.getValue().tags.get(tag1).contains(val1) || e.getValue().tags.get(tag2).contains(val2)) {
				paths.add(e.getKey());
			}
		}
		return paths;
	}
}
