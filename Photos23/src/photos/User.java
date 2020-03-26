package photos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {

	static final long serialVersionUID = 1L;
	
	HashMap<String, ArrayList<String>> albums;
	HashMap<String, Image> images;
	ArrayList<String> tagnames;
	
	public User() {
		
		albums = new HashMap<String, ArrayList<String>>();
		images = new HashMap<String, Image>();
		tagnames = new ArrayList<String>();
		tagnames.add("location");
		tagnames.add("person");
	}
}
