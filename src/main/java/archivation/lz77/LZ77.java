package archivation.lz77;

import utils.BitsWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

public class LZ77 {
    private static final int DICTIONARY_SIZE = 1 << 15;
    private static final int BUFFER_SIZE = DICTIONARY_SIZE >> 7;
    private byte[] window = new byte[DICTIONARY_SIZE + BUFFER_SIZE];

    private int pointer = 0;
    private InputStream source;
    private BitsWriter writer;
    private OutputStream dest;

    public LZ77(InputStream source, OutputStream out) {
        this.source = source;
        dest = out;
        writer = new BitsWriter(out);
    }

    public void compress() throws IOException {
        int n = source.read(window, DICTIONARY_SIZE, BUFFER_SIZE);
        if (n < BUFFER_SIZE) finalizeCompression(n);

        int max = 0;
        int p = 0;
        while (true) {
            for (int i = 0; i < DICTIONARY_SIZE; i++) {
                if (window[i] == window[DICTIONARY_SIZE]) {
                    int len = 1;
                    for (int j = 1; j < BUFFER_SIZE; j++) {
                        if (window[i + j] == window[DICTIONARY_SIZE + j]) {
                            len++;
                        } else break;
                    }

                    if (len > max) {
                        max = len;
                        p = i;
                    }
                }
            }

            if (max > 1) {
                writer.writeOneBit(1);
                if (max < 6) {
                    writer.writeOneBit(1);
                    writer.writeBits(max - 2, 2);
                } else if (max < 16) {
                    writer.writeOneBit(0);
                    writer.writeOneBit(1);
                    writer.writeBits(max, 4);
                } else {
                    writer.writeOneBit(0);
                    writer.writeOneBit(0);
                    writer.writeOneBit(1);
                    writer.writeBits(max, 7);
                }
                writer.writeBits(p, 16);

                for (int i = max; i < window.length; i++) {
                    window[i - max] = window[i];
                }
                int len = source.read(window, DICTIONARY_SIZE + BUFFER_SIZE - max, max);

                if (len == -1) {
                    finalizeCompression(BUFFER_SIZE - max);
                    return;
                }

                if (len < max) {
                    finalizeCompression(BUFFER_SIZE - max + len);
                    return;
                }
            } else {
                writer.writeOneBit(0);
                writer.writeByte(window[DICTIONARY_SIZE]);

                for (int i = 1; i < window.length; i++) {
                    window[i - 1] = window[i];
                }

                int r = source.read();
                if (r == -1) {
                    finalizeCompression(BUFFER_SIZE - 1);
                    return;
                }
                window[window.length - 1] = (byte) r;
            }

            max = 0;
            p = 0;
        }
    }

    /*
    * @param n: left unencoded bytes
    * */
    private void finalizeCompression(int n) throws IOException {

        int max = 0;
        int p = 0;
        while (n > 0) {
            for (int i = 0; i < DICTIONARY_SIZE; i++) {
                if (window[i] == window[DICTIONARY_SIZE]) {
                    int len = 1;
                    for (int j = 1; j < n; j++) {
                        if (window[i + j] == window[DICTIONARY_SIZE + j]) {
                            len++;
                        } else break;
                    }

                    if (len > max) {
                        max = len;
                        p = i;
                    }
                }
            }

            if (max > 1) {
                writer.writeOneBit(1);
                if (max < 6) {
                    writer.writeOneBit(1);
                    writer.writeBits(max - 2, 2);
                } else if (max < 16) {
                    writer.writeOneBit(0);
                    writer.writeOneBit(1);
                    writer.writeBits(max, 4);
                } else {
                    writer.writeOneBit(0);
                    writer.writeOneBit(0);
                    writer.writeOneBit(1);
                    writer.writeBits(max, 7);
                }
                writer.writeBits(p, 16);

                for (int i = max; i < window.length; i++) {
                    window[i - max] = window[i];
                }
                n -= max;

            } else {
                writer.writeOneBit(0);
                writer.writeByte(window[DICTIONARY_SIZE]);

                for (int i = 1; i < window.length; i++) {
                    window[i - 1] = window[i];
                }
                n--;
            }

            max = 0;
            p = 0;
        }

        source.close();
        writer.flush();
        writer.close();
    }

    public void decompress() throws IOException {

        int p = 0;
        int b = 0;
        while (true) {
            int r = source.read();
            if (r == -1) break;

            b = 0b1 & (r >> (7 - p) );

            p++;
            if (b == 0) {
                int n = source.read();
                if (n == -1) break;

                int d =  8 - p;
            }

            if (p == 8) {
                r = source.read();

                p = 0;
            }


        }
    }

    private void readLength() {

    }

    private void shift(byte val) {
        for (int i = 1; i < window.length; i++) {
            window[i - 1] = window[i];
        }

        window[window.length - 1] = val;
    }

}
