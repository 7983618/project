public class Coordinate {
    /* ^1 <2 v3 >4 */
    //DIRECCION ACTUAL DEL CAMINO
    public static int advance = 1;
    //COORDENADAS
    public int j;
    public int i;
    //DIRECCION ALMACENADA DE LA COORDENADA
    public char storedDirection;
    //CONSTRUCTOR
    public Coordinate(int j, int i) {
        this.j = j;
        this.i = i;
        this.storedDirection = direction(); //GUARDA LA DIRECCIÓN ACTUAL
    }
    //I DEL QUE ESTA A SU DERECHA
    public int rightI() {
        if (advance == 2) {
            return i - 1;
        } else if (advance == 4) {
            return i + 1;
        }
        return i;
    }
    //J DEL QUE ESTA A SU DERECHA
    public int rightJ() {
        if (advance == 1) {
            return j + 1;
        } else if (advance == 3) {
            return j - 1;
        }
        return j;
    }
    //I DEL QUE ESTA ENFRENTE SUYO
    public int nextJ() {
        if (advance == 2) {
            return j - 1;
        } else if (advance == 4) {
            return j + 1;
        }
        return j;
    }
    //J DEL QUE ESTA A SU DERECHA
    public int nextI() {
        if (advance == 1) {
            return i - 1;
        } else if (advance == 3) {
            return i + 1;
        }
        return i;
    }
    //ROTAR EN SENTIDO ANTIHORARIO
    public static void rotatePi() {
        if (advance == 4) {
            advance = 1;
            return;
        }
        advance++;
    }
    //ROTAR EN SENTIDO HORARIO
    public static void rotate() {
        if (advance == 1) {
            advance = 4;
            return;
        }
        advance--;
    }
    //CARACTER DE SU DIRECCIÓN ACTUAL
    public char direction() {
        if (advance == 1) {
            return '^';
        } else if (advance == 2) {
            return '<';
        } else if (advance == 3) {
            return 'v';
        } else {
            return '>';
        }
    }
    //REINICIA LAS DIRECCIONES (NUNCA USADO)
    public static void restart() {
        advance = 1;
    }
    //COORDENADA EN FORMATO TEXTO
    @Override
    public String toString() {
        return "j: " + j + " | I: " + i + " | D: " + storedDirection + "\n";
    }
    
    
    





}
