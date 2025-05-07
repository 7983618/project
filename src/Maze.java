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
                additionalPaths = new ArrayList<>(); //ELIMINAMOS TODOS LOS CAMINOS ALMACENADOS QUE HAYAMOS ENCONTRADO
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
                    clearPath(path); //BORRAMOS EL CAMINO
                } if (times == chances+1) {
                    continue; //ROMPEMOS BUCLE
                }
                //SI HA ENCONTRADO LA SALIDA SE DESACTIVA LA CREACIÓN DE MUROS
                if (isEndCoordinate(last)) {
                    makewalls = false;
                }
                //SI TIENE LIBRE POR LA DERECHA GIRA HACIA LA DERECHA Y CONTINUA, SINO SIGUE DE FRENTE (SI ESTA LIBRE, SINO GIRA EN SENTIDO ANTIHORARIO). LO BLOQUEAN LOS MUROS ORIGINALES
                if (!(isWall(last.rightJ(), last.rightI()))) {
                    Coordinate.rotate();
                    last.storedDirection = last.direction();
                }
                while (isWall(last.nextJ(), last.nextI())) {
                    Coordinate.rotatePi();
                    last.storedDirection = last.direction();
                }
                //POR DEFECTO SIEMPRE VAMOS A CREAR UN PASO
                boolean create = true;
                //CON ESTO BORRAMOS LOS "PASOS" SI HEMOS VUELTO SOBRE NUESTOS PASOS
                if (path.size() >= 2) { //MINIMO DOS PASOS PARA QUE NO HAYA UN FALLO DE RANGO
                    //GUARDAMOS LA ANTERIOR COORDENADA A LA ACUTAL
                    Coordinate penultimate = path.get(path.size()-2);
                    //SI LA SIGUIENTE ES IGUAL A LA ANTERIOR (ESTAMOS VOLVIENDO POR NUESTROS PASOS)
                    if (last.nextI() == penultimate.i && last.nextJ() == penultimate.j) {
                        if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) { //NO MODIFICAMOS SI SON ENTRADA O SALIDA
                            if (makewalls) { //CREAMOS MUROS
                                map[last.i][last.j] = Config.RED_WAY_WALL;
                            } else { //DAMOS VÍA LIBRE SI LA CREACION DE MUROS ESTA DESACTIVADA (HEMOS ENCONTRADO LA SALIDA) 
                                map[last.i][last.j] = ' ';
                            }
                        } 
                        path.pop(); //ELIMINAMOS ULTIMO PASO
                        create = false; //NO CREAMOS UN NUEVO PASO PORQUE ESTAMOS RETROCEDIENDO
                    }
                }
                //CREAMOS UN "PASO" SI NO ESTAMODS VOLVIENDO.
                if (create) {
                    path.push(new Coordinate(last.nextJ(), last.nextI()));
                    if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                        map[last.i][last.j] = last.storedDirection; //MARCAMOS DIRECCIÓN EN EL MAPA
                        makewalls = true; //VOLVEMOS A CREAR MUROS
                    }  
                }
                //TIEMPOS DE ESPERA
                try {
                    Thread.sleep(Config.RED_WAY_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //MOSTRAMOS MAPA
                System.out.println(showMap());
            }
            redWalls = true; //EL MAPA SE HA MODIFICADO POR EL CAMINO ROJO
            //MOSTRAMOS EL MAPA CON EL CAMINO ROJO
            System.out.println(showMap());
            //MOSTRAMOS RUTA DEL CAMINO ROJO
            System.out.println(pathString(path));
        }
    }
    //ES EL RECORRIDO DEFINITIVO QUE ENCUENTRA LA SALIDA
    private Stack<Coordinate> yellowWay() {
        //AQUI SE ALMACENA EL RECORRIDO
        Stack<Coordinate> path = new Stack<>();
        //SI EL CAMINO SE QUEDA ATRAPADO EN SI MISMO NO NOS SIRVE POR LO QUE LO BORRAMOS DEL MAPA
        boolean clear = false;
        if (coordinates && redWalls) { //SI SE HAN ESTABLECIDO COORDENADAS Y SE HAN PUESTO MUROS
            //METEMOS AL CAMINO LA COORDENADA DE ENTRADA
            path.push(new Coordinate(jStart, iStart));
            //CONTADOR DE VECES QUE ROTA
            int times = 0;
            //MIENTRAS NO HAYA ROTADO MAS DE CUATRO VECES (SIGNIFICA QUE SE HA QUEDADO ATRAPADO POR BLOQUEOS DE CUALQUIER TIPO)
            while (times < 4) {
                Coordinate last = path.peek();
                if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                    last.storedDirection = last.direction();
                    map[last.i][last.j] = last.storedDirection;
                }
                if (isEndCoordinate(last)) {
                    break;
                }
                //SI TIENE LIBRE POR LA DERECHA GIRA, SINO SIGUE DE FRENTE (SI ESTA LIBRE, SINO GIRA EN SENTIDO ANTIHORARIO). LO BLOQUEAN LOS MUROS ORIGINALES, LOS MUROS DEL CAMINO ROJO, SUS PROPIOS MUROS QUE EL DEJA CUANDO VUELVE POR SUS PASOS, SU PROPIO CAMINO, Y LOS MUROS DE DESCARTE
                if (!(isWall(last.rightJ(), last.rightI()) || isRedWall(last.j, last.i) || isYellowWay(last.rightJ(), last.rightI()) || isYellowWall(last.rightJ(), last.rightI()) || isBlockChanceWall(last.rightJ(), last.rightI()))) {
                    Coordinate.rotate(); //ROTAMOS
                    last.storedDirection = last.direction(); //ACTUALIZAMOS LA DIRECCION DE LA COORDENADA ACUTAL
                }
                //RESTABLECEMOS EL CONTADOR DE GIROS
                times = 0;
                while ((isWall(last.nextJ(), last.nextI()) || isRedWall(last.nextJ(), last.nextI()) || isYellowWay(last.nextJ(), last.nextI()) || isYellowWall(last.nextJ(), last.nextI()) || isBlockChanceWall(last.nextJ(), last.nextI())) && times < 4) {
                    if (path.size() >=2) { //PARA QUE NO HAYA UN ERROR DE RANGO
                        Coordinate penultimate = path.get(path.size()-2);
                        if (last.nextI() == penultimate.i && last.nextJ() == penultimate.j) { //SI LA SIGUIENTE ES LA PENÚLTIMA SALIMOS DEL BUCLE 
                            break;
                        }
                            
                    }
                    //SI ROTAMOS AUMENTAMOS CONTADOR Y ACTUALIA
                    Coordinate.rotatePi();
                    times++;
                    last.storedDirection = last.direction();
                }
                //SI SE HA QUEDADO ATRAPADO ATIVAMOS QUE SE BORRE EL CAMINO AL FINAL
                if (times >= 4) {
                    clear = true;
                }
                //CON ESTO BORRAMOS LOS "PASOS" SI HEMOS VUELTO SOBRE NUESTOS PASOS
                boolean create = true; //SI BORRAMOS NO HACE FALTA CREAR UN PASO MÁS
                if (path.size() >= 2) {
                    //PENULTIMA COORDENADA
                    Coordinate penultimate = path.get(path.size()-2);
                    if (last.nextI() == penultimate.i && last.nextJ() == penultimate.j) { //SI ES IGUAL AL SIGUIENTE SIGNIFICA QUE ESTAMOS VOLVIENDO SOBRE NUESTROS PASOS
                        //AL VOLVER SOBRE NUESTOS PASOS DEJAMOS MUROS DEL CAMINO AMARILLO EXCEPTO SI ES LA ENTRADA O LA SALIDA
                        if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                            map[last.i][last.j] = Config.YELLOW_WAY_WALL; 
                        }
                        //ELIMINAMOS EL ÚLTIMO
                        path.pop();
                        //NO HACE FALTA CREAR
                        create = false;
                    }
                }
                //CREAMOS UN "PASO" SI NO ESTAMODS VOLVIENDO.
                if (create) {
                    //CREAMOS COORDENADA
                    path.push(new Coordinate(last.nextJ(), last.nextI()));
                    //SI NO ES LA ENTRADA NI LA SALIDA MOSTRAMOS LA DIRECCIÓN EN EL MAPA
                    if (!(isStartCoordinate(last)) && !(isEndCoordinate(last))) {
                        map[last.i][last.j] = last.storedDirection;
                    }  
                }
                //TIEMPO DE ESPERA
                try {
                    Thread.sleep(Config.YELLOW_WAY_SPEED); // 5000 milisegundos = 5 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //MOSTRAMOS PROCESO
                System.out.println(showMap());
            }
        }
        //MOSTRAMOS RESULTADO
        System.out.println(showMap());
        //BORRAMOS CAMINO SI SE HA QUEDADO ENCERRADO
        if (clear) {
            path.clear();
        }
        //AÑADIMOS EL CAMINO A UNA LISTA DE CAMINOS POSIBLES
        additionalPaths.add(path);
        //DEVOLVEMOS CAMINO
        return path;
    }
    //ENCUENTRA EL PRIMER CAMINO QUE PUEDE
    public void firstPath() {
        redWay(); //CREAMOS MUROS ROJOS
        String traceroute = pathString(yellowWay()); //GUARDAMOS RECORRIDO
        clearYellowWayWalls(); //LIMPIAMOS MUROS
        System.out.println(showMap()); //MOSTRAMOS MAPA
        System.out.println(traceroute); //MOSTRAMOS EL CAMINO GUARDADO
    }
    //INTENTA ENCONTRAR EL CAMINO MÁS CORTO
    public void shortestPath() {
        if (redWalls) { //SI SE HAN PUESTO MUROS ROJOS
            clearYellowWayWalls(); //LIMPIAMOS MUROS AMARILLOS
            //RECORREMOS EL ARRAY DE LOS CAMINOS POSIBLES. AL INICIO SOLO TENDRÁ EL DEL PRIMER CAMINO POSIBLE
            for (int i = 0; i < additionalPaths.size(); i++) {
                //GUARDAMOS EL CAMINO
                Stack<Coordinate> auxPath = additionalPaths.get(i);
                //RECORREMOS EL CAMINO DEL FINAL HACIA EL INICIO
                for (int j = auxPath.size()-1; j >= 0; j--) {
                    //GUARDAMOS LA COORDENADA ACUTAL
                    Coordinate aux = auxPath.get(j);
                    //SI TIENE UNA ALTERNATIVA BLOQUEA EL SIGUIENTE. OSEA EL ANTERIOR DESDE LA CASILLA DE ENTRADA HASTA LA CASILLA DE SALIDA. Y VUELVE A LANZAR EL CAMINO AMARILLO QUE ESTA VEZ TENDRA QUE ENCONTRAR OTRO CAMINO PORQUE HEMOS BLOQUEADO LA COORDENADA POR LA QUE PASABA HASTA LA SALIDA EN EL CAMINO ANTERIOR
                    if (chances(aux) > 0 && !(isEndCoordinate(aux) && isStartCoordinate(aux))) {                   
                        //BLOQUEAMOS COORDENADA
                        Coordinate auxToWall = auxPath.get(j-1);
                        map[auxToWall.i][auxToWall.j] = Config.BLOCK_CHANCE_WALL;
                        //LIMPIAMOS CAMINO
                        clearYellowWay();
                        //LO VOLVEMOS A LANZAR CON LA COORDENADA BLOQUEADA
                        yellowWay();
                        //LIMPIAMOS LOS MUROS QUE HA DEJADO
                        clearYellowWayWalls();
                        //VOLVEMOS A HACEMOS EL MISMO PROCESO CON EL SIGUIENTE CAMINO (OSEA EL NUEVO QUE HA ENCONTRADO DESPUES DE BLOQUEAR LA CASILLA DEL ANTERIOR)
                        break;
                    }
                }
            }
            //EL METODO ANTERIOR DEJA ALGUNOS CAMINOS SIN COORDENADAS. ASI QUE LOS LIMPIAMOS
            ArrayList<Stack<Coordinate>> filteredPaths = new ArrayList<>();
            for (Stack<Coordinate> path : additionalPaths) {
                if (!path.isEmpty()) {
                    filteredPaths.add(path);
                }
            }
            additionalPaths = filteredPaths;
            //SI NO HAY NINGUN CAMINO EN LA LISTA DE CAMINOS POSIBLE SIGNIFICA QUE NO HA SIDO CAPAZ DE ENCONTRAR UNA SALIDA VÁLIDA (OSEA QUE LA SALIDA SE HA ESTABLECIDO EN UNA COORDENADA QUE EL CAMINO ROJO NO HA DESCARTADO). ESTO OCURRE POR EL PROPIO ALGORITMO, YA QUE EL CAMINO ROJO PODRIA VOLVER A LA SALIDA DESDE UN PUNTO DIFERENTE DEL QUE SALIÓ Y VOLVER A SALIR DESDE EL PUNTO QUE SALIÓ ANTERIORMENTE. (NO OCURRE SI EL BLOQUE HA SU DERECHA SE ENCUENTRA PEGADO A LAS PAREDES DEL LABERINTO)
            if (additionalPaths.isEmpty()) {
                System.out.println("LA SALIDA NO HA SIDO DESCARTADA POR EL CAMINO ROJO. SE DEBE A UN PROBLEMA EN EL ALGORITMO");
            } else {
                //OBTENEMOS EL CAMINO MÁS CORTO QUE HEMOS ENCONTRADO
                Stack<Coordinate> shortestPath = additionalPaths.get(0); 
                for (int i = 1; i < additionalPaths.size(); i++) {
                    if (additionalPaths.get(i).size() < shortestPath.size()) {
                        shortestPath = additionalPaths.get(i);
                    }
                }
                //GUARDAMOS RECORRIDO
                String traceroute = pathString(shortestPath);
                //VOLVEMOS A LEER EL MAPA PARA QUE QUEDE LIBRE DE ALTERACIONES
                setEntranceExit(jStart, iStart, jEnd, iEnd);
                //ESCRIBIMOS LAS DIRECCIONES DEL CAMINO EN EL MAPA
                for (Coordinate coordinate : shortestPath) {
                    if (!(isStartCoordinate(coordinate) || isEndCoordinate(coordinate))) {
                        map[coordinate.i][coordinate.j] = coordinate.storedDirection;
                    }
                }
                //MOSTRAMOS MAPA
                System.out.println(Config.hr + "CAMINO MÁS CORTO\n" + Config.hr);
                System.out.println(showMap());
                System.out.println(traceroute);
            }
        }
    }
    //NOS RESTABLECE EL MAPA CON LAS ENTRADAS Y SALIDAS PARA VOLVER A BUSCAR CAMINOS
    public void newSearh() {
        setEntranceExit(jStart, iStart, jEnd, iEnd);
        redWalls = false;
    }
    //BORRA LOS MUROS DEJADOS POR EL CAMINO AMARILLO
    private void clearYellowWayWalls() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == Config.YELLOW_WAY_WALL) {
                    map[i][j] = ' ';
                }
            }
        }
    }
    //BORRA LAS DIRECCIONES DEJADAS POR EL CAMINO AMARILLO
    private void clearYellowWay() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == '^' || map[i][j] == '<' || map[i][j] == 'v' || map[i][j] == '>') {
                    map[i][j] = ' ';
                }
            }
        }
    }
    //BORRA LAS DIRECCIONES DEJADAS POR UN CAMINO PROPORCIONADO
    private void clearPath(Stack<Coordinate> path) {
        for (Coordinate coordinate : path) {
            if (!(coordinate.i == iStart && coordinate.j == jStart) && !(coordinate.i == iEnd && coordinate.j == jEnd)) {
                map[coordinate.i][coordinate.j] = ' ';   
            }
        }
    }
    //STRING QUE DA LAS COORDENADAS DE UN CAMINO EN FORMATO TEXTO
    private String pathString(Stack<Coordinate> path) {
        StringBuilder s = new StringBuilder();
        for (Coordinate coordinate : path) {
            s.append(path.indexOf(coordinate) + " | " + coordinate.toString());
        }
        return s.toString();
    }
}
