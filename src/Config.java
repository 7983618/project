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
	public static final String VERSION = "1.0.0";
	/** Ruta de los archivos */
	public static final String FILE_PATH = "./assets/files/";
	public static final String MAZES_PATH = "./assets/mazes/";
	/** Ruta de el fichero de usuarios */
	public static String USERS_FILE = FILE_PATH + "users.txt";
	/** Mensaje de bienvenida */
	public static final String hr = "-------------------------------------------\n";
	public static final String WELCOME = hr + "BIENVENIDO AL PROYECTO LABERITO.\n Por la propia naturaleza del algoritmo que usa para resolver los laberintos, puede que falle al encontrar el laberinto más corto dado que se bloquea un punto que no debería bloquearse ya que el camino rojo lo debería haber descartado. Esto solo ocurre cuando el camino rojo vuelve al punto de entrada desde un punto de donde no salió, entones no termina de desartar todos los puntos muertos del laberinto.";
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
	public static final String START_EXIT_CONDITIONS =  hr + 
														"Introduce unas coordenadas válidas\n" +
														"-Deben existir en el mapa\n" +
														"-No deben ser un muro\n" +
														"-No pueden ser iguales las coordenadas de salida y entrada";

	public static final String SEARCH_PATH_MENU = 	hr +
													"Selecciona un camino\n" +
													hr +
													"1 El primer camino posible\n" +
													"2 El camino más corto\n" +
													"3 Restaurar (para volver a buscar)\n" +
													"0 Salir\n" +
													hr +
													"Eleción...";
	/** Separador de campos de usuario en el fichero de usuarios */
	public static final String USER_REGEX = "#";

	public static final char RED_WAY_WALL = '.';
	public static final char YELLOW_WAY_WALL = 'x';
	public static final char BLOCK_CHANCE_WALL = 'N';
	public static final int RED_WAY_SPEED = 50;
	public static final int YELLOW_WAY_SPEED = 50;

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
