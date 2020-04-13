package photos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {

	static final long serialVersionUID = 1L;
	
	public HashMap<String, ArrayList<String>> albums;
	public HashMap<String, Picture> pictures;
	public HashMap<String, Boolean> tagnames; //true if one value allowed per photo, false if multiple values allowed per photo
	
	public User() {
		
		albums = new HashMap<String, ArrayList<String>>();
		pictures = new HashMap<String, Picture>();
		tagnames = new HashMap<String, Boolean>();
		tagnames.put("location", true);
		tagnames.put("person", false);
	}
}
