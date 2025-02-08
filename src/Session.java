// PRUEBA A INICIAR SESIÓN CON UN FICHERO DE 1 SOLA LINEA Y INTENTA CREAR UN USUARIO CON UN FICHERO VACIÍO
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;
public class Session {
    private User user;
    private boolean logged;
	private static Scanner keyboard = new Scanner(System.in);
	
	public Session() {
        user = new User();
        logged = false;
    }
	
	public boolean read_users_file(String user_request, String password_request) {
		//SI EL COMPO DE CONTRASEÑA ES NULL, BUSCARA SI EXISTE EL USUARIO. SI ES UN STRING, INICIARÁ SESION CON EL USUARIO EXISTENTE (SI CONINCIDEN LAS CLAVES)
		try {
			File user_file = new File(Config.getUSERS_FILE());
			Scanner user_reader = new Scanner(user_file);
			while (user_reader.hasNextLine()) {
				String user_line = user_reader.nextLine();
				String[] user_fields = user_line.split(Config.getUSER_REGEX());
				String username = user_fields[0];
				String userpasswd = user_fields[1];
				//ESTE ESTA FALLANDO (NO DEBE DEVOLVER VALOR EN CIERTAS OPCIONES SINO NO SE RECORRE TODOS LOS USUARIOS DE LA TABLA)
				if (password_request != null) {
					if (user_request.equals(username) && password_request.equals(userpasswd)) {
						user.setUsername(username);
						user.setName(user_fields[2]);
						user.setNif(user_fields[3]);
						user.setEmail(user_fields[4]);
						user.setAddress(user_fields[5]);
						user.setBirthdate(user_fields[6]);
						user.setRole(user_fields[7]);
						user_reader.close();
						return true; // LAS CLAVES COINCIDEN CON LAS DE UN USUARIO ALMACENADO
					} else {
						user_reader.close();
						return false; // LAS CLAVES NO COINCIDEN CON NINGUN USUARIO ALMACENADO (Son incorrecta o el usuario no existe) 
					}
				} else {
					if (user_request.equals(username)) {
						user_reader.close();
						return true; //EL USUARIO EXISTE Y POR ENDE NO SE PUEDE CREAR UN USUARIO CON ESE NOMBRE
					} else {
						user_reader.close();
						return false; //NO EXISTE UN USUARIO CON ES NOMBRE POR LO QUE SI ES POSIBLE CREARLO
					}
				}
			}
			user_reader.close();
			return false; // EL FICHERO NO TIENE LINEAS (NUNCA VA A PASAR)
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
      		e.printStackTrace();
			return false;
		}
	}
	
	public static boolean write_users_file(String user_line) {
		try {
			FileWriter user_writer = new FileWriter(Config.getUSERS_FILE(), true);
			user_writer.write("\n" + user_line);
			user_writer.close();
			return true;
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}
	}

	public boolean login(String[] request) { //CAMBIAR A BOOLEAN
		String user_request = request[0];
		String password_request = request[1];
		if (read_users_file(user_request, password_request)) {
			logged = true;
			return true;
		} else {
			return false;
		}
	}
	
	public static String[] request_login(){
		String[] request = new String[2]; 
		request[0] = field_formater("Username").toLowerCase();
		request[1] = field_formater("Password");
		return request;
	}

	public boolean signup() {
		String[] request = request_signup();
		String[] login_request = new String[2]; 
		String user_line = request[0];
		String username = request[1];
		String passwd = request[2];
		login_request[0] = username;
		login_request[1] = passwd;
		if (write_users_file(user_line)) {
			if (login(login_request)) {
				return true;	
			} else {
				return false;
			}
			
		} else {
			return false;
		}
		
	}

	public String[] request_signup(){
		String[] processed = new String[3];
		String[] request = new String[8]; 
		
		String user_line = "";
		while (true) {
			request[0] = field_formater("Username");
			request[0] = request[0].toLowerCase();
			if (read_users_file(request[0],null) == false) {
				break;	
			} else {
				System.out.printf("El nombre de usuario %s no está disponible. Por favor, elige uno diferente.\n", request[0]);
			}
		}
		//AÑADIR DOBLE CONTRASEÑA
		request[1] = field_formater("Password");
		request[2] = field_formater("Full Name");
		request[3] = field_formater("Nif");
		request[4] = field_formater("Email");
		request[5] = field_formater("Address");
		request[6] = field_formater("Birthdate");
		request[7] = field_formater("Rol"); //TAL VEZ SE TENGA QUE QUITAR
		for (int i = 0; i < request.length; i++) {
			user_line = user_line + request[i] + Config.getUSER_REGEX();
		}
		user_line = user_line.substring(0,user_line.length()-1);
		processed[0] = user_line; //Linea
		processed[1] = request[0]; //Usuario
		processed[2] = request[1]; //Contraseña
		return processed;
	}

	public static String field_formater(String field) {
		String field_formated;
		while (true) {
			System.out.printf("%s...\n", field);
			
			field_formated = keyboard.nextLine();
			for (char c : field_formated.toCharArray()) {
				if (c == Config.getUSER_REGEX().charAt(0)) {
					System.out.printf("El caracter \"%s\" esta prohibido. Por favor pruebe con otra opción\n", Config.getUSER_REGEX());
					continue;
				}
			}
			break;
		}
		
		return field_formated;
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