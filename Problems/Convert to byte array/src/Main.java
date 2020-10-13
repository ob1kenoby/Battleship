import java.io.IOException;
import java.io.CharArrayWriter;

class Converter {
    public static char[] convert(String[] words) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        for (String word : words) {
            output.write(word);
        }
        return output.toCharArray();
    }
}