import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        byte c = (byte) inputStream.read();
        while (c != -1) {
            System.out.print(c);
            c = (byte) inputStream.read();
        }
    }
}