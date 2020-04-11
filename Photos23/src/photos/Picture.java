package photos;

import java.io.File;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

public class Picture implements Serializable {

	static final long serialVersionUID = 1L;
	
	public String caption;
	public LocalDateTime date;
	public HashMap<String, ArrayList<String>> tags;
	
	public Picture(File picture) {
		caption = "";
		LocalDateTime lastModifiedDate =
		        LocalDateTime.ofInstant(Instant.ofEpochMilli(picture.lastModified()), 
		                                TimeZone.getDefault().toZoneId());  
		date = lastModifiedDate;
		tags = new HashMap<String, ArrayList<String>>();
	}
}
