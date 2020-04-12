package photos;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

public class Picture implements Serializable {

	static final long serialVersionUID = 1L;
	
	public String caption;
	public LocalDateTime date;
	public HashMap<String, ArrayList<String>> tags;
	
	public Picture(File file) {
		
		caption = "";
		date = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
		tags = new HashMap<String, ArrayList<String>>();
	}
}
