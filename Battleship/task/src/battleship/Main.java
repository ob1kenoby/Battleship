package battleship;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Field field = createField();
        shoot(field);
    }

    private static void shoot(Field field) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean incorrectInput = true;
            do {
                System.out.println("Take a shot!\n");
                String input = scanner.nextLine();
            } while (incorrectInput);
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
