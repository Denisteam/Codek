package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputToOutput {
    private InputStream is;
    private OutputStream os;
    private byte[] buff;

    public InputToOutput(InputStream is, OutputStream os, int buffSize) {
        this.is = is;
        this.os = os;
        this.buff = new byte[buffSize];
    }

    public InputToOutput(InputStream is, OutputStream os) {
        this(is, os, 32);
    }

    public void transfer() throws IOException {
        while (is.available() > buff.length) {
            is.read(buff);
            os.write(buff);
        }
        os.write(is.readAllBytes());

        os.flush();
        os.close();
        is.close();
    }
}
