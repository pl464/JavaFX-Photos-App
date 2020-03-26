package photos;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;

public class Image implements Serializable {

	static final long serialVersionUID = 1L;
	
	String caption;
	LocalDateTime date;
	HashMap<String, ArrayList<String>> tags;
	
	public Image(String path) {
		
		caption = "";
		date = LocalDateTime.ofEpochSecond((new File(path)).lastModified(), 0, ZoneOffset.UTC);
		tags = new HashMap<String, ArrayList<String>>();
	}
}
