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
	
	private boolean read_users_file(String user_request, String password_request) { //SI EL CAMPO DE CONTRASEÑA ES NULL, BUSCARA SI EXISTE EL USUARIO. SI ES UN STRING, BUSCARÁ SI EXISTE UN USUARIO ALMACENADO QUE COINCIDA CON LAS CLAVES; SI EXISTE, ASIGNARÁ SUS VALORES A LOS ATRIBUTOS DE EL USUARIO DE ESTA CLASE.
		try {
			File user_file = new File(Config.getUSERS_FILE());
			Scanner user_reader = new Scanner(user_file);
			while (user_reader.hasNextLine()) {
				String user_line = user_reader.nextLine();
				String[] user_fields = user_line.split(Config.getUSER_REGEX());
				String username = user_fields[0];
				String userpasswd = user_fields[1];
				if (password_request != null) { //SI TIENE CONTRASEÑA
					if (user_request.equals(username) && password_request.equals(userpasswd)) { //SI COINCIDE CON ALGUN USUARIO ALMACENADO ALMACENAMOS SUS VALORES A LOS ATRIBUTOS DE EL USUARIO DE ESTA CLASE
						user.setUsername(username);
						user.setName(user_fields[2]);
						user.setNif(user_fields[3]);
						user.setEmail(user_fields[4]);
						user.setAddress(user_fields[5]);
						user.setBirthdate(user_fields[6]);
						user.setRole(user_fields[7]);
						user_reader.close();
						return true; // LAS CLAVES COINCIDEN CON LAS DE UN USUARIO ALMACENADO
					}
				} else {
					if (user_request.equals(username)) {
						user_reader.close();
						return true; //EL USUARIO EXISTE Y POR ENDE NO SE PUEDE CREAR UN USUARIO CON ESE NOMBRE
					}
				}
			}
			user_reader.close();
			return false; // LAS CLAVES NO COINCIDEN CON LAS DE NINGUN USUARIO ALMACENADO. O NO EXISTE UN USUARIO CON EL NOMBRE PROPORCIONADO POR LO QUE SI SE PUEDE CREAR. O EL FICHERO NO TIENE LINEAS (NUNCA VA A PASAR)
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
      		e.printStackTrace();
			return false;
		}
	}
	
	private static boolean write_users_file(String user_line) { //ESCRIBE EL USUARIO EN EL FICHERO DE USUARIOS
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

	public boolean login(String[] request) { //VERIFICA SI EL USUARIO Y CONTRASEÑA QUE LE PROPORCIONAN COINCIDEN CON LOS DATOS ALMACENADOS
		String user_request = request[0];
		String password_request = request[1];
		if (read_users_file(user_request, password_request)) { //COMRUEBA
			logged = true;
			return true; //COINCIDEN USUARIO Y CONTRASEÑA
		} else {
			return false; //NO COINCIDEN O NO EXISTEN
		}
	}
	
	public static String[] request_login(){ //PIDE USUARIO Y CONTRASEÑA
		String[] request = new String[2]; 
		request[0] = field_formater("Username").toLowerCase();
		request[1] = field_formater("Password");
		return request;
	}

	public boolean signup() { //CREA UN USUARIO E INICIA SESIÓN
		String[] request = request_signup(); //PIDE LA LINEA DE USUARIO FORMATEADA, EL USUARIO, Y LA CONTRASEÑA
		String[] login_request = new String[2]; 
			//ASIGNA A VARIABLES LOCALES
			String user_line = request[0];
			String username = request[1];
			String passwd = request[2];
			//CONSTRUYE ARRAY PARA LLAMAR AL METODO LOGIN 
			login_request[0] = username;
			login_request[1] = passwd;

		if (write_users_file(user_line)) { //ESCRIBE LINEA DE USUARIO EN EL FICHERO
			if (login(login_request)) { //INTENTA INICIAR SESIÓN
				return true; //SE HA CREADO EL USUARIO Y SE HA INCIADO SESIÓN EXITOSAMENTE	
			} else {
				return false; //NO SE HA PODIDO INICIAR SESIÓN CON EL USUARIO YA CREADO
			}
		} else {
			return false; //NO SE HA PODIDO ESCRIBIR LA LINEA DE USUARIO EN EL FICHERO
		}
		
	}

	private String[] request_signup(){
		String[] processed = new String[3]; //LINEA DE USUARIO, NOMBRE DE USUARIO Y CONTRASEÑA
		String[] request = new String[8]; //CAMPOS DE USUARIO 
		
		String user_line = "";
		while (true) { //PIDE USUARIO HASTA QUE SE PROPORCIONE UNO VÁLIDO
			request[0] = field_formater("Username"); //PIDE USUARIO
			request[0] = request[0].toLowerCase(); //TRASFORMA A MINUSCULAS
			if (read_users_file(request[0],null) == false) { //SI USUARIO NO EXISTE
				break; //CONTINUA PIDIENDO EL RESTO DE DATOS	
			} else { //SI EXISTE
				System.out.printf("\nEl nombre de usuario %s no está disponible. Por favor, elige uno diferente.\n", request[0]); //MUESTRA MENSAJE Y LO VUELVE A PEDIR
			}
		}
		//AÑADIR DOBLE CONTRASEÑA
		String passwd_again = "";
		while (true) {
			request[1] = field_formater("Password");
			passwd_again = field_formater("Vuelve a introducir la password");
			if (request[1].equals(passwd_again)) { //COMPRUEBA QUE LAS PASSWORD SEAN IGUALES
				break; //SON IGUALES
			} else {
				System.out.println("\nLas password ingresadas no coinciden.\nVuelva a intentarlo"); //NO SON IGUALES
				//SE VUELVEN A SOLICITAR
			}
		}
		request[2] = field_formater("Full Name");
		request[3] = field_formater("Nif");
		request[4] = field_formater("Email");
		request[5] = field_formater("Address");
		request[6] = field_formater("Birthdate");
		request[7] = field_formater("Rol"); //TAL VEZ SE TENGA QUE QUITAR
		for (int i = 0; i < request.length; i++) {
			user_line = user_line + request[i] + Config.getUSER_REGEX(); //CONSTRUYE LINEA DE USUARIO
		}
		user_line = user_line.substring(0,user_line.length()-1); //CORTA ÚLTIMO REGEX
		processed[0] = user_line; //Linea
		processed[1] = request[0]; //Usuario
		processed[2] = request[1]; //Contraseña
		return processed; //DEVUELVE LINEA DE USUARIO, NOMBRE DE USUARIO Y CONTRASEÑA
	}

	private static String field_formater(String field) { //DEVUELVE UN CAMPO DE USUARIO FORMATEADO
		String field_formated;
		while (true) { //PIDE CAMPO HASTA QUE CUMPLA CON EL FORMATO
			boolean repeat = false; //CONDICIÓN QUE ALMACENA SI CUMPLE EL FORMATO. SI SE TERMINA EN FALSE SIGNIFICA QUE SE HA CUMPLIDO LAS REGLAS DE FORMATO. SI TERMINA EN TRUE, NO LAS CUMPLE, Y POR ENDE SE VUELVE A REPETIR LA PETICIÓN
			System.out.printf("%s...\n", field); //MENSAJE
			
			field_formated = keyboard.nextLine(); //ENTRADA DE DATOS
			
			for (char c : field_formated.toCharArray()) { //COMPRUEBA SI CUMPLE EL FORMATO
				if (c == Config.getUSER_REGEX().charAt(0)) {
					System.out.printf("El caracter \"%s\" esta prohibido. Por favor pruebe con otra opción\n", Config.getUSER_REGEX());
					repeat = true; //NO CUMPLE EL FORMATO
					break; //SE VUELVE A PEDIR CAMPO
				}
			}
			if (repeat == false) { //SE CUMPLE EL FORMATO
				break; //SE FINALIZA LA PETICIÓN
			}
		}
		return field_formated; //DEVUELVE CAMPO FORMATEADO
	}	

	public String showUser() { //MUESTRA LA INFORMACIÓN DEL USUARIO ACTUAL
		return user.toString();
	}

	public void logout() { //ELIMINA LOS DATOS DEL USUARIO ACTUAL Y RESTABLECE EL VALOR LOGGED DE LA CLASE
        user = new User();
        logged = false;
	}
	
	public User getUser() {
		return user;
	}
	public boolean isLogged() {
		return logged;
	}
}