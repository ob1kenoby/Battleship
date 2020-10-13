package battleship;

import java.util.HashMap;
import java.util.Map;

public class Field {
    private final int[][] FIELD; // Content described in fieldSymbols
    private int shipCount;
    private final Map<int[], Ship> SHIPS;

    final private static String[] LETTERS = "ABCDEFGHIJ".split("");
    final private static Map<Integer, String> CELL_TYPES = Map.of(
            0, "~", //    0 - available
            1, "~", //    1 - near another ship
            2, "o", //    2 - another ship
            3, "X", //    3 - hit
            4, "M"  //    4 - miss
    );

    public Field() {
        this.FIELD = new int[10][10];
        this.shipCount = 0;
        this.SHIPS = new HashMap<>();
    }

    int getShipCount() {
        return shipCount;
    }

    private void addShipIsNearCell(int... coordinates) {
        setField(1, coordinates);
    }

    private void addShipCell(Ship ship, int... coordinates) {
        SHIPS.put(coordinates, ship);
        setField(2, coordinates);
        shipCount += 1;
    }

    private void addHitCell(int... coordinates) {
        setField(3, coordinates);
        shipCount -= 1;
    }

    private void addMissCell(int... coordinates) {
        setField(4, coordinates);
    }

    private int getField(int... coordinates) {
        return FIELD[coordinates[0]][coordinates[1]];
    }

    private void setField(int value, int... coordinates) {
        FIELD[coordinates[0]][coordinates[1]] = value;
    }

    String prepareField(boolean fog) {
        StringBuilder fieldToOutput = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");

        for (int i = 0; i < 10; i++) {
            fieldToOutput.append(Field.getLetter(i));
            fieldToOutput.append(" ");
            for (int j = 0; j < 10; j++) {
                int squareType = this.getField(i, j);
                if (fog && squareType == 2) {
                    fieldToOutput.append("~");
                } else {
                    fieldToOutput.append(CELL_TYPES.get(squareType));
                }
                fieldToOutput.append(" ");
            }
            fieldToOutput.append("\n");
        }

        return fieldToOutput.toString();
    }

    private static String getLetter(int i) throws ArrayIndexOutOfBoundsException {
        if (i >= 0 && i < LETTERS.length) {
            return LETTERS[i];
        }
        throw new ArrayIndexOutOfBoundsException("Input is out of range");
    }

    /**
     * Adds a ship to the field.
     * @param ship object of Ship class
     * @return true is unsuccessful / false if successful
     */
    boolean addShipToField(Ship ship) {
        int available = 0;

        for (int i = ship.getBeginY(); i <= ship.getEndY(); i++) {
            for (int j = ship.getBeginX(); j <= ship.getEndX(); j++) {
                if (this.getField(i, j) > 0) {
                    available = Math.max(this.getField(i, j), available);
                }
            }
        }

        if (available == 1) {
            return true;
        } else if (available == 2) {
            return true;
        }

        int[] rows = new int[2];
        int[] columns = new int[2];

//      adding a ship: the coordinates from rows[0] to rows[1] && from columns[0] to columns[1]
//      become unavailable to other ships

        rows[0] = ship.getBeginY() > 0 ? ship.getBeginY() - 1 : ship.getBeginY();
        rows[1] = ship.getEndY() < 9 ? ship.getEndY() + 1 : ship.getEndY();
        columns[0] = ship.getBeginX() > 0 ? ship.getBeginX() - 1 : ship.getBeginX();
        columns[1] = ship.getEndX() < 9 ? ship.getEndX() + 1 : ship.getEndX();

        for (int i = rows[0]; i <= rows[1] ; i++) {
            for (int j = columns[0]; j <= columns[1]; j++) {
                if (i >= ship.getBeginY() && i <= ship.getEndY() && j >= ship.getBeginX() && j <= ship.getEndX()) {
                    addShipCell(ship, i, j);
                } else {
                    addShipIsNearCell(i, j); // the place is next to the ship
                }
            }
        }
        return false;
    }

    boolean shootAtCell(String input) throws NumberFormatException {
        if (input.length() < 2) {
            throw new NumberFormatException("You entered the wrong coordinates!");
        }
        int[] coordinates = Field.convertCoordinate(input);
        if (getField(coordinates[0], coordinates[1]) == 2) {
            this.addHitCell(coordinates);
            return true;
        } else if (getField(coordinates[0], coordinates[1]) == 3) {
            return true;
        } else {
            this.addMissCell(coordinates);
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
