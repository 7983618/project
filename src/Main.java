//CADA VEZ QUE SE AHCE UN COMMIT HAY QUE ACTUALIZAR LA DIRECCIÓN DE LOS USUARIOS (por alguna razón)
import java.util.Scanner;
public class Main {
    private static Session session = new Session();
    private static Scanner keyboard = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println();
        System.out.print(Config.getWELCOME());
        int auxUnlogged = -1;
        while (true) { //MUESTRA UNLOGGED MENU HASTA QUE SE SALGA
            //SOLICÍTA ELECCIÓN
            System.out.println(Config.getUNLOGGED_MENU());
            System.out.println("Elección...");
            auxUnlogged = scannerInt();
            System.out.println("------------------");
            
            if (auxUnlogged == 1) { //INTENTA INICIAR SESIÓN CON USUARIO Y CONTRASEÑA
                if (session.login(Session.request_login())) { //INICIO DE SESIÓN EXITOSO
                    System.out.println("------------------");
                    System.out.print("Sesión Iniciada");
                    if (logged()) { //MUESTRA LOGGED MENU Y PERMITE INTERACTUAR CON ÉL
                        break; //FINALIZA EL PROGRAMA SI SE PULSA 0 EN EL LOGGED MENU
                    } else {
                        continue; //CONTINUA EL PROGRAMA SI SE PUSA 6 EN EL LOGGED MENU
                    }
                } else { //FRACASO EN INICIO DE SESIÓN
                    System.out.println("------------------");
                    System.out.print("El usuario o la contraseña no coinciden");
                    continue;
                }
            } else if (auxUnlogged == 2) { //INICIA PROCCESO DE REGISTRO
                if (session.signup()) { //EXITO AL REGISTRAR USUARIO E INICIAR SESIÓN CON ÉL
                    System.out.println("Usuario creado con éxito");
                    System.out.print("Sesión Iniciada");
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
        
    
    private static int scannerInt() { //METODO PARA PEDIR DATOS POR TECLADO
        int num = keyboard.nextInt();
        keyboard.nextLine();
        return num;
    }
    private static boolean logged() { //MUESTRA LOGGED MENU Y PERMITE INTERACTUAR CON ÉL
        int auxLogged = -1;
        boolean exit = false; //DEFINE SI SE QUIERE VOLVER AL UNLOGGED MENU O SI FINALIZA EL PROGRAMA
        while (true) {
            System.out.println(Config.getLOGGED_MENU());
            System.out.println("Elección...");
            auxLogged = scannerInt();
            System.out.println("------------------");
            if (auxLogged >= 1 && auxLogged <= 4) { //OPCIONES DEL 1-4 SIN DESARROLLAR
                System.out.print("PRÓXIMAMENTE");
                continue; //REPITE LOGGED MENU
            } else if (auxLogged == 5) { //INFORMACIÓN DEL USUARIO ACTUAL
                System.out.print(session.showUser());
                continue; //REPITE LOGGED MENU
            } else if (auxLogged == 6) { //CERRAR SESIÓN Y VOLVER AL UNLOGGED MENU
                session.logout();
                System.out.print("Sesión Cerrada");
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
}
