import java.io.File;
import java.util.Scanner;
/**
 * Clase principal que maneja la interfaz de usuario y el flujo del programa.
 * 
 * @author Santiago Quispe
 * @version 0.1.0
 */
public class Main {
    /** Sessión del programa principal */
    private static Session session = new Session();
    /** Objeto Scanner utilizado en toda la clase */
    private static Scanner keyboard = new Scanner(System.in);
    /** String que simula ser una barra horizontal */
    private static String hr = "------------------";
    private static Maze maze = new Maze();
    private static String[] mazesList;
    /**
     * Método principal que inicia el programa.
     * 
     * @param args Argumentos de línea de comando (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println();
        System.out.print(Config.getWELCOME());
        int auxUnlogged = -1;
        while (true) { //MUESTRA UNLOGGED MENU HASTA QUE SE SALGA
            //SOLICÍTA ELECCIÓN
            System.out.println(Config.getUNLOGGED_MENU());
            System.out.println("Elección...");
            auxUnlogged = scannerInt();
            System.out.println(hr);
            
            if (auxUnlogged == 1) { //INTENTA INICIAR SESIÓN CON USUARIO Y CONTRASEÑA
                if (session.login(Session.requestLogin())) { //INICIO DE SESIÓN EXITOSO
                    System.out.println(hr);
                    System.out.print("Sesión Iniciada\n");
                    if (logged()) { //MUESTRA LOGGED MENU Y PERMITE INTERACTUAR CON ÉL
                        break; //FINALIZA EL PROGRAMA SI SE PULSA 0 EN EL LOGGED MENU
                    } else {
                        continue; //CONTINUA EL PROGRAMA SI SE PUSA 6 EN EL LOGGED MENU
                    }
                } else { //FRACASO EN INICIO DE SESIÓN
                    System.out.println(hr);
                    System.out.print("El usuario o la contraseña no coinciden");
                    continue;
                }
            } else if (auxUnlogged == 2) { //INICIA PROCCESO DE REGISTRO
                if (session.signup()) { //EXITO AL REGISTRAR USUARIO E INICIAR SESIÓN CON ÉL
                    System.out.println(hr);
                    System.out.println("Usuario creado con éxito");
                    System.out.print("Sesión Iniciada\n");
                    if (logged()) { //MUESTRA LOGGED MENU Y PERMITE INTERACTUAR CON ÉL
                        break; //FINALIZA EL PROGRAMA SI SE PULSA 0 EN EL LOGGED MENU
                    } else {
                        continue; //CONTINUA EL PROGRAMA SI SE PUSA 6 EN EL LOGGED MENU
                    }
                } 
            } else if (auxUnlogged == 0) {
                break; //FINALIZA EL PROGRAMA SI SE PULSA 0 EN EL UNLOGGED MENU
            } else { //VUELVE A PEDIR DATOS SI NO SE HA PULSADO UNA OPCIÓN VALIDA EN EL UNLOGGED MENU
                System.out.print("Elija una de las opciones");
            }
        }
        keyboard.close(); //CIERRA TECLADO GLOBAL
        System.out.print(Config.getGOODBYE());
    }
        
    /**
     * Lee un entero desde el teclado.
     * 
     * @return El entero ingresado por el usuario
     */
    private static int scannerInt() { //METODO PARA PEDIR DATOS POR TECLADO
        int num = keyboard.nextInt();
        keyboard.nextLine();
        return num;
    }

    private static String scannerString() { //METODO PARA PEDIR DATOS POR TECLADO
        String string = keyboard.nextLine();
        return string;
    }

    /**
     * Maneja el menú y las opciones para usuarios autenticados.
     * 
     * @return true si el usuario elige salir del programa
     */
    private static boolean logged() { //MUESTRA LOGGED MENU Y PERMITE INTERACTUAR CON ÉL
        int auxLogged = -1;
        boolean exit = false; //DEFINE SI SE QUIERE VOLVER AL UNLOGGED MENU O SI FINALIZA EL PROGRAMA
        while (true) {
            System.out.print("Elija una de las opciones");
            System.out.println(Config.getLOGGED_MENU());
            System.out.println("Elección...");
            auxLogged = scannerInt();
            System.out.print(hr);
            if (auxLogged == 1) {
                System.out.println("\nElija una de las opciones");
                int option = -1;
                while (option != 0) {
                    System.out.println(Config.getMAZE_MENU());
                    System.out.println("Elección...");
                    option = scannerInt();
                    System.out.println(hr);
                    if (option == 1) {
                        System.out.println("Escriba el nombre del laberinto...");
                        String filenameString = scannerString();
                        System.out.println(hr);
                        if (maze.loadMaze(filenameString)) {
                            System.out.println(hr);
                            System.out.println("Archivo cargado con éxito");
                            System.out.println(hr);
                        } else {
                            System.out.println("Su archivo no existe");
                        }
                    } else if (option == 2) {
                        mazesList = mazesList();
                        int optionMazesList = -1;
                        while (optionMazesList != 0) {
                            System.out.println("Seleccione alguno de los laberintos disponibles");
                            System.out.println(hr);
                            for (int i = 0; i < mazesList.length; i++) {
                                System.out.println(i + 1 + ". " + mazesList[i]);
                            }
                            System.out.println("0. Salir");
                            System.out.println(hr);
                            System.out.println("Elección...");
                            optionMazesList = scannerInt();
                            if (optionMazesList >= 0 && optionMazesList <= mazesList.length) {
                                if (optionMazesList != 0) {
                                    if (maze.loadMaze(mazesList[optionMazesList-1])) {
                                        System.out.println("Archivo cargado con éxito");
                                        System.out.println(hr);
                                        optionMazesList = 0;
                                        option = 0;
                                    }
                                }
                            } else {
                                System.out.println("No has seleccionado una opción válida");
                            }
                        }
                    } else if (option == 0) {
                        option = 0;
                    } else {
                        System.out.println("\nNo se ha introducido un número valido");
                    }

                }
            } else if (auxLogged == 2) {
                System.out.print("\n"+maze.showMap());
                System.out.println(hr);
            } else if (auxLogged == 3) {
                char[][] validator = maze.getMap();
                int xStart = -1;
                int yStart = -1;
                int xEnd = -1;
                int yEnd = -1;
                boolean correctFormat = false;
                while (!correctFormat) {
                    System.out.println(hr);
                    System.out.println("Introduce unas coordenadas válidas");
                    System.out.println(hr);
                    System.out.println("Coordenada de entrada X...");
                    xStart = scannerInt();
                    System.out.println("Coordenada de entrada Y...");
                    yStart = scannerInt();
                    System.out.println("Coordenada de salida X...");
                    xEnd = scannerInt();
                    System.out.println("Coordenada de salida Y...");
                    yEnd = scannerInt();
                    if (xStart >= 0 && xStart < validator[0].length && yStart >= 0 && yStart < validator.length && xEnd >= 0 && xEnd < validator[0].length && yEnd >= 0 && yEnd < validator.length) {
                        correctFormat = true;
                        maze.setEntranceExit(xStart, yStart, xEnd, yEnd);
                    }
                }
            } else if (auxLogged == 4) { //OPCION 4 SIN DESARROLLAR
                System.out.print("PRÓXIMAMENTE");
                continue; //REPITE LOGGED MENU
            } else if (auxLogged == 5) { //INFORMACIÓN DEL USUARIO ACTUAL
                System.out.print(session.showUser());
                continue; //REPITE LOGGED MENU
            } else if (auxLogged == 6) { //CERRAR SESIÓN Y VOLVER AL UNLOGGED MENU
                session.logout();
                System.out.print("\nSesión Cerrada");
                break; //VUELVE A UNLOGGED MENU
            } else if (auxLogged == 0) { //SALIR DEL PROGRAMA
                exit = true;
                break; //VUELVE A ULOGGED MENU (PARA SALIR)
            } else { //VUELVE A PEDIR DATOS SI NO SE HA PULSADO UNA OPCIÓN VALIDA EN EL UNLOGGED MENU
                System.out.print("Elija una de las opciones");
            }
        }
        return exit; //DEVUELVE SI SE VUELVE A UNLOGGED MENU O SI FINALIZA EL PROGRAMA
    }
    public static String[] mazesList() {
        File mazesPath = new File(Config.getMAZES_PATH());
        return mazesPath.list();

    }
}
