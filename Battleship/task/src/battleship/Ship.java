package battleship;

import java.util.*;

public class Ship {
    private int[][] coordinates;
    private int size;

    public Ship(int size, String name) {
        this.size = size;
        this.coordinates = Ship.inputCoordinates(size,name);
    }

    private static int[][] inputCoordinates(int size, String name) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", name, size);
        boolean incorrectInput = true;
        Scanner scanner = new Scanner(System.in);
        while (incorrectInput) {
            String rawCoordinates = scanner.nextLine();
            try {
                int[][] coordinates = Ship.parseCoordinates(rawCoordinates);
                incorrectInput = false;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error! Please enter two coordinates. Try again:\n");
            } catch (NumberFormatException e) {
                System.out.printf("Error! Incorrect coordinates. %s. Try again:%n%n", e.getMessage());
            }
        }
        return new int[0][0];
    }

    private static int[][] parseCoordinates(String rawCoordinates) throws NumberFormatException {
        String[] coordinatesToParse = rawCoordinates.split(" ");
        if (coordinatesToParse.length != 2) {
            throw new ArrayIndexOutOfBoundsException("Wrong input");
        }
        int[][] coordinates = new int[2][2];
        for (int i = 0; i < 2; i++) {
            coordinates[i] = convertCoordinate(coordinatesToParse[i]);
        }
        return coordinates;
    }

    private static int[] convertCoordinate(String s) throws NumberFormatException {
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

    public static Map<String, Integer> getShips() {
        Map<String, Integer> shipTypes = new LinkedHashMap<>();
        shipTypes.put("Aircraft Carrier", 5);
        shipTypes.put("Battleship", 4);
        shipTypes.put("Submarine", 3);
        shipTypes.put("Cruiser", 3);
        shipTypes.put("Destroyer", 2);
        return shipTypes;
    }
}
