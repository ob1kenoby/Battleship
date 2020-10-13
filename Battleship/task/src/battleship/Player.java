package battleship;

import java.util.Map;

class Player {

    private Field field;

    public Player() {
        this.field = new Field();
        initializeField();
    }

    private void initializeField() {
        Map<String, Integer> shipSizes = Ship.getShips();
        for (String shipType : shipSizes.keySet()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n%n", shipType, shipSizes.get(shipType));
            try {
                Ship ship = new Ship(shipType);
                field.putToField(ship);
            } catch (ShipTooCloseException | TakenByOtherShipException e) {
                System.out.println("Error! You placed it too close to another one. Try again:\n");
            }
        }
        field.printField(false);
    }

}
