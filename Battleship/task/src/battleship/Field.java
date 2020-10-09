package battleship;

public class Field {
    private char[][] field;
    private int[][] available; // 0 - available; 1 - another ship; 2 - area around another ship.
    final private static String[] letters = "ABCDEFGHIJ".split("");


    public Field() {
        this.field = new char[10][10];
        this.available = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.field[i][j] = '~';
            }
        }
    }

    public void printField() {
        System.out.println(prepareField());
//        System.out.println();
//        System.out.println(debugField());
    }

    private String prepareField() {
        StringBuilder fieldToOutput = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");

        for (int i = 0; i < 10; i++) {
            fieldToOutput.append(Field.getLetter(i));
            fieldToOutput.append(" ");
            for (int j = 0; j < 10; j++) {
                fieldToOutput.append(this.getField(i, j));
                fieldToOutput.append(" ");
            }
            fieldToOutput.append("\n");
        }

        return fieldToOutput.toString();
    }

    private String debugField() {
        StringBuilder fieldToOutput = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");

        for (int i = 0; i < 10; i++) {
            fieldToOutput.append(Field.getLetter(i));
            fieldToOutput.append(" ");
            for (int j = 0; j < 10; j++) {
                fieldToOutput.append(this.isAvailable(i, j));
                fieldToOutput.append(" ");
            }
            fieldToOutput.append("\n");
        }

        return fieldToOutput.toString();
    }

    private static int[] rotate(int begin, int end) {
        int[] result = new int[2];
        result[0] = Math.min(begin, end);
        result[1] = Math.max(begin, end);
        return result;
    }

    public void putToField(int[][] coordinates) throws ShipTooCloseException, TakenByOtherShipException {
        int beginY = Field.rotate(coordinates[0][0], coordinates[1][0])[0];
        int endY = Field.rotate(coordinates[0][0], coordinates[1][0])[1];
        int beginX = Field.rotate(coordinates[0][1], coordinates[1][1])[0];
        int endX = Field.rotate(coordinates[0][1], coordinates[1][1])[1];

        int available = 0;

        for (int i = beginY; i <= endY; i++) {
            for (int j = beginX; j <= endX; j++) {
                if (this.isAvailable(i, j) > 0) {
                    available = Math.max(this.isAvailable(i, j), available);
                }
            }
        }

        if (available == 1) {
            throw new ShipTooCloseException();
        } else if (available == 2) {
            throw new TakenByOtherShipException();
        }

        int[] rows = new int[2];
        int[] columns = new int[2];

        rows[0] = beginY > 0 ? beginY - 1 : beginY;
        rows[1] = endY < 9 ? endY + 1 : endY;
        columns[0] = beginX > 0 ? beginX - 1 : beginX;
        columns[1] = endX < 9 ? endX + 1 : endX;
        for (int i = rows[0]; i <= rows[1] ; i++) {
            for (int j = columns[0]; j <= columns[1]; j++) {
                if (i >= beginY && i <= endY && j >= beginX && j <= endX) {
                    placeShip(i, j);
                    setAvailable(i, j, 2); // the place is occupied by the ship
                } else {
                    setAvailable(i, j, 1); // the place is next ot the ship
                }
            }
        }
    }

    private char getField(int x, int y) {
        return field[x][y];
    }

    private void placeShip(int... coordinates) {
        this.putSymbolOnMap('o', coordinates);
    }

    private void putSymbolOnMap(char symbol, int[] coordinates) {
        this.field[coordinates[0]][coordinates[1]] = symbol;
    }

    private void putHit(int[] coordinates) {
        this.putSymbolOnMap('X', coordinates);
    }

    private int isAvailable(int x, int y) {
        return available[x][y];
    }

    private void setAvailable(int x, int y, int value) {
        this.available[x][y] = value;
    }

    private static String getLetter(int i) throws ArrayIndexOutOfBoundsException {
        if (i >= 0 && i < letters.length) {
            return letters[i];
        }
        throw new ArrayIndexOutOfBoundsException("Input is out of range");
    }

    public static int[] convertCoordinate(String s) throws NumberFormatException {
        int[] coordinate = new int[2];
        switch (s.charAt(0)) {
            case 'A':
            case 'a':
                coordinate[0] = 0;
                break;
            case 'B':
            case 'b':
                coordinate[0] = 1;
                break;
            case 'C':
            case 'c':
                coordinate[0] = 2;
                break;
            case 'D':
            case 'd':
                coordinate[0] = 3;
                break;
            case 'E':
            case 'e':
                coordinate[0] = 4;
                break;
            case 'F':
            case 'f':
                coordinate[0] = 5;
                break;
            case 'G':
            case 'g':
                coordinate[0] = 6;
                break;
            case 'H':
            case 'h':
                coordinate[0] = 7;
                break;
            case 'I':
            case 'i':
                coordinate[0] =  8;
                break;
            case 'J':
            case 'j':
                coordinate[0] =  9;
                break;
            default:
                throw new NumberFormatException("Only letters A-Z are allowed");
        }
        coordinate[1] = Integer.valueOf(s.substring(1)) - 1;
        if (coordinate[1] < 0 || coordinate[1] > 9) {
            throw new NumberFormatException("Only numbers from 1 to 10 are allowed");
        }
        return coordinate;
    }

    public boolean shootAt(String input) throws NumberFormatException {
        if (input.length() < 2) {
            throw new NumberFormatException("You entered the wrong coordinates!");
        }
        int[] coordinates = Field.convertCoordinate(input);
        if (isAvailable(coordinates[0], coordinates[1]) == 2) {
            this.putHit(coordinates);
            return true;
        } else {
            this.putMiss(coordinates);
            return false;
        }
    }
}
