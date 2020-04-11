package photos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Initializer {

	public static void initialize() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		HashMap<String, User> users = new HashMap<String, User>();
		users.put("stock", new User());
		users.get("stock").albums.put("stock", new ArrayList<String>());
		String a = "data/tomnook.jpg";
		String b = "data/kkslider.jpg";
		String c = "data/mrresetti.jpg";
		String d = "data/isabelle.jpg";
		String e = "data/blathers.jpg";
		users.get("stock").albums.get("stock").add(a);
		users.get("stock").albums.get("stock").add(b);
		users.get("stock").albums.get("stock").add(c);
		users.get("stock").albums.get("stock").add(d);
		users.get("stock").albums.get("stock").add(e);
		//users.get("stock").pictures.put(a, new Picture(a));
		//users.get("stock").pictures.put(b, new Picture(b));
		//users.get("stock").pictures.put(c, new Picture(c));
		//users.get("stock").pictures.put(d, new Picture(d));
		//users.get("stock").pictures.put(e, new Picture(e));
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
		oos.writeObject(users);
		oos.close();
		/*
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/users.dat"));
		HashMap<String, User> users = (HashMap<String, User>) ois.readObject();
		for (String s : users.get("stock").albums.get("stock")) {
			System.out.println(users.get("stock").images.get(s).date);
		}
		for (String s : users.get("stock").tagnames) {
			System.out.println(s);
		}
		*/
	}
}