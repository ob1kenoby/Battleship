package battleship;

import java.util.Map;
import java.util.Scanner;

public class Player {
    Field field;

    public Player() {
        this.field = new Field();
        initializeField();
    }

    private void initializeField() {
        Map<String, Integer> shipSizes = Ships.getShips();
        for (String shipType : shipSizes.keySet()) {
            addShip(shipType, shipSizes.get(shipType));
        }
        field.printField(false);
    }

    private void addShip(String name, int size) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", name, size);
        boolean incorrectInput = true;
        Scanner scanner = new Scanner(System.in);
        int[][] coordinates;
        while (incorrectInput) {
            String rawCoordinates = scanner.nextLine();
            try {
                coordinates = parseCoordinates(rawCoordinates);
                this.addShipToField(coordinates);
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

    private void addShipToField(int[][] coordinates) throws
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
            lengthOk = checkLength(beginY, endY);
        } else {
            lengthOk = checkLength(beginX, endX);
        }

        if (lengthOk) {
            field.putToField(coordinates);
        } else {
            throw new IncorrectLengthOfShipException();
        }
    }


}
