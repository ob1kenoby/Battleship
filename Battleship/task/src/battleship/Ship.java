package battleship;

import java.util.*;

public class Ship {
    private final int[][] COORDINATES;
    private final int SIZE;
    private final Field FIELD;

    public Ship(int size, String name, Field field) {
        this.SIZE = size;
        this.FIELD = field;
        this.COORDINATES = this.inputCoordinates(size, name);
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
            this.FIELD.putToField(coordinates);
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
            coordinates[i] = Field.convertCoordinate(coordinatesToParse[i]);
        }
        return coordinates;
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
