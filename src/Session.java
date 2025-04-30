// PRUEBA A INICIAR SESIÓN CON UN FICHERO DE 1 SOLA LINEA Y INTENTA CREAR UN USUARIO CON UN FICHERO VACIÍO
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;
/**
 * Gestiona la sesión de usuario, incluyendo login, registro y manejo de datos.
 * 
 * @author Santiago Quispe
 * @version 0.1.0
 */
public class Session {
	/** Objeto Usuario de la Sessión */
    private User user;
	/** Valor que determina si se ha iniciado sesión o no */
	private boolean logged;
	private static Scanner keyboard = new Scanner(System.in);
	
	/**
	 * Constructor por defecto. Inicializa un nuevo usuario y establece logged a false.
	 */
	public Session() {
        user = new User();
        logged = false;
    }
	
	/**
	 * Lee el archivo de usuarios y verifica las credenciales coinciden con las almacenadas o si el usuario existe.
	 * 
	 * @param userRequest     Nombre de usuario solicitado
	 * @param passwordRequest Contraseña solicitada (si es null comprobará si el usuario)
	 * @return true si las credenciales son válidas o el usuario existe
	 */
	private boolean readUsersFile(String userRequest, String passwordRequest) { //SI EL CAMPO DE CONTRASEÑA ES NULL, BUSCARA SI EXISTE EL USUARIO. SI ES UN STRING, BUSCARÁ SI EXISTE UN USUARIO ALMACENADO QUE COINCIDA CON LAS CLAVES; SI EXISTE, ASIGNARÁ SUS VALORES A LOS ATRIBUTOS DE EL USUARIO DE ESTA CLASE.
		try {
			File userFile = new File(Config.getUSERS_FILE());
			Scanner userReader = new Scanner(userFile);
			while (userReader.hasNextLine()) {
				String userLine = userReader.nextLine();
				String[] userFields = userLine.split(Config.getUSER_REGEX());
				String username = userFields[0];
				String userpasswd = userFields[1];
				if (passwordRequest != null) { //SI TIENE CONTRASEÑA
					if (userRequest.equals(username) && passwordRequest.equals(userpasswd)) { //SI COINCIDE CON ALGUN USUARIO ALMACENADO ALMACENAMOS SUS VALORES A LOS ATRIBUTOS DE EL USUARIO DE ESTA CLASE
						user.setUsername(username);
						user.setName(userFields[2]);
						user.setNif(userFields[3]);
						user.setEmail(userFields[4]);
						user.setAddress(userFields[5]);
						user.setBirthdate(userFields[6]);
						user.setRole(userFields[7]);
						userReader.close();
						return true; // LAS CLAVES COINCIDEN CON LAS DE UN USUARIO ALMACENADO
					}
				} else {
					if (userRequest.equals(username)) {
						userReader.close();
						return true; //EL USUARIO EXISTE Y POR ENDE NO SE PUEDE CREAR UN USUARIO CON ESE NOMBRE
					}
				}
			}
			userReader.close();
			return false; // LAS CLAVES NO COINCIDEN CON LAS DE NINGUN USUARIO ALMACENADO. O NO EXISTE UN USUARIO CON EL NOMBRE PROPORCIONADO POR LO QUE SI SE PUEDE CREAR. O EL FICHERO NO TIENE LINEAS (NUNCA VA A PASAR)
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
      		e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Escribe una nueva línea de usuario en el archivo de usuarios.
	 * 
	 * @param userLine Línea de usuario a escribir
	 * @return true si la escritura fue exitosa
	 */
	private static boolean writeUsersFile(String userLine) { //ESCRIBE EL USUARIO EN EL FICHERO DE USUARIOS
		try (FileWriter userWriter = new FileWriter(Config.getUSERS_FILE(), true)){
			userWriter.write("\n" + userLine);
			return true;
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Intenta iniciar sesión con las credenciales proporcionadas.
	 * 
	 * @param request Array con nombre de usuario y contraseña
	 * @return true si el login fue exitoso
	 */
	public boolean login(String[] request) { //VERIFICA SI EL USUARIO Y CONTRASEÑA QUE LE PROPORCIONAN COINCIDEN CON LOS DATOS ALMACENADOS
		String userRequest = request[0];
		String passwordRequest = request[1];
		if (readUsersFile(userRequest, passwordRequest)) { //COMRUEBA
			logged = true;
			return true; //COINCIDEN USUARIO Y CONTRASEÑA
		} else {
			return false; //NO COINCIDEN O NO EXISTEN
		}
	}
	
	/**
	 * Solicita al usuario las credenciales para iniciar sesión.
	 * 
	 * @return Array con nombre de usuario y contraseña
	 */
	public static String[] requestLogin(){ //PIDE USUARIO Y CONTRASEÑA
		String[] request = new String[2]; 
		request[0] = fieldFormater("-----------------------------\n" + "Username").toLowerCase();
		request[1] = fieldFormater("Password");
		return request;
	}

	/**
	 * Registra un nuevo usuario e inicia sesión.
	 * 
	 * @return true si el registro y login fueron exitosos
	 */
	public boolean signup() { //CREA UN USUARIO E INICIA SESIÓN
		String[] request = requestSignup(); //PIDE LA LINEA DE USUARIO FORMATEADA, EL USUARIO, Y LA CONTRASEÑA
		String[] loginRequest = new String[2]; 
			//ASIGNA A VARIABLES LOCALES
			String userLine = request[0];
			String username = request[1];
			String passwd = request[2];
			//CONSTRUYE ARRAY PARA LLAMAR AL METODO LOGIN 
			loginRequest[0] = username;
			loginRequest[1] = passwd;

		if (writeUsersFile(userLine)) { //ESCRIBE LINEA DE USUARIO EN EL FICHERO
			if (login(loginRequest)) { //INTENTA INICIAR SESIÓN
				return true; //SE HA CREADO EL USUARIO Y SE HA INCIADO SESIÓN EXITOSAMENTE	
			} else {
				return false; //NO SE HA PODIDO INICIAR SESIÓN CON EL USUARIO YA CREADO
			}
		} else {
			return false; //NO SE HA PODIDO ESCRIBIR LA LINEA DE USUARIO EN EL FICHERO
		}
		
	}

	/**
	 * Solicita y procesa los datos para el registro de un nuevo usuario.
	 * 
	 * @return Array con la línea de usuario, nombre de usuario y contraseña
	 */
	private String[] requestSignup(){
		String[] processed = new String[3]; //LINEA DE USUARIO, NOMBRE DE USUARIO Y CONTRASEÑA
		String[] request = new String[8]; //CAMPOS DE USUARIO 
		
		StringBuilder userLine = new StringBuilder();
		while (true) { //PIDE USUARIO HASTA QUE SE PROPORCIONE UNO VÁLIDO
			request[0] = fieldFormater("-----------------------------\n" + "Username"); //PIDE USUARIO
			request[0] = request[0].toLowerCase(); //TRASFORMA A MINUSCULAS
			if (!readUsersFile(request[0],null)) { //SI USUARIO NO EXISTE
				break; //CONTINUA PIDIENDO EL RESTO DE DATOS	
			} else { //SI EXISTE
				System.out.printf("\nEl nombre de usuario %s no está disponible. Por favor, elige uno diferente.\n", request[0]); //MUESTRA MENSAJE Y LO VUELVE A PEDIR
			}
		}
		//AÑADIR DOBLE CONTRASEÑA
		String passwdAgain = "";
		while (true) {
			request[1] = fieldFormater("Password");
			passwdAgain = fieldFormater("Vuelve a introducir la password");
			if (request[1].equals(passwdAgain)) { //COMPRUEBA QUE LAS PASSWORD SEAN IGUALES
				break; //SON IGUALES
			} else {
				System.out.println("\nLas password ingresadas no coinciden.\nVuelva a intentarlo"); //NO SON IGUALES
				//SE VUELVEN A SOLICITAR
			}
		}
		request[2] = fieldFormater("Full Name");
		request[3] = fieldFormater("Nif");
		request[4] = fieldFormater("Email");
		request[5] = fieldFormater("Address");
		request[6] = fieldFormater("Birthdate");
		request[7] = fieldFormater("Rol"); //TAL VEZ SE TENGA QUE QUITAR
		for (int i = 0; i < request.length; i++) {
			userLine.append(request[i] + Config.getUSER_REGEX()); //CONSTRUYE LINEA DE USUARIO
		}
		processed[0] = userLine.toString().substring(0, userLine.length()-1); //Linea sin el último REGEX
		processed[1] = request[0]; //Usuario
		processed[2] = request[1]; //Contraseña
		return processed; //DEVUELVE LINEA DE USUARIO, NOMBRE DE USUARIO Y CONTRASEÑA
	}

	/**
	 * Formatea y valida un campo de entrada de usuario.
	 * 
	 * @param field Nombre del campo a solicitar
	 * @return Valor del campo formateado y validado
	 */
	private static String fieldFormater(String field) { //DEVUELVE UN CAMPO DE USUARIO FORMATEADO
		String fieldFormated;
		while (true) { //PIDE CAMPO HASTA QUE CUMPLA CON EL FORMATO
			boolean repeat = false; //CONDICIÓN QUE ALMACENA SI CUMPLE EL FORMATO. SI SE TERMINA EN FALSE SIGNIFICA QUE SE HA CUMPLIDO LAS REGLAS DE FORMATO. SI TERMINA EN TRUE, NO LAS CUMPLE, Y POR ENDE SE VUELVE A REPETIR LA PETICIÓN
			System.out.printf("%s...\n", field); //MENSAJE
			
			fieldFormated = keyboard.nextLine(); //ENTRADA DE DATOS
			
			for (char c : fieldFormated.toCharArray()) { //COMPRUEBA SI CUMPLE EL FORMATO
				if (c == Config.getUSER_REGEX().charAt(0)) {
					System.out.printf("El caracter \"%s\" esta prohibido. Por favor pruebe con otra opción\n", Config.getUSER_REGEX());
					repeat = true; //NO CUMPLE EL FORMATO
					break; //SE VUELVE A PEDIR CAMPO
				}
			}
			if (!repeat) { //SE CUMPLE EL FORMATO
				break; //SE FINALIZA LA PETICIÓN
			}
		}
		return fieldFormated; //DEVUELVE CAMPO FORMATEADO
	}	

	/**
	 * Muestra la información del usuario actual.
	 * 
	 * @return Representación en cadena del usuario actual
	 */
	public String showUser() { //MUESTRA LA INFORMACIÓN DEL USUARIO ACTUAL
		return user.toString();
	}

	/**
	 * Cierra la sesión del usuario actual.
	 */
	public void logout() { //ELIMINA LOS DATOS DEL USUARIO ACTUAL Y RESTABLECE EL VALOR LOGGED DE LA CLASE
        user = new User();
        logged = false;
	}
	
	/**
	 * Obtiene el usuario actual de la sesión.
	 * 
	 * @return El objeto User de la sesión actual
	 */
	public User getUser() {
		return user;
	}
	/**
	 * Verifica si hay una sesión activa.
	 * 
	 * @return true si hay un usuario con sesión iniciada, false en caso contrario
	 */
	public boolean isLogged() {
		return logged;
	}
}