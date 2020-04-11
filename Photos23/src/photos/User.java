package photos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {

	static final long serialVersionUID = 1L;
	
	public HashMap<String, ArrayList<String>> albums;
	public HashMap<String, Picture> pictures;
	public ArrayList<String> tagnames;
	
	public User() {
		
		albums = new HashMap<String, ArrayList<String>>();
		pictures = new HashMap<String, Picture>();
		tagnames = new ArrayList<String>();
		tagnames.add("location");
		tagnames.add("person");
	}
}
