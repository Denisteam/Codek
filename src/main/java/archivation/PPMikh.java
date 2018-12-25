package archivation;

import javafx.css.Match;
import utils.BitsWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PPMikh {
    private InputStream source;
    private OutputStream dest;
    private BitsWriter writer;
    private int contextSize;

    public PPMikh(InputStream source, OutputStream dest, int contextSize) {
        if (contextSize < 0) throw new IllegalArgumentException();

        this.source = source;
        this.dest = dest;
        this.contextSize = contextSize;
        writer = new BitsWriter(dest);
    }

    public void compress() throws IOException {
        int count = 0;
        byte[] buff = new byte[contextSize];
        byte predicted = 0b01111111;
        int r = 0;
        while ((r = source.read()) != -1) {
            if (count > 1) {
                predicted = 0;
                for (int i = 0; i < count; i++) {
                    predicted += (byte) (buff[i] * ((float) i) / ((count - 1) * count / 2f));
                }
            }

            writeDif((byte) r, predicted);

            if (count < contextSize) {
                count++;
            } else {
                for (int i = 1; i < count; i++) {
                    buff[i - 1] = buff[i];
                }
            }
            buff[count - 1] = (byte) r;
        }

        writer.flush();
        writer.close();
        source.close();
    }

    private void writeDif(byte r, byte predicted) throws IOException {
        byte dif = (byte) (predicted - r);
        dif = (byte) ((dif << 1) ^ (dif >>> 7));

        byte i = 0;
        while ((dif >>> i) != 0) i++;

        if (i < 4) {
            writer.writeOneBit(0);
            writer.writeOneBit(1);
            writer.writeBits(dif, 3);
        } else if (i < 6) {
            writer.writeOneBit(1);
            writer.writeBits(dif, 5);
        } else {
            writer.writeOneBit(0);
            writer.writeOneBit(0);
            writer.writeOneBit(1);
            writer.writeBits(dif, 8);
        }
    }

    private void decompress() throws IOException {

        int p = 7;
        byte b = 0;
        int r;
        while ((r = source.read()) != -1) {

            if ((r >>> p) == 1) {
                if (p < 3) {}
            }

        }
    }
}
