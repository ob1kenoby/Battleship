package battleship;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player playerOne = new Player(1);
        Player playerTwo = new Player(2);

        game(playerOne, playerTwo);
    }

    private static void game(Player playerOne, Player playerTwo) {
        int turn = 0;
        while (playerOne.hasRemainingShips() && playerTwo.hasRemainingShips()) {
            turn++;
            if (turn % 2 == 1) {
                playerOne.shoot();
            }
        }
    }

    private static void shoot(Field field) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean incorrectInput = true;
            boolean isHit = false;
            do {
                System.out.println("Take a shot!\n");
                String input = scanner.nextLine();
                try {
                    isHit = field.shootAt(input);
                    incorrectInput = false;
                } catch (NumberFormatException e) {
                    System.out.printf("Error! %s. Try again:%n", e.getMessage());
                }
            } while (incorrectInput);
            System.out.println();
            field.printMyField(true);
            if (isHit) {
                System.out.println("You hit a ship!");
            } else {
                System.out.println("You missed!");
            }
        }
    }
}
