import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int printed = 0;
        int i = 0;
        while (printed < n) {
        i++;
            for (int j = 0; j < i && printed < n; j++) {
                System.out.print(i + " ");
                printed++;
            }
        }
    }
}