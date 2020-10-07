package battleship;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Field field = createField();
    }

    private static Field createField() {
        Field field = new Field();
        Map<String, Integer> shipTypes = Ship.getShips();
        return field;
    }
}
