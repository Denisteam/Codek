package utils;

import java.io.*;

public class BitsWriter implements Closeable, Flushable{
    private byte[] buff;
    private int index = 0;
    private int buffSize;
    private byte tmp;
    private byte pos = 0;
    private OutputStream output;

    public BitsWriter(OutputStream output, int buffSize) {
        this.output = output;
        buff = new byte[buffSize];
        this.buffSize = buffSize;
    }

    public BitsWriter(OutputStream output) {
        this.output = output;
        buff = new byte[20];
        this.buffSize = 20;
    }

    public void writeOneBit(int val) throws IOException {
        writeBits((byte) val, (byte) 1);
    }

    public void writeBits(int val, int count) throws IOException {
        if (count > 32) throw new IllegalArgumentException("Bits count: " + count);
        int rst = count % 8;
        int byt = count / 8;

        if (rst > 0) {
            writeBits((byte) (val >>> (byt * 8)), (byte) rst);
        }

        for (int i = byt - 1; i >= 0; i--) {
            writeByte((byte) (0xff & (val >>> (i * 8))) );
        }
    }

    public void writeBits(byte val, byte count) throws IOException {
        if (count > 8) throw new IllegalArgumentException("Bits count: " + count);
        if (count + pos > 8) {
            pos = (byte) (count - (8 - pos));

            if (index < buffSize) {
                buff[index] = (byte) (tmp | val >> pos);
                index++;
            } else {
                output.write(buff);
                index = 0;
                buff[index] = (byte) (tmp | val >> pos);
                index++;
            }

            tmp = (byte) (val << (8 - pos));
        } else {
            pos += count;
            tmp |= val << (8 - pos);
        }
    }

    public void writeByte(byte val) throws IOException {
        writeBits(val, (byte) 8);
    }

    @Override
    public void flush() throws IOException {
        output.write(buff, 0, index);
        output.write(tmp);
        output.flush();
    }

    @Override
    public void close() throws IOException {
        output.close();
    }
}
