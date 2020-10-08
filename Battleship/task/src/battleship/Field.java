package battleship;

public class Field {
    private char[][] field;
    private int[][] available; // 0 - available; 1 - another ship; 2 - area around another ship.

    public Field() {
        this.field = new char[10][10];
        this.available = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.field[i][j] = '~';
            }
        }
    }

    public void printField() {
        System.out.println(prepareField());
    }

    private String prepareField() {
        StringBuilder fieldToOutput = new StringBuilder("  1 2 3 4 5 6 7 8 9 10\n");
        String[] letters = "ABCDEFGHIJ".split("");

        for (int i = 0; i < 10; i++) {
            fieldToOutput.append(letters[i] + " ");
            for (int j = 0; j < 10; j++) {
                fieldToOutput.append(this.field[i][j] + " ");
            }
            fieldToOutput.append("\n");
        }

        return fieldToOutput.toString();
    }

    private static int[] rotate(int begin, int end) {
        int[] result = new int[2];
        result[0] = Math.min(begin, end);
        result[1] = Math.max(begin, end);
        return result;
    }

    public void putToField(int beginX, int endX, int beginY, int endY) throws
            ShipTooCloseException, TakenByOtherShipException {
        int[] rotate = Field.rotate(beginX, endX);
        beginX = rotate[0];
        endX = rotate[1];
        rotate = Field.rotate(beginY, endY);
        beginY = rotate[0];
        endY = rotate[1];

        int isAvailable = 0;
//        int[] rows = new int[2];
//        int[] columns = new int[2];
//
//        rows[0] = beginY > 0 ? beginY - 1 : beginY;
//        rows[1] = endY < 9 ? endY + 1 : endY;
//        columns[0] = beginX > 0 ? beginX - 1 : beginX;
//        columns[1] = endX < 9 ? endX + 1 : endX;

        for (int i = beginX; i <= endX; i++) {
            for (int j = beginY; j <= beginY; j++) {
                if (this.available[i][j] > 0) {
                    isAvailable = Math.max(this.available[i][j], isAvailable);
                }
            }
        }

        if (isAvailable == 1) {
            throw new ShipTooCloseException();
        } else if (isAvailable == 2) {
            throw new TakenByOtherShipException();
        }
    }
}
