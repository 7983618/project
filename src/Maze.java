import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Maze {
    //ARRAY DEL LABERINTO
    private char[][] map;
    //NOMBRE DEL ARCHIVO
    private String filename;
    //SI YA SE HA CARGADO UN LABERINTO
    private boolean loaded = false;
    //COORDENADAS
    private int jStart, iStart, jEnd, iEnd;
    //SI YA SE HAN ESTABLECIDO LAS COORDENADAS.
    private boolean coordinates = false;
    private boolean redWalls = false;
    private Stack<Coordinate> path = new Stack<>();

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
                    coordinates = false; //SE ESTABLECE A FALSE PORQUE TODAVÍA NO HABRAN ESTABLECIDO COORDENADAS DE ENTRADA Y SALIDA
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
            int countT = 10;
            maze.append("     J >                 ");
            for (int j = 0; j < map[0].length - 10; j++) {
                maze.append(countT/10 + " ");
                countT++;
            }
            maze.append("\n");
            maze.append("     ");
            int conutU = 0;
            for (int j = 0; j < map[0].length; j++) {
                if (conutU == 10) {
                    conutU = conutU - 10;
                }
                maze.append(conutU + " ");
                conutU++;
            }
            maze.append("\nI ");
            for (int i = 0; i < map.length; i++) {
                if (i >= 10) {
                    maze.append(i + " ");    
                } else {
                    maze.append(i + "  ");
                }
                for (int j = 0; j < map[0].length; j++) {
                    maze.append(map[i][j]+" ");
                }
                if (i == 0) {
                    maze.append("\nv ");
                } else {
                    maze.append("\n  ");
                }
            }
            return maze.toString().substring(0, maze.toString().length()-2);
        }
        //DEVUELVE MENSAJE DE ERROR
        return "NO SE HA CARGADO UN LABERINTO TODAVÍA";
    }
    //ESTABLECE COORDENADAS DE ENTRADA Y SALIDA
    public boolean setEntranceExit(int jStart, int iStart, int jEnd, int iEnd) { 
        if (loaded) { //SI ESTA CARGADO
            if (coordinates) { //SI YA SE HAN ESTABLECIDO COORDENADAS
                loadMaze(filename); //SE VUELVE A CARGAR PARA ELIMINAR LAS ENTRADAS ANTERIORES
            }
            //SE ESTABLECEN LAS ENTRADAS (posible cambio por como se nombran a los ejes)
            this.jStart = jStart;
            this.iStart = iStart;
            this.jEnd = jEnd;
            this.iEnd = iEnd;
            //ESTABLECE ENTRADA (S)TART
            map[iStart][jStart] = 'S';
            //ESTALECE SALIDA (E)ND
            map[iEnd][jEnd] = 'E';
            //SE HA MODIFICADO EL LABERINTO. POR LO QUE SI SE VULVE HA ESTALECER ENTRADA Y SALIDA NO SE VOLVERÁ A CONTAR EL EL TAMAÑO DEL LABERINTO EN EL FICHERO
            coordinates = true;
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
    public boolean isWall(int j, int i) {
        if (map[i][j] == '#') {
            return true;
        } else {
            return false;
        }
    }
    private boolean isRedWall(int j, int i) {
        if (map[i][j] == Config.RED_WAY_WALL) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isYellowWall(int j, int i) {
        if (map[i][j] == Config.YELLOW_WAY_WALL) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isYellowWay(int j, int i) {
        if (map[i][j] == '^' || map[i][j] == '<' || map[i][j] == 'v' || map[i][j] == '>') {
            return true;
        } else {
            return false;
        }
    }

    public void readWay() {
        if (coordinates) {
            path.push(new Coordinate(jStart, iStart));
            int chances = 0;
            for (int i = 0; i < 4; i++) {
                if (map[path.peek().rightI()][path.peek().rightJ()] == ' ') {
                    chances++;
                }
                Coordinate.rotate();
            }
            
            int times = 0;
            while (times != chances+1) {
                Coordinate last = path.peek();
                //CONTAMOS LAS VECES QUE VUELVE A LA ENTRADA. DEBEN SER EL NUMERO DE ALTERNATIVAS QUE TIENE EL INICIO + EL PRIMER PASO QUE ES LA ENTRADA
                if (last.i == iStart && last.j == jStart) {
                    times++;
                    clearPath(); //borramos el camino
                } if (times == chances+1) {
                    continue; //Asi no ejecuto el resto del codigo y puedo contar con la coordenada del paso actual
                }
                //SI TIENE LIBRE POR LA DERECHA GIRA, SINO SIGUE DE FRENTE (SI ESTA LIBRE, SINO GIRA EN SENTIDO ANTIHORARIO)
                if (!isWall(last.rightJ(), last.rightI())) {
                    Coordinate.rotate();
                    last.storedDirection = last.direction();
                }
                while (isWall(last.nextJ(), last.nextI())) {
                    Coordinate.rotatePi();
                    last.storedDirection = last.direction();
                }
                boolean create = true;
                //CON ESTO BORRAMOS LOS "PASOS" SI HEMOS VUELTO SOBRE NUESTOS PASOS
                if (path.size() >= 2) {
                    if (last.nextI() == path.get(path.size()-2).i && last.nextJ() == path.get(path.size()-2).j) {
                        if (!(last.i == iStart && last.j == jStart) && !(last.i == iEnd && last.j == jEnd)) {
                            map[last.i][last.j] = Config.RED_WAY_WALL;
                        } 
                        path.pop();
                        create = false;
                    }
                }
                //CREAMOS UN "PASO" SI NO ESTAMODS VOLVIENDO. SIEMPRE TENDREMOS LIBRE PORQUE NOS ASEGURAMOS CON LOS METODOS ANTERIORES
                if (create) {
                    path.push(new Coordinate(last.nextJ(), last.nextI()));
                    if (!(last.i == iStart && last.j == jStart) && !(last.i == iEnd && last.j == jEnd)) {
                        map[last.i][last.j] = last.storedDirection;
                    }  
                }
                try {
                    Thread.sleep(Config.speed); // 5000 milisegundos = 5 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(showMap());
            }
            redWalls = true; //EL MAPA SE HA MODIFICADO
            System.out.println(showMap());
            System.out.println(pathString());
            path = new Stack<>(); //EL CAMINO ES RESTAURADO
        }
    }
    
    public void yellowWay() {
        if (coordinates && redWalls) { //SI SE HAN ESTABLECIDO COORDENADAS Y SE HAN PUESTO MUROS
            path.push(new Coordinate(jStart, iStart));
            while (true) {
                Coordinate last = path.peek();
                if (!(last.i == iStart && last.j == jStart) && !(last.i == iEnd && last.j == jEnd)) {
                    last.storedDirection = last.direction();
                    map[last.i][last.j] = last.storedDirection;
                }
                if (last.i == iEnd && last.j == jEnd) {
                    break;
                }
                //SI TIENE LIBRE POR LA DERECHA GIRA, SINO SIGUE DE FRENTE (SI ESTA LIBRE, SINO GIRA EN SENTIDO ANTIHORARIO)
                if (!(isWall(last.rightJ(), last.rightI()) || isRedWall(last.j, last.i) || isYellowWay(last.rightJ(), last.rightI()) || isYellowWall(last.rightJ(), last.rightI()) )) {
                    Coordinate.rotate();
                    last.storedDirection = last.direction();
                }
                while (isWall(last.nextJ(), last.nextI()) || isRedWall(last.nextJ(), last.nextI()) || isYellowWay(last.nextJ(), last.nextI()) || isYellowWall(last.nextJ(), last.nextI())) {
                    if (path.size() >=2 && last.nextI() == path.get(path.size()-2).i && last.nextJ() == path.get(path.size()-2).j) {
                        break;
                    }
                    Coordinate.rotatePi();
                    last.storedDirection = last.direction();
                }
                //CON ESTO BORRAMOS LOS "PASOS" SI HEMOS VUELTO SOBRE NUESTOS PASOS
                boolean create = true; //SI BORRAMOS NO HACE FALTA CREAR UN PASO MÁS
                if (path.size() >= 2) {
                    if (last.nextI() == path.get(path.size()-2).i && last.nextJ() == path.get(path.size()-2).j) {
                        if (!(last.i == iStart && last.j == jStart) && !(last.i == iEnd && last.j == jEnd)) {
                            map[last.i][last.j] = Config.YELLOW_WAY_WALL;
                        } 
                        path.pop();
                        create = false;
                    }
                }
                //CREAMOS UN "PASO" SI NO ESTAMODS VOLVIENDO. SIEMPRE TENDREMOS LIBRE PORQUE NOS ASEGURAMOS CON LOS METODOS ANTERIORES
                if (create) {
                    path.push(new Coordinate(last.nextJ(), last.nextI()));
                    if (!(last.i == iStart && last.j == jStart) && !(last.i == iEnd && last.j == jEnd)) {
                        map[last.i][last.j] = last.storedDirection;
                    }  
                }
                try {
                    Thread.sleep(Config.speed); // 5000 milisegundos = 5 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(showMap());
            }
            clearYellowWayWalls();
            System.out.println(showMap());
            clearPath();
            System.out.println(pathString());
        }
    }
    private void clearYellowWayWalls() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == ':') {
                    map[i][j] = ' ';
                }
            }
        }
    }
    
    private void clearPath() {
        for (Coordinate coordinate : path) {
            if (!(coordinate.i == iStart && coordinate.j == jStart) && !(coordinate.i == iEnd && coordinate.j == jEnd)) {
                map[coordinate.i][coordinate.j] = ' ';   
            }
        }
    }
    private String pathString() {
        StringBuilder s = new StringBuilder();
        for (Coordinate coordinate : path) {
            s.append(path.indexOf(coordinate) + " " + coordinate.toString());
        }
        return s.toString();
    }
}
