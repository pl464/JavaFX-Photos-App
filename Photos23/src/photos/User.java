package photos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
* Class to represent an individual user.
* @author Lance Luo
*/
public class User implements Serializable {

	/**
	* Field to keep serialization consistent.
	*/
	static final long serialVersionUID = 1L;
	
	/**
	* HashMap to store album names and the list of photos inside each.
	*/
	public HashMap<String, ArrayList<String>> albums;
	/**
	* HashMap to store photos and the metadata associated with each.
	*/
	public HashMap<String, Picture> pictures;
	/**
	* HashMap to store user-defined tag names and their respective types (one value allowed or multiple values allowed).
	*/
	public HashMap<String, Boolean> tagnames; //true if one value allowed per photo, false if multiple values allowed per photo
	
	/**
	* Constructor for a User. Stores location and person as predefined tag names.
	*/
	public User() {
		albums = new HashMap<String, ArrayList<String>>();
		pictures = new HashMap<String, Picture>();
		tagnames = new HashMap<String, Boolean>();
		tagnames.put("location", true);
		tagnames.put("person", false);
	}
}
