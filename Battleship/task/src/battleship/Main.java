package battleship;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player playerOne = new Player(1);
        Player playerTwo = new Player(2);

        game(playerOne, playerTwo);
    }

    private static void game(Player playerOne, Player playerTwo) {
        int turn = 0;
        while (playerOne.hasRemainingShips() && playerTwo.hasRemainingShips()) {
            turn++;
            if (turn % 2 == 1) {
                playerOne.shoot();
            }
        }
    }

}
