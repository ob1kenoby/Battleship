package battleship;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Field fieldOne = createField();
        System.out.println("The game starts!\n");
        fieldOne.printField(true);
        shoot(fieldOne);
        fieldOne.printField(false);
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
        Ship[] ships = new Ship[5];
        int i = 0;
        for (String shipType : shipTypes.keySet()) {
            int size = shipTypes.get(shipType);
            field.printField(false);
            ships[i] = new Ship(size, shipType, field);
            i++;
        }
        field.printField(false);
        return field;
    }
}
