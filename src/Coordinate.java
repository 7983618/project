public class Coordinate {
    /* ^1 <2 v3 >4 */
    public static int advance = 1;
    public int j;
    public int i;
    public char storedDirection;

    public Coordinate(int j, int i) {
        this.j = j;
        this.i = i;
        this.storedDirection = direction();
    }
    public int rightI() {
        if (advance == 2) {
            return i - 1;
        } else if (advance == 4) {
            return i + 1;
        }
        return i;
    }
    public int rightJ() {
        if (advance == 1) {
            return j + 1;
        } else if (advance == 3) {
            return j - 1;
        }
        return j;
    }
    public int nextJ() {
        if (advance == 2) {
            return j - 1;
        } else if (advance == 4) {
            return j + 1;
        }
        return j;
    }
    public int nextI() {
        if (advance == 1) {
            return i - 1;
        } else if (advance == 3) {
            return i + 1;
        }
        return i;
    }
    public static void rotatePi() {
        if (advance == 4) {
            advance = 1;
            return;
        }
        advance++;
    }
    public static void rotate() {
        if (advance == 1) {
            advance = 4;
            return;
        }
        advance--;
    }
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
    public static void restart() {
        advance = 1;
    }
    @Override
    public String toString() {
        return "j: " + j + " | I: " + i + " | D: " + storedDirection + "\n";
    }
    
    
    





}
