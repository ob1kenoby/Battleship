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
                coordinates = Ships.parseCoordinates(rawCoordinates);
                this.putToField(coordinates);
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

}
