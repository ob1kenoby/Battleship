package battleship;

import java.util.Map;
import java.util.Scanner;

class Player {

    private final Field FIELD;
    private final int playerNumber;

    public Player(int i) {
        this.playerNumber = i;
        this.FIELD = new Field();
        System.out.printf("Player %d, place your ships on the game field %n%n", playerNumber);
        initializeField();
    }

    private void initializeField() {
        Map<String, Integer> shipSizes = Ship.getShips();
        for (String shipType : shipSizes.keySet()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", shipType, shipSizes.get(shipType));
            boolean incorrectPlacement;
            do {
                Ship ship = new Ship(shipType);
                incorrectPlacement = FIELD.addShipToField(ship);
            } while (incorrectPlacement); {
                System.out.println("Error! You placed it too close to another one. Try again:\n");
            }
            printMyField(false);
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Press Enter and pass the move to another player");
                System.out.println("...");
                scanner.nextLine();
            }
        }
    }

    private void printMyField(boolean fog) {
        System.out.println(FIELD.prepareField(fog));
    }

    private void printEnemyField() {
        System.out.println(FIELD.prepareField(true));
    }

    boolean hasRemainingShips() {
        return FIELD.getShipCount() > 0;
    }
}
