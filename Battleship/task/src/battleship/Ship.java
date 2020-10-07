package battleship;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Ship {
    private int[] coordinates;
    private int size;

    public Ship(int size, String name) {
        this.size = size;
        this.coordinates = inputCoordinates(size,name);
    }

    public int[] inputCoordinates(int size, String name) {
        System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", name, size);
        boolean incorrectInput = true;
        Scanner scanner = new Scanner(System.in);
        while (incorrectInput) {
            String[] rawCoordinates = scanner.nextLine().split(" ");
            if (rawCoordinates.length != 2) {
                System.out.println("Error! You need to enter two coordinates. Try again:\n");
                incorrectInput = true;
            } else {
                // Continue here
            }
        }
        return new int[0];
    }

    public static Map<String, Integer> getShips() {
        Map<String, Integer> shipTypes = new LinkedHashMap<>();
        shipTypes.put("Aircraft Carrier", 5);
        shipTypes.put("Battleship", 4);
        shipTypes.put("Submarine", 3);
        shipTypes.put("Cruiser", 3);
        shipTypes.put("Destroyer", 2);
        return shipTypes;
    }
}
