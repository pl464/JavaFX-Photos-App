package photos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Temp {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		/*
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
		users.get("stock").images.put(a, new Image(a));
		users.get("stock").images.put(b, new Image(b));
		users.get("stock").images.put(c, new Image(c));
		users.get("stock").images.put(d, new Image(d));
		users.get("stock").images.put(e, new Image(e));
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
		oos.writeObject(users);
		*/
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
