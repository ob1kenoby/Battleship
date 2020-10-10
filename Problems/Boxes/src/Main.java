import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] box1 = new int[3];
        int[] box2 = new int[3];
        for (int i = 0; i < 3; i++) {
            box1[i] = scanner.nextInt();
        }
        for (int i = 0; i < 3; i++) {
            box2[i] = scanner.nextInt();
        }

        Arrays.sort(box1);
        Arrays.sort(box2);

        boolean box1IsBigger = compareBoxes(box1, box2);
        boolean box2IsBigger = compareBoxes(box2, box1);

        if (box1IsBigger) {
            System.out.println("Box 1 > Box 2");
        } else if (box2IsBigger) {
            System.out.println("Box 1 < Box 2");
        } else {
            System.out.println("Incompatible");
        }
    }

    private static boolean compareBoxes(int[] box1, int[] box2) {
        boolean compatible;
        if (box1[0] > box2[0] && box1[1] > box2[1] && box1[2] > box2[2]) {
            compatible = true;
        } else {
            compatible = false;
        }
        return compatible;
    }
}