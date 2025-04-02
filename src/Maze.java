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
    private static boolean modified;

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
                map = new char[yCounter][xCounter];
                mazeReader.close();
                
                mazeReader = new Scanner(mazeFile);
                for (int i = 0; i < yCounter; i++) {
                    map[i] = mazeReader.nextLine().toCharArray(); 
                }
                mazeReader.close();
                loaded = true;
                filename = mazeFilename;
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("Se ha producido un error");
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
        
    }
    public String showMap() {
        if (loaded) {
            StringBuilder maze = new StringBuilder();
            for (int i = 0; i < map.length; i++) {
                if (i == 0) {
                    maze.append("  ");
                    for (int j = 0; j < map[0].length; j++) {
                        maze.append(j + " ");
                    }
                    maze.append("\n");
                }
                maze.append(i + " ");
                for (int j = 0; j < map[0].length; j++) {
                    maze.append(map[i][j]+" ");
                }
                maze.append("\n");
            }
            return maze.toString();
        }
        return "NO SE HA CARGADO UN LABERINTO TODAVÍA";
    }
    public boolean setEntranceExit(int xStart, int yStart, int xEnd, int yEnd) {
        if (modified) {
            loadMaze(filename);
        }
        if (loaded) {
            startI = yStart;
            startJ = xStart;
            endI = yEnd;
            endJ = xEnd;
            map[startI][startJ] = 'S';
            map[endI][endJ] = 'E';
            modified = true;
            return true;
        } else {
            return false;
        }
    }

    public char[][] getMap() {
        return map;
    }
    
    
    
}
