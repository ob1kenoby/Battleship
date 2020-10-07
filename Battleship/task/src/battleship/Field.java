package battleship;

public class Field {
    private char[][] field;
    private boolean[][] available;

    public Field() {
        this.field = new char[10][10];
        this.available = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.field[i][j] = '~';
                this.available[i][j] = true;
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
}
