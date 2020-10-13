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
        boolean incorrectInput = true;
        Scanner scanner = new Scanner(System.in);
        while (incorrectInput) {
            String rawCoordinates = scanner.nextLine();
            try {
                setCoordinates(rawCoordinates);
                incorrectInput = false;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error! Please enter two coordinates. Try again:\n");
            } catch (NumberFormatException e) {
                System.out.printf("Error! Incorrect coordinates. %s. Try again:%n%n", e.getMessage());
            } catch (WrongPositionOfShipException e) {
                System.out.println("Error! Wrong ship location! Try again:\n");
            } catch (IncorrectLengthOfShipException e) {
                System.out.printf("Error! Wrong length of the %s! Try again:%n%n", name);
            }
        }
    }

    /**
     * Takes String in format X* X*.
     * E.g. rawCoordinates = "A1 A5" => beginY = 0; beginX = 0; endY = 0, endX = 4;
     * In case of incorrect input: incorrect input format or wrong coordinates (e.g. E11) throws exceptions.
     * @param rawCoordinates
     * @throws ArrayIndexOutOfBoundsException
     * @throws NumberFormatException
     * @throws WrongPositionOfShipException
     * @throws IncorrectLengthOfShipException
     * @throws ShipTooCloseException
     * @throws TakenByOtherShipException
     */
    private void setCoordinates(String rawCoordinates) throws
            ArrayIndexOutOfBoundsException,
            NumberFormatException,
            WrongPositionOfShipException,
            IncorrectLengthOfShipException {

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

        boolean horizontalPosition = checkPosition();
        boolean lengthOk;
        if (horizontalPosition) {
            lengthOk = checkLength(beginY, endY);
        } else {
            lengthOk = checkLength(beginX, endX);
        }

        if (!lengthOk) {
            throw new IncorrectLengthOfShipException();
        }
    }

    /**
     * @return
     * A* A* => false
     * *2 *2 => true
     * @throws WrongPositionOfShipException
     */
    private boolean checkPosition() throws WrongPositionOfShipException {
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
            return end - begin + 1 == size;
        } else {
            return begin - end + 1 == size;
        }
    }

}
