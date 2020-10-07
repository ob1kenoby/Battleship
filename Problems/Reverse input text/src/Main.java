import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        char[] data = new char[50];
        int i = reader.read(data);
        for (int j = i - 1; j >= 0; j--) {
            System.out.print(data[j]);
        }
        reader.close();
    }
}