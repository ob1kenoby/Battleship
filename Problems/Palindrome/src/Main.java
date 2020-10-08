import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String palindrome = scanner.nextLine();
        int i = 0;
        int j = palindrome.length() - 1;
        boolean equal;
        do {
            equal = palindrome.charAt(i) == palindrome.charAt(j);
            i++;
            j--;
        } while (equal && i < j);
        if (equal) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }
}