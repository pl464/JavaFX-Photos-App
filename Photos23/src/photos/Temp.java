package photos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Temp extends Application {

	@Override
    public void start(Stage stage) throws FileNotFoundException, IOException {
		
		HashMap<String, User> users = new HashMap<String, User>();
		users.put("stock", new User());
		users.get("stock").albums.put("stock", new ArrayList<String>());
		for (int i = 0; i < 5; i++) {
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(stage);
	        if (file != null) {
	            System.out.println(file.getAbsolutePath());
	            users.get("stock").albums.get("stock").add(file.getAbsolutePath());
	            users.get("stock").pictures.put(file.getAbsolutePath(), new Picture(file));
	        }
		}
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/users.dat"));
		oos.writeObject(users);
		oos.close();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/users.dat"));
		@SuppressWarnings("unchecked")
		HashMap<String, User> users = (HashMap<String, User>) ois.readObject();
		for (String s : users.get("stock").albums.get("stock")) {
			System.out.println(users.get("stock").pictures.get(s).date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		}
		for (String s : users.get("stock").tagnames) {
			System.out.println(s);
		}
		ois.close();
	}
}
