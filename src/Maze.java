import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    //SI YA HA PASADO EL CAMINO ROJO SOBRE EL MAPA
    private boolean redWalls = false;
    //AQUI ALMACENAMOS TODOS LOS CAMINOS POSIBLES
    private ArrayList<Stack<Coordinate>> additionalPaths = new ArrayList<>();
    //CONSTRUCTOR VACÍO
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
    //STRING CON EL MAPA FORMATEADO. PARA VER EL RESULTADO FINAL Y EL PROCESO
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
                additionalPaths = new ArrayList<>();
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
    //DEVUELVE SI YA SE HAN ESTABLECIDO LAS COORDENADAS
    public boolean isCoordinates() {
        return coordinates;
    }
    //DEVUELVE SI EL CAMINO ROJO YA HA PUESTO SUS "MUROS" EN LOS CAMINOS SIN SALIDA
    public boolean isRedWalls() {
        return redWalls;
    }
    //DEVUELVE SI LAS COORDENADAS SON UN MURO DEL LABERINTO ORIGINAL "#"
    public boolean isWall(int j, int i) {
        if (map[i][j] == '#') {
            return true;
        } else {
            return false;
        }
    }
    //DEVUELVE SI LAS COORDENADAS SON UN MURO PUESTO POR EL CAMINO ROJO AL DESCARTAR CAMINOS SIN SALIDA
    private boolean isRedWall(int j, int i) {
        if (map[i][j] == Config.RED_WAY_WALL) {
            return true;
        } else {
            return false;
        }
    }
    //DEVUELVE SI LAS COORDENADAS SON UN MURO PUESTO POR EL CAMINO AMARILLO AL DESCARTAR CAMINOS SIN SALIDA
    private boolean isYellowWall(int j, int i) {
        if (map[i][j] == Config.YELLOW_WAY_WALL) {
            return true;
        } else {
            return false;
        }
    }
    //DEVUELVE SI LAS COORDENADAS FORMA PARTE DEL RECORRIDO DEL CAMINO AMARILLO
    private boolean isYellowWay(int j, int i) {
        if (map[i][j] == '^' || map[i][j] == '<' || map[i][j] == 'v' || map[i][j] == '>') {
            return true;
        } else {
            return false;
        }
    }
    //DEVUELVE SI LAS COORDENADAS SON UN MURO DE DESCARTE. ESTOS FUNCIONAN PARA OBLIGAR AL CAMINO A TOMAR OTRA RUTA Y ASÍ ALMACENAR TODOS LOS CAMINOS POSIBLES
    private boolean isBlockChanceWall(int j, int i) {
        if (map[i][j] == Config.BLOCK_CHANCE_WALL) {
            return true;
        } else {
            return false;
        }
    }
    //SI LA COORDENADA ES LA COORDENADA DE ENTRADA
    private boolean isStartCoordinate(Coordinate coordinate) {
        if (coordinate.i == iStart && coordinate.j == jStart) {
            return true;
        }
        return false;
    }
    //SI LA COORDENADA ES LA COORDENADA DE SALIDA
    private boolean isEndCoordinate(Coordinate coordinate) {
        if (coordinate.i == iEnd && coordinate.j == jEnd) {
            return true;
        }
        return false;
    }
    //DEVUELVE LA CANTIDAD DE VÍAS LIBRES QUE TIENE UNA COORDENADA
    private int chances(Coordinate coordinate) {
        int chances = 0;
        if (map[coordinate.i-1][coordinate.j] == ' ') {
            chances++;
        }
        if (map[coordinate.i][coordinate.j-1] == ' ') {
            chances++;
        }
        if (map[coordinate.i+1][coordinate.j] == ' ') {
            chances++;
        }
        if (map[coordinate.i][coordinate.j+1] == ' ') {
            chances++;
        }
        return chances;
    }
    //ES EL PRIMER "RECORRIDO" PARA LA RESOLUCIÓN DEL LABERINTO. LA FUNCIÓN DE ESTE RECORRIDO NO ES ENCONTRAR LA SALIDA. SINO DESCARTAR LOS CAMINOS SIN SALIDA CREADO LOS "MUROS ROJOS"
    private void redWay() {
        //AQUI SE ALMACENA EL RECORRIDO
        Stack<Coordinate> path = new Stack<>();
        //SOLO SE EJEUTA SI SE HAN ESTABLEIDO LAS COORDENADAS DE ENTRADA Y SALIDA
        if (coordinates) {
            //CREAMOS LA PRIMERA COORDENADA Y LO METEMOS EN EL CAMINO
            Coordinate first = new Coordinate(jStart, iStart);
            path.push(first);
            //ALMACENAMOS LAS VIAS LIBRES QUE TIENE LA ENTRADA PORQUE SON LAS VECES QUE EL CAMINO ROJO TENDRÁ QUE VOLVER A LA ENTRADA
            int chances = chances(first);
            //CONTADOR DE LAS VECES QUE VUELVE A LA ENTRADA
            int times = 0;
            //CON ESTO LE INDICAMOS SI DEBE CREAR MUROS. CON ESTO PODEMOS DESACTIVAR LA CREACIÓN DE MUROS SI ENCUENTRA LA SALIDA PARA QUE NO LA CONFUNDA CON UN CAMINO SIN SALIDA Y LA BLOQUEE
            boolean makewalls = true;
            //MIENTRAS NO HAYA ESTADO EN LA ENTRADA LA CANIDAD DE VIAS LIBRE QUE TIENE LA ENTRADA + LA PRIMERA DEL PRINCIPIO
            while (times != chances+1) {
                //LA ÚLTIMA COORDENADA DEL CAMINO
                Coordinate last = path.peek();
                //AUMENTAMOS EL CONTADOR SI LA ULTIMA ES LA ENTRADA
                if (isStartCoordinate(last)) {
                    times++;
                    clearPath(path); //borramos el camino
                } if (times == chances+1) {
                    continue; //Asi no ejecuto el resto del codigo y puedo contar con la coordenada del paso actual
                }
                //SI TIENE LIBRE POR LA DERECHA GIRA, SINO SIGUE DE FRENTE (SI ESTA LIBRE, SINO GIRA EN SENTIDO ANTIHORARIO)
                if (isEndCoordinate(last)) {
                    makewalls = false;
                }
                if (!(isWall(last.rightJ(), last.rightI()))) {
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
                    Coordinate penultimate = path.get(path.size()-2);
                    if (last.nextI() == penultimate.i && last.nextJ() == penultimate.j) {
                        if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                            if (makewalls) {
                                map[last.i][last.j] = Config.RED_WAY_WALL;
                            } else {
                                map[last.i][last.j] = ' ';
                            }
                        } 
                        path.pop();
                        create = false;
                    }
                }
                //CREAMOS UN "PASO" SI NO ESTAMODS VOLVIENDO. SIEMPRE TENDREMOS LIBRE PORQUE NOS ASEGURAMOS CON LOS METODOS ANTERIORES
                if (create) {
                    path.push(new Coordinate(last.nextJ(), last.nextI()));
                    if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                        map[last.i][last.j] = last.storedDirection;
                        makewalls = true;
                    }  
                }
                try {
                    Thread.sleep(Config.RED_WAY_SPEED); // 5000 milisegundos = 5 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(showMap());
            }
            redWalls = true; //EL MAPA SE HA MODIFICADO
            System.out.println(showMap());
            System.out.println(pathString(path));
        }
    }
    private Stack<Coordinate> yellowWay() {
        System.out.println("CAMINO AMARILLO");
        Stack<Coordinate> path = new Stack<>();
        boolean clear = false;
        if (coordinates && redWalls) { //SI SE HAN ESTABLECIDO COORDENADAS Y SE HAN PUESTO MUROS
            path.push(new Coordinate(jStart, iStart));
            int times = 0;
            
            while (true && times < 4) {
                Coordinate last = path.peek();
                if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                    last.storedDirection = last.direction();
                    map[last.i][last.j] = last.storedDirection;
                }
                if (isEndCoordinate(last)) {
                    break;
                }
                //SI TIENE LIBRE POR LA DERECHA GIRA, SINO SIGUE DE FRENTE (SI ESTA LIBRE, SINO GIRA EN SENTIDO ANTIHORARIO)
                if (!(isWall(last.rightJ(), last.rightI()) || isRedWall(last.j, last.i) || isYellowWay(last.rightJ(), last.rightI()) || isYellowWall(last.rightJ(), last.rightI()) || isBlockChanceWall(last.rightJ(), last.rightI()))) {
                    Coordinate.rotate();
                    last.storedDirection = last.direction();
                }
                
                times = 0;
                while ((isWall(last.nextJ(), last.nextI()) || isRedWall(last.nextJ(), last.nextI()) || isYellowWay(last.nextJ(), last.nextI()) || isYellowWall(last.nextJ(), last.nextI()) || isBlockChanceWall(last.nextJ(), last.nextI())) && times < 4) {
                    if (path.size() >=2) {
                        Coordinate penultimate = path.get(path.size()-2);
                        if (last.nextI() == penultimate.i && last.nextJ() == penultimate.j) {
                            break;
                        }
                            
                    }
                    Coordinate.rotatePi();
                    times++;
                    last.storedDirection = last.direction();
                }
                if (times >= 4) {
                    clear = true;
                }
                //CON ESTO BORRAMOS LOS "PASOS" SI HEMOS VUELTO SOBRE NUESTOS PASOS
                boolean create = true; //SI BORRAMOS NO HACE FALTA CREAR UN PASO MÁS
                if (path.size() >= 2) {
                    Coordinate penultimate = path.get(path.size()-2);
                    if (last.nextI() == penultimate.i && last.nextJ() == penultimate.j) {
                        
                        if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                            map[last.i][last.j] = Config.YELLOW_WAY_WALL;
                        }
                        
                        path.pop();
                        create = false;
                    }
                }
                //CREAMOS UN "PASO" SI NO ESTAMODS VOLVIENDO. SIEMPRE TENDREMOS LIBRE PORQUE NOS ASEGURAMOS CON LOS METODOS ANTERIORES
                if (create) {
                    path.push(new Coordinate(last.nextJ(), last.nextI()));
                    if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                        map[last.i][last.j] = last.storedDirection;
                    }  
                }
                try {
                    Thread.sleep(Config.YELLOW_WAY_SPEED); // 5000 milisegundos = 5 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(showMap());
            }
        }
        System.out.println(showMap());
        if (clear) {
            path.clear();
        }
        additionalPaths.add(path);
        return path;
    }
    
    public void firstPath() {
        redWay();
        String traceroute = pathString(yellowWay());
        clearYellowWayWalls();
        System.out.println(showMap());
        System.out.println(traceroute);
    }
    public void shortestPath() {
        if (redWalls) {
            clearYellowWayWalls();
            for (int i = 0; i < additionalPaths.size(); i++) {
                Stack<Coordinate> auxPath = additionalPaths.get(i);
                for (int j = auxPath.size()-1; j >= 0; j--) {
                    Coordinate aux = auxPath.get(j);
                    if (chances(aux) > 0 && !(isEndCoordinate(aux) && isStartCoordinate(aux))) {                   
                        Coordinate auxToWall = auxPath.get(j-1);
                        map[auxToWall.i][auxToWall.j] = Config.BLOCK_CHANCE_WALL;
                        clearYellowWay();
                        yellowWay();
                        clearYellowWayWalls();
                        break;
                    }
                }
            }
            ArrayList<Stack<Coordinate>> filteredPaths = new ArrayList<>();
            for (Stack<Coordinate> path : additionalPaths) {
                if (!path.isEmpty()) {
                    filteredPaths.add(path);
                }
            }
            additionalPaths = filteredPaths;
            if (additionalPaths.isEmpty()) {
                System.out.println("LA SALIDA NO HA SIDO DESCARTADA POR EL CAMINO ROJO. SE DEBE A UN PROBLEMA EN EL ALGORITMO");
            } else {
                Stack<Coordinate> shortestPath = additionalPaths.get(0); 
                for (int i = 1; i < additionalPaths.size(); i++) {
                    if (additionalPaths.get(i).size() < shortestPath.size()) {
                        shortestPath = additionalPaths.get(i);
                    }
                }
                String traceroute = pathString(shortestPath);
                setEntranceExit(jStart, iStart, jEnd, iEnd);
                
                for (Coordinate coordinate : shortestPath) {
                    if (!(isStartCoordinate(coordinate) || isEndCoordinate(coordinate))) {
                        map[coordinate.i][coordinate.j] = coordinate.storedDirection;
                    }
                }
                System.out.println(Config.hr + "CAMINO MÁS CORTO\n" + Config.hr);
                
                
                System.out.println(showMap());
                System.out.println(traceroute);
            }
        }
    }
    public void newSearh() {
        setEntranceExit(jStart, iStart, jEnd, iEnd);
        redWalls = false;
    }
    
    private void clearYellowWayWalls() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == Config.YELLOW_WAY_WALL) {
                    map[i][j] = ' ';
                }
            }
        }
    }
    private void clearYellowWay() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == '^' || map[i][j] == '<' || map[i][j] == 'v' || map[i][j] == '>') {
                    map[i][j] = ' ';
                }
            }
        }
    }
    
    private void clearPath(Stack<Coordinate> path) {
        for (Coordinate coordinate : path) {
            if (!(coordinate.i == iStart && coordinate.j == jStart) && !(coordinate.i == iEnd && coordinate.j == jEnd)) {
                map[coordinate.i][coordinate.j] = ' ';   
            }
        }
    }
    private String pathString(Stack<Coordinate> path) {
        StringBuilder s = new StringBuilder();
        for (Coordinate coordinate : path) {
            s.append(path.indexOf(coordinate) + " | " + coordinate.toString());
        }
        return s.toString();
    }

}
