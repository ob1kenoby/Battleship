package battleship;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Ship {

    private String name;
    private int size;
    private int beginY;
    private int endY;
    private int beginX;
    private int endX;

    static Map<String, Integer> getShips() {
        Map<String, Integer> shipTypes = new LinkedHashMap<>();
        shipTypes.put("Aircraft Carrier", 5);
        shipTypes.put("Battleship", 4);
        shipTypes.put("Submarine", 3);
        shipTypes.put("Cruiser", 3);
        shipTypes.put("Destroyer", 2);
        return shipTypes;
    }

    public Ship(String name) {
        this.name = name;
        this.size = getShips().get(name);
        getCoordinatesFromPlayer();
    }

    private void getCoordinatesFromPlayer() {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", name, size);
        boolean incorrectInput = true;
        Scanner scanner = new Scanner(System.in);
        int[][] coordinates;
        while (incorrectInput) {
            String rawCoordinates = scanner.nextLine();
            try {
                setCoordinates(rawCoordinates);
                addShipToField();
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
    }

    /**
     * Takes String in format X* X*.
     * E.g. rawCoordinates = "A1 A5" => beginY = 0; beginX = 0; endY = 0, endX = 4;
     * In case of incorrect input: incorrect input format or wrong coordinates (e.g. E11) throws exceptions.
     * @param rawCoordinates
     * @throws NumberFormatException
     */
    private void setCoordinates(String rawCoordinates) throws NumberFormatException {
        String[] coordinatesToParse = rawCoordinates.split(" ");
        if (coordinatesToParse.length != 2) {
            throw new ArrayIndexOutOfBoundsException("Wrong input");
        }
        int[][] coordinates = new int[2][2];
        for (int i = 0; i < 2; i++) {
            coordinates[i] = Field.convertCoordinate(coordinatesToParse[i]);
        }

        beginY = coordinates[0][0]; // First letter
        beginX = coordinates[0][1]; // First digit
        endY = coordinates[1][0];  // Second letter
        endX = coordinates[1][1];  // Second digit
    }

    /**
     * A* A* => false
     * *2 *2 => true
     * A1 B2 || A1 A1 => exception
     * @param beginX
     * @param beginY
     * @param endX
     * @param endY
     * @return
     * @throws WrongPositionOfShipException
     */
    static boolean checkPosition(int beginX, int beginY, int endX, int endY) throws WrongPositionOfShipException {
        if (beginX == endX && beginY == endY) {
            throw new WrongPositionOfShipException();
        } else if (beginX == endX) {
            return true;
        } else if (beginY == endY) {
            return false;
        }
        throw new WrongPositionOfShipException();
    }

    private boolean checkLength(int begin, int end){
        if (end > begin) {
            return end - begin + 1 == this.SIZE;
        } else {
            return begin - end + 1 == this.SIZE;
        }
    }

}
