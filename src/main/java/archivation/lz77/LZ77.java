package archivation.lz77;

import javafx.util.Pair;
import utils.BitsWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;

public class LZ77 {
    private static final int DICTIONARY_SIZE = 1 << 15;
    private static final int BUFFER_SIZE = DICTIONARY_SIZE >> 2;
    private char[] window = new char[DICTIONARY_SIZE + BUFFER_SIZE];

    private int pointer = 0;
    private Reader reader;
    private BitsWriter writer;

    public LZ77(Reader reader, OutputStream out) {
        this.reader = reader;
        writer = new BitsWriter(out);
    }

    public void compress() throws IOException {
        int n = reader.read(window, DICTIONARY_SIZE, BUFFER_SIZE);
        if (n < BUFFER_SIZE) finalizeCompression(n);

        int max = 0;
        int p = 0;
        int r;
        while ((r = reader.read()) != -1) {
            for (int i = 0; i < DICTIONARY_SIZE; i++) {
                if (window[i] == window[DICTIONARY_SIZE]) {
                    int len = 0;


                    if (len > max) {
                        max = len;
                        p = i;
                    }
                }
            }
        }
    }

    private void finalizeCompression(int n) {

    }

    private void shift(int val) {

    }

}
