package battleship;

import java.util.Map;

public class Field {
    private final int[][] FIELD; // Content described in fieldSymbols
    private int shipCount;
    final private static String[] letters = "ABCDEFGHIJ".split("");
    final private static Map<Integer, String> squareTypes = Map.of(
            0, "~", //    0 - available
            1, "~", //    1 - near another ship
            2, "o", //    2 - another ship
            3, "X", //    3 - hit
            4, "M"  //    4 - miss
    );

    public Field() {
        this.FIELD = new int[10][10];
        this.shipCount = 0;
    }

    public boolean doShipsRemain() {
        return shipCount > 0;
    }

    private void reduceShipCount() {
        this.shipCount -= 1;
    }

    private void addShipCount() {
        this.shipCount += 1;
    }

    private void putNearShip(int... coordinates) {
        this.setField(1, coordinates);
    }

    private void putShip(int... coordinates) {
        this.setField(2, coordinates);
        this.addShipCount();
    }

    private void putHit(int... coordinates) {
        this.setField(3, coordinates);
        this.reduceShipCount();
    }

    private void putMiss(int... coordinates) {
        this.setField(4, coordinates);
    }

    private int getField(int... coordinates) {
        return FIELD[coordinates[0]][coordinates[1]];
    }

    private void setField(int value, int... coordinates) {
        this.FIELD[coordinates[0]][coordinates[1]] = value;
    }

    private static String getLetter(int i) throws ArrayIndexOutOfBoundsException {
        if (i >= 0 && i < letters.length) {
            return letters[i];
        }
        throw new ArrayIndexOutOfBoundsException("Input is out of range");
    }

    public void printField(boolean fog) {
        System.out.println(prepareField(fog));
    }

    private String prepareField(boolean fog) {
        StringBuilder fieldToOutput = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");

        for (int i = 0; i < 10; i++) {
            fieldToOutput.append(Field.getLetter(i));
            fieldToOutput.append(" ");
            for (int j = 0; j < 10; j++) {
                int squareType = this.getField(i, j);
                if (fog && squareType == 2) {
                    fieldToOutput.append("~");
                } else {
                    fieldToOutput.append(squareTypes.get(squareType));
                }
                fieldToOutput.append(" ");
            }
            fieldToOutput.append("\n");
        }

        return fieldToOutput.toString();
    }

    public void putToField(Ship ship) throws ShipTooCloseException, TakenByOtherShipException {
        int available = 0;

        for (int i = beginY; i <= endY; i++) {
            for (int j = beginX; j <= endX; j++) {
                if (this.getField(i, j) > 0) {
                    available = Math.max(this.getField(i, j), available);
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
                    putShip(i, j);
                } else {
                    putNearShip(i, j); // the place is next ot the ship
                }
            }
        }
    }

    public boolean shootAt(String input) throws NumberFormatException {
        if (input.length() < 2) {
            throw new NumberFormatException("You entered the wrong coordinates!");
        }
        int[] coordinates = Field.convertCoordinate(input);
        if (getField(coordinates[0], coordinates[1]) == 2) {
            this.putHit(coordinates);
            return true;
        } else if (getField(coordinates[0], coordinates[1]) == 3) {
            return true;
        } else {
            this.putMiss(coordinates);
            return false;
        }
    }

    static int[] convertCoordinate(String s) throws NumberFormatException {
        int[] coordinate = new int[2];
        switch (s.charAt(0)) {
            case 'A':
            case 'a':
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
        coordinate[1] = Integer.parseInt(s.substring(1)) - 1;
        if (coordinate[1] < 0 || coordinate[1] > 9) {
            throw new NumberFormatException("Only numbers from 1 to 10 are allowed");
        }
        return coordinate;
    }
}
