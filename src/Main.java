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
    private static String hr = "-----------------------------";
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
        //ES LA VARIABLE QUE ALMACENA LA ELECCIÓN DEL USUARIO
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
                //ES LA VARIABLE QUE ALMACENA LA ELECCIÓN DEL USUARIO
                int option = -1;
                while (option != 0) {
                    System.out.println(Config.getMAZE_MENU()); //MENU DE CARGA DE LABERINTO
                    
                    System.out.println("Elección...");
                    option = scannerInt();
                    System.out.println(hr);
                    
                    if (option == 1) { //PIDE NOMBRE DEL LABERINTO Y LO INTENTA CARGAR
                        System.out.println("Escriba el nombre del laberinto...");
                        String filenameString = scannerString();
                        System.out.println(hr);
                        if (maze.loadMaze(filenameString)) { //EXITO AL CARGAR
                            System.out.println("Archivo cargado con éxito");
                            System.out.println(hr);
                            option = 0;
                        } else { //EL ARCHIVO NO EXISTE O HUBO UNA EXCEPCIÓN
                            System.out.println("Su archivo no existe");
                        }
                    } else if (option == 2) { //MUESTRA LA LISTA DE LABERINTOS DISPONIBLES Y PERMITE AL USUARIO ELEGIR ALGUNO
                        mazesList = mazesList(); //OBTIENE LA LISTA DE LABERINTOS
                        int optionMazesList = -1;
                        while (optionMazesList != 0) {
                            //MENU CON LOS LABERINTOS DISPONIBLES
                            System.out.println("Seleccione alguno de los laberintos disponibles");
                            System.out.println(hr);
                            for (int i = 0; i < mazesList.length; i++) {
                                System.out.println(i + 1 + ". " + mazesList[i]);
                            }
                            System.out.println("0. Salir");
                            System.out.println(hr);
                            //ELECCIÓN DEL USUARIO
                            System.out.println("Elección...");
                            optionMazesList = scannerInt();
                            //COMPRUEBA SI LA OPCIÓN ES VALIDA
                            if (optionMazesList >= 0 && optionMazesList <= mazesList.length) {
                                if (optionMazesList != 0) { 
                                    //CARGA EL LABERINTO CORRESPONDIENTE
                                    if (maze.loadMaze(mazesList[optionMazesList-1])) {
                                        System.out.println(hr);
                                        System.out.println("Archivo cargado con éxito");
                                        System.out.println(hr);
                                        //SALE AL MENU PRINCIPAL YA TENIENDO EL LABERINTO CARGADO
                                        optionMazesList = 0;
                                        option = 0;
                                    }
                                }
                            } else { //LA OPCIÓN INTRODUCIDA NO ES VALIDA
                                System.out.println(hr);
                                System.out.println("No has seleccionado una opción válida");
                                System.out.println(hr);
                            }
                        }
                    } else { //SI NO SE HA INTRODUCIDO UNA OPCIÓN VÁLIDA EN EL MENU DE CARGAR LABERINTO
                        System.out.println("No se ha introducido una opción valida");
                    }

                }
            } else if (auxLogged == 2) { 
                System.out.println("\n"+maze.showMap()); //MUESTRA EL MAPA DEL LABERINTO SI ESTA CARGADO, SINO MUESTRA MENSAJE DE QUE FALTA CARGAR
                System.out.println(hr);
            } else if (auxLogged == 3) { //PIDE COORDENADAS DE ENTRADA Y SALIDA DEL LABERINTO CARGADO
                //COMPRUEBA SI ESTA CARGADO
                if (maze.isLoaded()) {
                    int xStart = -1;
                    int yStart = -1;
                    int xEnd = -1;
                    int yEnd = -1;
                    //PEDIMOS COORDENADAS HASTA QUE SEAN VÁLIDAS
                    boolean correctFormat = false;
                    while (!correctFormat) {
                        System.out.println("\nIntroduce unas coordenadas válidas");
                        System.out.println("-Deben existir en el mapa");
                        System.out.println("-No deben ser un muro");
                        System.out.println("-No pueden ser iguales las coordenadas de salida y entrada");
                        System.out.println(hr);
                        System.out.println("Coordenada de entrada (Start) X...");
                        xStart = scannerInt();
                        System.out.println("Coordenada de entrada (Start) Y...");
                        yStart = scannerInt();
                        System.out.println("Coordenada de salida (End) X...");
                        xEnd = scannerInt();
                        System.out.println("Coordenada de salida (End) Y...");
                        yEnd = scannerInt();
                        //COMPROBAMOS SI SON VALIDAS
                        if (xStart >= 0 && xStart < maze.getXSize() && yStart >= 0 && yStart < maze.getYSize() && xEnd >= 0 && xEnd < maze.getXSize() && yEnd >= 0 && yEnd < maze.getYSize() && !maze.isWall(xStart, yStart) && !maze.isWall(xEnd, yEnd) && !(xStart==xEnd && yStart==yEnd)) {
                            //DEJAMOS DE PEDIR COORDENADAS Y ESTABLECEMOS LAS QUE HEMOS RECIVIDO
                            correctFormat = true;
                            maze.setEntranceExit(xStart, yStart, xEnd, yEnd);
                        }
                        System.out.print(hr);
                    }
                } else {
                    System.out.println("\nNO SE HA CARGADO UN LABERINTO TODAVÍA");
                    System.out.println(hr);
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
                System.out.println();
                exit = true;
                break; //VUELVE A ULOGGED MENU (PARA SALIR)
            } else { //VUELVE A PEDIR DATOS SI NO SE HA PULSADO UNA OPCIÓN VALIDA EN EL UNLOGGED MENU
                System.out.print("\n");
            }
        }
        return exit; //DEVUELVE SI SE VUELVE A UNLOGGED MENU O SI FINALIZA EL PROGRAMA
    }
    public static String[] mazesList() {
        File mazesPath = new File(Config.getMAZES_PATH());
        return mazesPath.list();

    }
}
