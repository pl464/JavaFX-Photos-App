package photos;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

/**
* Class to represent an individual photo.
* @author Lance Luo
*/
public class Picture implements Serializable {
	
	/**
	* Field to keep serialization consistent.
	*/
	static final long serialVersionUID = 1L;
	
	/**
	* String to store the caption.
	*/
	public String caption;
	/**
	* Field to store the date the photo was taken.
	*/
	public LocalDateTime date;
	/**
	* HashMap to store tag names and the values associated with each.
	*/
	public HashMap<String, ArrayList<String>> tags;
	
	/**
	* Constructor for a Picture. Stores the last modified time of the file as the date.
	* @param file The photo file.
	*/
	public Picture(File file) {
		caption = "";
		date = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
		tags = new HashMap<String, ArrayList<String>>();
	}
}
