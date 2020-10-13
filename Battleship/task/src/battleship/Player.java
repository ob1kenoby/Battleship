package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Player {

    private Field field;
    private List<Ship> fleet;

    public Player() {
        this.field = new Field();
        this.fleet = new ArrayList<>();
        initializeField();
    }

    private void initializeField() {
        Map<String, Integer> shipSizes = Ship.getShips();
        for (String shipType : shipSizes.keySet()) {
            fleet.add(new Ship(shipType));
        }
        field.printField(false);
    }

    private void addShipToField(int[][] coordinates) throws
            WrongPositionOfShipException,
            IncorrectLengthOfShipException,
            ShipTooCloseException,
            TakenByOtherShipException{

        int beginY = coordinates[0][0]; // First letter
        int beginX = coordinates[0][1]; // First digit
        int endY = coordinates[1][0];  // Second letter
        int endX = coordinates[1][1];  // Second digit

        boolean horizontalPosition = Ship.checkPosition(beginX, beginY, endX, endY);
        boolean lengthOk;
        if (horizontalPosition) {
            lengthOk = checkLength(beginY, endY);
        } else {
            lengthOk = checkLength(beginX, endX);
        }

        if (lengthOk) {
            field.putToField(coordinates);
        } else {
            throw new IncorrectLengthOfShipException();
        }
    }


}
