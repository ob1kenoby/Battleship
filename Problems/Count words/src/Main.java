import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int unicode = reader.read();
        int count = 0;
        boolean previousIsSpace = true;
        while (unicode != -1) {
            if ((char) unicode != ' ' && previousIsSpace) {
                count++;
                previousIsSpace = false;
            } else if ((char) unicode == ' ') {
                previousIsSpace = true;
            }
            unicode = reader.read();
        }
        reader.close();
        System.out.println(count);
    }
}