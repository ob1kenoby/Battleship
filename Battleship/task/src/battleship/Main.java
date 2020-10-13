package battleship;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player playerOne = new Player(1);
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Press Enter and pass the move to another player\n");
            scanner.nextLine();
        }
        Player playerTwo = new Player(2);

        game(playerOne, playerTwo);
    }

    private static void game(Player playerOne, Player playerTwo) {

        System.out.println("The game starts!\n");
        fieldOne.printField(true);
        while (fieldOne.doShipsRemain()) {
            shoot(fieldOne);
            fieldOne.printField(false);
        }
        fieldOne.printField(false);
        System.out.println("You sank the last ship. You won. Congratulations!");
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
