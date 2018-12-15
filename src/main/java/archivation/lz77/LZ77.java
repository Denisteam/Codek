package archivation.lz77;

import utils.BitsWriter;

import java.io.OutputStream;
import java.io.Reader;

public class LZ77 {
    private byte[] window = new byte[65536];
    private int pointer = 0;
    private Reader reader;
    private BitsWriter writer;

    public LZ77(Reader reader, OutputStream out) {
        this.reader = reader;
        writer = new BitsWriter(out);
    }


}
