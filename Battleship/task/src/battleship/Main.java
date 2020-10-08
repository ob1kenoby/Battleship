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
        field.printField();
        for (String shipType : shipTypes.keySet()) {
            int size = shipTypes.get(shipType);
            ships[i] = new Ship(size, shipType, field);
            field.printField();
            i++;
        }
        return field;
    }
}
