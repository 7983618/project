/**
 * Contiene variables que sirven para el correcto funcionamiento del programa.
 * 
 * @author Santiago Quispe
 * @version 0.1.0
 */
public class Config {
	
	private Config() {}
	/**
	 *  Versión actual del programa
	 */
	public static final String VERSION = "0.2.0";
	/** Ruta de los archivos */
	public static final String FILE_PATH = "./assets/files/";
	public static final String MAZES_PATH = "./assets/mazes/";
	/** Ruta de el fichero de usuarios */
	public static String USERS_FILE = FILE_PATH + "users.txt";
	/** Mensaje de bienvenida */
	public static final String hr = "-------------------------------------------\n";
	public static final String WELCOME = hr + "BIENVENIDO AL PROYECTO LABERITO.\nEste proyecto se encuentra en desarrollo. Actualmente se ha implementado y documentado toda la parte que tenga que ver con iniciar sessión siguiendo recomendaciones de Sonar. IMPORTANTE EJECUTAR DESDE EL CENTRO DEL PAQUETE, SINO NO ENCONTRARÁ CORRECTAMENTE LA RUTA DE LOS FICHEROS EXTERNOS";
	/** Mesnsaje de despedida */
	public static final String GOODBYE = hr + "Hasta la proxima.\n";
	/** Menú antes de loggear */
	public static final String UNLOGGED_MENU = 	hr +
											"Elija una de las opciones\n" +
											hr +
										 	"1 Iniciar Sesión\n" +
											"2 Registro\n" +
											"0 Salir\n" +
											hr +
											"Elección...";
	/** Menú despues de loggear */
	public static final String LOGGED_MENU = 	hr +
											"Elija una de las opciones\n" +
											hr +
											"1 Cargar laberinto\n" +
											"2 Ver laberinto actual\n" +
											"3 Establecer casillas de entrada y salida\n" +
											"4 Buscar caminos\n" +
											"5 Ver usuario actual\n" +
											"6 Cerrar sesión\n" +
											"0 Salir\n" +
											hr +
											"Elección...";
											
	public static final String MAZE_MENU =		hr +
											"Elija una de las opciones\n" +
											hr +
											"1 Introducir nombre de laberinto\n" +
											"2 Listar laberintos disponibles\n" +
											"0 Salir\n" +
											hr +
											"Elección...";										
	/** Separador de campos de usuario en el fichero de usuarios */
	public static final String USER_REGEX = "#";

	/** @return Ruta de los archivos */
	public static String getFILE_PATH() {
		return FILE_PATH;
	}
	
	public static String getMAZES_PATH() {
		return MAZES_PATH;
	}
	/** @return Ruta de el fichero de usuarios */
	public static String getUSERS_FILE() {
		return USERS_FILE;
	}
	/** @return Mensaje de bienvenida */
	public static String getWELCOME() {
		return WELCOME;
	}
	/** @return Mensaje de despedida */
	public static String getGOODBYE() {
		return GOODBYE;
	}
	/** @return Menú antes de loggear */
	public static String getUNLOGGED_MENU() {
		return UNLOGGED_MENU;
	}
	/** @return Menú despues de loggear */
	public static String getLOGGED_MENU() {
		return LOGGED_MENU;
	}
	
	public static String getMAZE_MENU() {
		return MAZE_MENU;
	}
	/** @return Separador de campos de usuario en el fichero de usuarios */
	public static String getUSER_REGEX() {
		return USER_REGEX;
	}
	
}
