package battleship;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Field field = createField();
        System.out.println("The game starts!\n");
        field.printField();
        shoot(field);
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
            field.printField();
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
        Ship[] ships = new Ship[5];
        int i = 0;
        for (String shipType : shipTypes.keySet()) {
            int size = shipTypes.get(shipType);
            field.printField();
            ships[i] = new Ship(size, shipType, field);
            i++;
        }
        field.printField();
        return field;
    }
}
