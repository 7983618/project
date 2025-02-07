import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;
public class Session {
    private User user;
    private boolean logged;
	
	public Session() {
        user = new User();
        logged = false;
    }

	public void login(String[] request) {
		String user_request = request[0];
		String password_request = request[1];
		try {
			File user_file = new File(Config.USERS_FILE);
			Scanner user_reader = new Scanner(user_file);
			while (user_reader.hasNextLine()) {
				String user_line = user_reader.nextLine();
				String[] user_fields = user_line.split(Config.USER_REGEX);
				String username = user_fields[0];
				String userpasswd = user_fields[1];
				if (user_request.equals(username) && password_request.equals(userpasswd)) {
					user.setUsername(username);
					user.setName(user_fields[2]);
					user.setNif(user_fields[3]);
					user.setEmail(user_fields[4]);
					user.setAddress(user_fields[5]);
					user.setBirthdate(user_fields[6]);
					user.setRole(user_fields[7]);
					logged = true;
					break;
					//IMPLEMTENTAR SI NO SE ENCUENTRA EL USUARIO
				}
			}
			user_reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
      		e.printStackTrace();
		}
	}
	public static String[] request_login(){
		String[] request = new String[2]; 
		Scanner keyboard = new Scanner(System.in);
		System.out.println("User...");
		String user_request = keyboard.nextLine();
		request[0] = user_request.toLowerCase();
		System.out.println("Password...");
		String password_request = keyboard.nextLine();
		request[1] = password_request;
		keyboard.close();
		return request;
	}

	public void signup() {

	}
	public static String[] request_signup(){
		String[] request = new String[2]; 
		Scanner keyboard = new Scanner(System.in);
		System.out.println("User...");
		String user_request = keyboard.nextLine();
		request[0] = user_request.toLowerCase();
		System.out.println("Password...");
		String password_request = keyboard.nextLine();
		request[1] = password_request;
		keyboard.close();
		return request;
	}

	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isLogged() {
		return logged;
	}
	public void setLogged(boolean logged) {
		this.logged = logged;
	}
    
    
}
