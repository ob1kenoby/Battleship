package battleship;

public class Field {
    private char[][] field;
    private int[][] available; // 0 - available; 1 - another ship; 2 - area around another ship.

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
    }

    private String prepareField() {
        StringBuilder fieldToOutput = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        String[] letters = "ABCDEFGHIJ".split("");

        for (int i = 0; i < 10; i++) {
            fieldToOutput.append(letters[i] + " ");
            for (int j = 0; j < 10; j++) {
                fieldToOutput.append(this.getField(i, j) + " ");
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
        int endX = Field.rotate(coordinates[0][1], coordinates[1][1])[0];

        int available = 0;

        for (int i = beginX; i <= endX; i++) {
            for (int j = beginY; j <= endY; j++) {
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

    private void placeShip(int x, int y) {
        this.field[x][y] = 'o';
    }

    private int isAvailable(int x, int y) {
        return available[x][y];
    }

    private void setAvailable(int x, int y, int value) {
        this.available[x][y] = value;
    }
}