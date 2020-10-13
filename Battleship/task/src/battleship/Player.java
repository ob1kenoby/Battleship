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
            printMyField();
            passMove();
        }
    }

    private static void passMove() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Press Enter and pass the move to another player");
            System.out.println("...");
            scanner.nextLine();
        }
    }

    void shoot() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean incorrectInput = true;
            boolean isHit = false;
            printBothFields();
            System.out.printf("Player %d, it's your turn:%n%n", playerNumber);
            do {
                String input = scanner.nextLine();
                try {
                    isHit = FIELD.shootAtCell(input);
                    incorrectInput = false;
                } catch (NumberFormatException e) {
                    System.out.printf("Error! %s. Try again:%n", e.getMessage());
                }
            } while (incorrectInput);
            System.out.println();
            if (isHit) {
                System.out.println("You hit a ship!");
            } else {
                System.out.println("You missed!");
            }
        }
        passMove();
    }

    private void printBothFields() {
        printEnemyField();
        System.out.println("---------------------");
        printMyField();
    }

    private void printMyField() {
        System.out.println(FIELD.prepareField(false));
    }

    private void printEnemyField() {
        System.out.println(FIELD.prepareField(true));
    }

    boolean hasRemainingShips() {
        return FIELD.getShipCount() > 0;
    }
}
