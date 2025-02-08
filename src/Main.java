import java.util.Scanner;
public class Main {
    private static Session session = new Session();
    private static Scanner keyboard = new Scanner(System.in);
    public static void main(String[] args) {
        
        int auxUnlogged = -1;
        while (true) {
            System.out.println(Config.getUNLOGGED_MENU());
            auxUnlogged = scannerInt();
            if (auxUnlogged >= 0 && auxUnlogged <= 2) {
                if (auxUnlogged == 1) {
                    if (session.login(Session.request_login())) {
                        System.out.println("Sesión Iniciada");
                        logged();
                    } else {
                        System.out.println("El usuario o la contraseña no coinciden");
                    }
                } else if (auxUnlogged == 2) {
                    if (session.signup()) {
                        System.out.println("Usuario creado con éxito");
                        System.out.println("Sesión Iniciada");
                        logged();
                    } 
                } else {
                    break;
                }
            }
            System.out.println("Elija una de las opciones");
        }
       
        keyboard.close();
    }
    public static int scannerInt() {
        
        int num = keyboard.nextInt();
        keyboard.nextLine();
        return num;
    }
    public static void logged() {
        if (session.isLogged()) {
            int auxLogged = -1;
            while (auxLogged != 0) {
                System.out.println(Config.getLOGGED_MENU());
                auxLogged = scannerInt();
                if (auxLogged >= 1 && auxLogged <= 4) {
                    System.out.println("PRÓXIMAMENTE");
                } else if (auxLogged == 5) {
                    System.out.println(session.getUser().toString());
                } else if (auxLogged == 6) {
                    session = new Session();
                }    

            }
        }
    }
}
