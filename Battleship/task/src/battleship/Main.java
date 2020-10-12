package battleship;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player playerOne = new Player();
        Player playerTwo = new Player();

        Field fieldOne = createField();
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
            field.printField(true);
            if (isHit) {
                System.out.println("You hit a ship!");
            } else {
                System.out.println("You missed!");
            }
        }
    }

    private static Field createField() {
        Field field = new Field();
        Map<String, Integer> shipTypes = Ship.getShips();
        for (String shipType : shipTypes.keySet()) {
            field.printField(false);
            Ship ship = new Ship(shipType, field);
        }
        field.printField(false);
        return field;
    }
}
