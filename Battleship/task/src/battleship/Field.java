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
}
