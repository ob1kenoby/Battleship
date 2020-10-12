package battleship;

import java.util.*;

public class Ships {
    private final String NAME;
    private final int SIZE;
    private final Field FIELD;

    public Ships(String name, Field field) {
        this.NAME = name;
        this.SIZE = Ships.getShips().get(name);
        this.FIELD = field;
        this.inputCoordinates();
    }


    private static boolean checkPosition(int beginX, int beginY, int endX, int endY) throws WrongPositionOfShipException {
        if (beginX == endX && beginY == endY) {
            throw new WrongPositionOfShipException();
        } else if (beginX == endX) {
            return true;
        } else if (beginY == endY) {
            return false;
        }
        throw new WrongPositionOfShipException();
    }

    private boolean checkLength(int begin, int end){
        if (end > begin) {
            return end - begin + 1 == this.SIZE;
        } else {
            return begin - end + 1 == this.SIZE;
        }
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
