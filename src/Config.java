public class Config {
	private static String VERSION = "0.1.0";
	private static String FILE_PATH = "./assets/files/";
	private static String USERS_FILE = "assets/files/users.txt";
	private static String WELCOME = "BIENVENIDA #COMPLETAR";
	private static String GOODBYE = "Hasta la proxima.";
	private static String UNLOGGED_MENU = 	"------------------\n" +
										 	"1 Iniciar Sesión\n" +
											"2 Registro\n" +
											"0 Salir\n" +
											"------------------\n";
	private static String LOGGED_MENU = 		"------------------\n" +
											"1 Cargar laberinto\n" +
											"2 Ver laberinto actual\n" +
											"3 Establecer casillas de entrada y salida\n" +
											"4 Buscar caminos\n" +
											"5 Ver usuario actual\n" +
											"6 Cerrar sesión\n" +
											"0 Salir\n" +
											"------------------\n";
	private static String USER_REGEX = "#";
	
	public static String getVERSION() {
		return VERSION;
	}
	public static String getFILE_PATH() {
		return FILE_PATH;
	}
	public static String getUSERS_FILE() {
		return USERS_FILE;
	}
	public static String getWELCOME() {
		return WELCOME;
	}
	public static String getGOODBYE() {
		return GOODBYE;
	}
	public static String getUNLOGGED_MENU() {
		return UNLOGGED_MENU;
	}
	public static String getLOGGED_MENU() {
		return LOGGED_MENU;
	}
	public static String getUSER_REGEX() {
		return USER_REGEX;
	}
	
}
