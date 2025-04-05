import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maze {
    //ARRAY DEL LABERINTO
    private char[][] map;
    //NOMBRE DEL ARCHIVO
    private String filename;
    //SI YA SE HA CARGADO UN LABERINTO
    private boolean loaded = false;
    //COORDENADAS
    private int startI;
    private int startJ;
    private int endI;
    private int endJ;
    //SI YA SE HAN ESTABLECIDO LAS COORDENADAS.
    private boolean modified = false;

    public Maze() {
    }
    //LEE EL FICHERO Y CREA EL MAPA EN EL ARRAY
    public boolean loadMaze(String mazeFilename) {
        File mazeFile = new File(Config.getMAZES_PATH()+mazeFilename);
        //SI EXISTE EL ARCHIVO
        if (mazeFile.exists()) {
            try {
                Scanner mazeReader = new Scanner(mazeFile);
                if (!mazeFilename.equals(filename)) { //SI SE VA A CARGAR UN ARCHIVO DIFERENTE AL QUE YA SE ENCONTRABA CARGADO 
                    //SE CUENTAN LAS LINEA Y CARACTERES PARA FORMAR EL ARRAY
                    int xCounter = mazeReader.nextLine().length();
                    int yCounter = 1;
                    while (mazeReader.hasNextLine()) {
                        yCounter++; 
                        mazeReader.nextLine();
                    }
                    //SE CREA EL ARRAY QUE CONTENDRÁ EL MAPA
                    map = new char[yCounter][xCounter];
                    modified = false; //SE ESTABLECE A FALSE PORQUE TODAVÍA NO HABRAN ESTABLECIDO COORDENADAS DE ENTRADA Y SALIDA
                    mazeReader.close();
                    
                    mazeReader = new Scanner(mazeFile);
                }
                //CARGAMOS LA INFORMACIÓN DEL LABERINTO
                for (int i = 0; mazeReader.hasNextLine(); i++) {
                    map[i] = mazeReader.nextLine().toCharArray(); 
                }
                mazeReader.close();
                //SE ACTUALIZA LA PROPIEDAD CARGADO Y EL NOMBRE DEL FICHERO DEL LABERINTO ACTUAL
                loaded = true;
                filename = mazeFilename;
                //HA SIDO CARGADO CORRECTAMENTE
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("Se ha producido un error");
                e.printStackTrace();
                //HUBO UNA EXCEPCIÓN (SEGURAMENTE EN SI EXISTÍA)
                return false;
            }
        } else { //NO EXISTE EL FICHERO
            return false;
        }
        
    }
    public String showMap() {
        if (loaded) { //SI YA HAY UN LABERINTO CARGADO
            //SE CREA Y DEVUELVE UN STRING DEL LABERINTO
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
            return maze.toString().substring(0, maze.toString().length()-1);
        }
        //DEVUELVE MENSAJE DE ERROR
        return "NO SE HA CARGADO UN LABERINTO TODAVÍA";
    }
    //ESTABLECE COORDENADAS DE ENTRADA Y SALIDA
    public boolean setEntranceExit(int xStart, int yStart, int xEnd, int yEnd) { 
        if (loaded) { //SI ESTA CARGADO
            if (modified) { //SI YA SE HAN ESTABLECIDO COORDENADAS
                loadMaze(filename); //SE VUELVE A CARGAR PARA ELIMINAR LAS ENTRADAS ANTERIORES
            }
            //SE ESTABLECEN LAS ENTRADAS (posible cambio por como se nombran a los ejes)
            startI = yStart;
            startJ = xStart;
            endI = yEnd;
            endJ = xEnd;
            //ESTABLECE ENTRADA (S)TART
            map[startI][startJ] = 'S';
            //ESTALECE SALIDA (E)ND
            map[endI][endJ] = 'E';
            //SE HA MODIFICADO EL LABERINTO. POR LO QUE SI SE VULVE HA ESTALECER ENTRADA Y SALIDA NO SE VOLVERÁ A CONTAR EL EL TAMAÑO DEL LABERINTO EN EL FICHERO
            modified = true;
            return true;
        } else { //NO SE HA CARGADO UN LABERINTO PREVIAMENTE
            return false;
        }
    }
    //DEVUELVE SI SE HA CARGADO UN LABERINTO
    public boolean isLoaded() {
        return loaded;
    }
    //DEVULVE EL TAMAÑO DEL EJE X
    public int getXSize() {
        return map[0].length;
    }
    //DEVULVE EL TAMAÑO DEL EJE Y
    public int getYSize() {
        return map.length;
    }
    //DEVUELVE SI LAS COORDENADAS SON UN MURO
    public boolean isWall(int x, int y) {
        if (map[x][y] == '#') {
            return true;
        } else {
            return false;
        }
    }
}
