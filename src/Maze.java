import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    private char[][] map;
    private String filename;
    private boolean loaded = false;
    private int startI;
    private int startJ;
    private int endI;
    private int endJ;

    private static Scanner mazeReader = new Scanner(System.in);

    public Maze() {
    }

    public boolean loadMaze(String mazeFilename) {
        File mazeFile = new File(Config.getMAZES_PATH()+mazeFilename);
        if (mazeFile.exists()) {
            try {
                Scanner mazeReader = new Scanner(mazeFile);
                int xCounter = mazeReader.nextLine().length();
                int yCounter = 1;
                while (mazeReader.hasNextLine()) {
                    yCounter++; 
                    mazeReader.nextLine();
                }
                System.out.println("X: " + xCounter + " Y: " + yCounter);
                mazeReader.close();
                return true;
                /*
                mazeReader = new Scanner(mazeFile);
                while (mazeReader.hasNextLine()) {
                    String line = mazeReader.nextLine();
                    System.out.println(line);
                }
                mazeReader.close();
                */
            } catch (FileNotFoundException e) {
                System.out.println("Se ha producido un error");
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("No existe");
            return false;
        }
        
    }
    
    
}
