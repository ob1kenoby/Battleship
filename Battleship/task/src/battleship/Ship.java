package battleship;

import java.util.*;

public class Ship {
    private int[][] coordinates;
    private int size;
    private Field field;

    public Ship(int size, String name, Field field) {
        this.size = size;
        this.field = field;
        this.coordinates = this.inputCoordinates(size, name);
    }

    private int[][] inputCoordinates(int size, String name) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", name, size);
        boolean incorrectInput = true;
        Scanner scanner = new Scanner(System.in);
        int[][] coordinates = new int[2][2];
        while (incorrectInput) {
            String rawCoordinates = scanner.nextLine();
            try {
                coordinates = Ship.parseCoordinates(rawCoordinates);
                this.putToField(coordinates, size);
                incorrectInput = false;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error! Please enter two coordinates. Try again:\n");
            } catch (NumberFormatException e) {
                System.out.printf("Error! Incorrect coordinates. %s. Try again:%n%n", e.getMessage());
            } catch (WrongPositionOfShipException e) {
                System.out.println("Error! Wrong ship location! Try again:\n");
            } catch (IncorrectLengthOfShipException e) {
                System.out.printf("Error! Wrong length of the %s! Try again:%n%n", name);
            } catch (ShipTooCloseException | TakenByOtherShipException e) {
            System.out.println("Error! You placed it too close to another one. Try again:\n");
            }
        }
        return coordinates;
    }

    private void putToField(int[][] coordinates, int size) throws
            WrongPositionOfShipException,
            IncorrectLengthOfShipException,
            ShipTooCloseException,
            TakenByOtherShipException{

        int beginY = coordinates[0][0];
        int beginX = coordinates[0][1];
        int endY = coordinates[1][0];
        int endX = coordinates[1][1];

        boolean horizontalPosition = checkPosition(beginX, beginY, endX, endY);
        boolean lengthOk;
        if (horizontalPosition) {
            lengthOk = Ship.checkLength(beginY, endY, size);
        } else {
            lengthOk = Ship.checkLength(beginX, endX, size);
        }

        if (lengthOk) {
            this.field.putToField(coordinates);
        } else {
            throw new IncorrectLengthOfShipException();
        }
    }

    private static boolean checkPosition(int beginX, int beginY, int endX, int endY) throws WrongPositionOfShipException {
        if (beginX == endX && beginY == endY) {
            throw new WrongPositionOfShipException();
        } else if (beginX == endX) {
            return true;
        } else if (beginY == endY) {
            return false;
        }
        throw new WrongPositionOfShipException();
    }

    private static boolean checkLength(int begin, int end, int size){
        if (end > begin) {
            return end - begin + 1 == size;
        } else {
            return begin - end + 1 == size;
        }
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
