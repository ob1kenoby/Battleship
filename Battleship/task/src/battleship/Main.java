package battleship;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Field field = createField();
    }

    private static Field createField() {
        Field field = new Field();
        Map<String, Integer> shipTypes = Ship.getShips();
        Ship[] ships = new Ship[5];
        int i = 0;
        for (String shipType : shipTypes.keySet()) {
            int size = shipTypes.get(shipType);
            field.printField();
            ships[i] = new Ship(size, shipType);
            i++;
        }
        return field;
    }
}
