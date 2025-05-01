import java.io.File;
import java.util.Scanner;
/**
 * Clase principal que maneja la interfaz de usuario y el flujo del programa.
 * 
 * @author Santiago Quispe
 * @version 0.2.0
 */
public class Main {
    /** Sessión del programa principal */
    private static Session session = new Session();
    /** Objeto Scanner utilizado en toda la clase */
    private static Scanner keyboard = new Scanner(System.in);
    private static Maze maze = new Maze();
    /**
     * Método principal que inicia el programa.
     * 
     * @param args Argumentos de línea de comando (no utilizados)
     */
    public static void main(String[] args) {
        
        System.out.println(Config.getWELCOME());
        int option = -1;
        while (!(option >= 0 && option <= 2)) { //MUESTRA UNLOGGED MENU HASTA QUE SE SALGA
            //SOLICÍTA ELECCIÓN
            System.out.println(Config.getUNLOGGED_MENU());
            option = scannerInt();
            if (option == 1) { //INTENTA INICIAR SESIÓN CON USUARIO Y CONTRASEÑA
                if (session.login(Session.requestLogin())) { //INICIO DE SESIÓN EXITOSO
                    System.out.println(Config.hr + "Sesión Iniciada");
                    if (logged()) { //MUESTRA LOGGED MENU Y PERMITE INTERACTUAR CON ÉL
                        break; //FINALIZA EL PROGRAMA SI SE PULSA 0 EN EL LOGGED MENU
                    } else {
                        option = -1; //CONTINUA EL PROGRAMA SI SE PUSA 6 EN EL LOGGED MENU
                    }
                } else { //FRACASO EN INICIO DE SESIÓN
                    System.out.println(Config.hr + "El usuario o la contraseña no coinciden");
                    option = -1;
                }
            } else if (option == 2) { //INICIA PROCCESO DE REGISTRO
                if (session.signup()) { //EXITO AL REGISTRAR USUARIO E INICIAR SESIÓN CON ÉL
                    System.out.println(Config.hr + "Usuario creado con éxito");
                    System.out.println(Config.hr + "Sesión Iniciada");
                    if (logged()) { //MUESTRA LOGGED MENU Y PERMITE INTERACTUAR CON ÉL
                        break; //FINALIZA EL PROGRAMA SI SE PULSA 0 EN EL LOGGED MENU
                    } else {
                        option = -1; //CONTINUA EL PROGRAMA SI SE PUSA 6 EN EL LOGGED MENU
                    }
                } 
            } else if (!(option >= 0 && option <= 2)) { //VUELVE A PEDIR DATOS SI NO SE HA PULSADO UNA OPCIÓN VALIDA EN EL UNLOGGED MENU
                System.out.println(Config.hr + "Elija una opcion valida");
            }
        }
        keyboard.close(); //CIERRA TECLADO GLOBAL
        System.out.println(Config.getGOODBYE());
    }
        

    /**
     * Maneja el menú y las opciones para usuarios autenticados.
     * 
     * @return true si el usuario elige salir del programa
     */
    private static boolean logged() { //MUESTRA LOGGED MENU Y PERMITE INTERACTUAR CON ÉL
        //ES LA VARIABLE QUE ALMACENA LA ELECCIÓN DEL USUARIO
        int option = -1;
        boolean exit = false; //DEFINE SI SE QUIERE VOLVER AL UNLOGGED MENU O SI FINALIZA EL PROGRAMA
        while (option != 0) {
            System.out.println(Config.getLOGGED_MENU());
            option = scannerInt();
            if (option == 1) {
                loadMaze();
            } else if (option == 2) { 
                System.out.println(Config.hr + maze.showMap()); //MUESTRA EL MAPA DEL LABERINTO SI ESTA CARGADO, SINO MUESTRA MENSAJE DE QUE FALTA CARGAR
            } else if (option == 3) { //PIDE COORDENADAS DE ENTRADA Y SALIDA DEL LABERINTO CARGADO
                setCoordinates();
            } else if (option == 4) { //OPCION 4 SIN DESARROLLAR
                maze.readWay();
            } else if (option == 5) { //INFORMACIÓN DEL USUARIO ACTUAL
                System.out.println(Config.hr + session.showUser());
            } else if (option == 6) { //CERRAR SESIÓN Y VOLVER AL UNLOGGED MENU
                maze = new Maze(); //SE ELIMINA LOS DATOS DEL OBJETO MAZE. 
                session.logout();
                System.out.println(Config.hr + "Sesión Cerrada");
                option = 0; //VUELVE A UNLOGGED MENU
            } else if (option == 0) { //SALIR DEL PROGRAMA
                exit = true; //VUELVE A ULOGGED MENU (PARA SALIR)
            } else if (!(option >= 0 && option <= 6)) { //VUELVE A PEDIR DATOS SI NO SE HA PULSADO UNA OPCIÓN VALIDA EN EL UNLOGGED MENU
                System.out.println(Config.hr + "Elija una opcion valida");
            }
        }
        return exit; //DEVUELVE SI SE VUELVE A UNLOGGED MENU O SI FINALIZA EL PROGRAMA
    }

    private static void loadMaze() {
        //ES LA VARIABLE QUE ALMACENA LA ELECCIÓN DEL USUARIO
        int option = -1;
        while (option != 0) {
            System.out.println(Config.getMAZE_MENU()); //MENU DE CARGA DE LABERINTO
            option = scannerInt();
            if (option == 1) { //PIDE NOMBRE DEL LABERINTO Y LO INTENTA CARGAR
                System.out.println(Config.hr + "Escriba el nombre del laberinto...");
                String filenameString = scannerString();
                if (maze.loadMaze(filenameString)) { //EXITO AL CARGAR
                    System.out.println(Config.hr + "Archivo cargado con éxito");
                    option = 0;
                } else { //EL ARCHIVO NO EXISTE O HUBO UNA EXCEPCIÓN
                    System.out.println(Config.hr + "Su archivo no existe");
                }
            } else if (option == 2) { //MUESTRA LA LISTA DE LABERINTOS DISPONIBLES Y PERMITE AL USUARIO ELEGIR ALGUNO
                listMazes();
                if (maze.isLoaded()) { //SALIMOS DEL MENU SI SE HA SELECCIONADO UN LABERINTO DISPONIBLE
                    option = 0;
                }
            } else { //SI NO SE HA INTRODUCIDO UNA OPCIÓN VÁLIDA EN EL MENU DE CARGAR LABERINTO
                System.out.println(Config.hr + "No se ha introducido una opción valida");
            }
        }
    }

    private static void listMazes() {
        File mazesPath = new File(Config.getMAZES_PATH());
        String[] mazesList = mazesPath.list(); //OBTIENE LA LISTA DE LABERINTOS
        int option = -1;
        while (option != 0) { //MENU CON LOS LABERINTOS DISPONIBLES
            System.out.print(Config.hr + "Seleccione alguno de los laberintos disponibles\n" + Config.hr);
            for (int i = 0; i < mazesList.length; i++) {
                System.out.println(i + 1 + ". " + mazesList[i]);
            }
            System.out.println("0. Salir");
            //ELECCIÓN DEL USUARIO
            System.out.println(Config.hr + "Elección...");
            option = scannerInt();
            //COMPRUEBA SI LA OPCIÓN ES VALIDA
            if (option >= 0 && option <= mazesList.length) {
                if (option != 0) { 
                    //CARGA EL LABERINTO CORRESPONDIENTE
                    if (maze.loadMaze(mazesList[option-1])) {
                        System.out.println(Config.hr + "Archivo cargado con éxito");
                        //SALE AL MENU PRINCIPAL YA TENIENDO EL LABERINTO CARGADO
                        option = 0;
                    }
                }
            } else { //LA OPCIÓN INTRODUCIDA NO ES VALIDA
                System.out.println(Config.hr + "No has seleccionado una opción válida");
            }
        }
    }
    
    private static void setCoordinates() {
        //COMPRUEBA SI ESTA CARGADO
        if (maze.isLoaded()) {
            int jStart = -1;
            int iStart = -1;
            int jEnd = -1;
            int iEnd = -1;
            //PEDIMOS COORDENADAS HASTA QUE SEAN VÁLIDAS
            boolean correctFormat = false;
            while (!correctFormat) {
                System.out.println(Config.START_EXIT_CONDITIONS);
                System.out.println(Config.hr + "Coordenada de entrada (Start) J...");
                jStart = scannerInt();
                System.out.println("Coordenada de entrada (Start) I...");
                iStart = scannerInt();
                System.out.println("Coordenada de salida (End) J...");
                jEnd = scannerInt();
                System.out.println("Coordenada de salida (End) I...");
                iEnd = scannerInt();
                //COMPROBAMOS SI SON VALIDAS
                if (jStart >= 0 && jStart < maze.getXSize() && iStart >= 0 && iStart < maze.getYSize() && jEnd >= 0 && jEnd < maze.getXSize() && iEnd >= 0 && iEnd < maze.getYSize() && !maze.isWall(jStart, iStart) && !maze.isWall(jEnd, iEnd) && !(jStart==jEnd && iStart==iEnd)) {
                    //DEJAMOS DE PEDIR COORDENADAS Y ESTABLECEMOS LAS QUE HEMOS RECIVIDO
                    correctFormat = true;
                    maze.setEntranceExit(jStart, iStart, jEnd, iEnd);
                }
                
            }
        } else {
            System.out.println("NO SE HA CARGADO UN LABERINTO TODAVÍA");
            
        }
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
}
