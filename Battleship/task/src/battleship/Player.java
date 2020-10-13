package battleship;

import java.util.List;
import java.util.Map;

class Player {

    private Field field;
    private List<Ship> fleet;

    public Player() {
        this.field = new Field();
        initializeField();
    }

    private void initializeField() {
        Map<String, Integer> shipSizes = Ship.getShips();
        for (String shipType : shipSizes.keySet()) {
            Ship ship = new Ship(shipType);
            field.putToField(ship);
        }
        field.printField(false);
    }

}
