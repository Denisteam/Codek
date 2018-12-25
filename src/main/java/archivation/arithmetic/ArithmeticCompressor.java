package archivation.arithmetic;

import utils.BitsWriter;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class ArithmeticCompressor {
    public static final long INTERVAL_SIZE = 1L << 8;
    private static final long HALF = INTERVAL_SIZE >> 1;
    private static final long FIRST_QUARTER = INTERVAL_SIZE >> 2;
    private static final long THIRD_QUARTER = HALF + FIRST_QUARTER;

    private InputStream source;
    private Writer dest;
    private BitsWriter out;
    private char[] chars;
    private int[] frequency;
    private int sourceSize;

    /**
     * @param source Some data source.
     * @param freqTable Table with frequency of source`s characters.
     * @param dest Data destination.
     */
    public ArithmeticCompressor(InputStream source, TreeMap<Character, Integer> freqTable, OutputStream dest) {
        this.source = source;
        this.dest = new OutputStreamWriter(dest);
        this.out = new BitsWriter(dest);
        chars = new char[freqTable.size() + 1];
        frequency = new int[freqTable.size() + 1];

        frequency[0] = 0;
        int sum = 0;
        int i = 1;
        for (Map.Entry<Character, Integer> entry: freqTable.entrySet()) {
            sum += entry.getValue();
            chars[i] = entry.getKey();
            frequency[i] = sum;
            i++;
        }
        sourceSize = sum;
    }

    private int followingBits = 0;
    public void compress() throws IOException {
        Reader reader = new InputStreamReader(source);
        long l = 0;
        long h = INTERVAL_SIZE - 1;
        long l0;
        long h0 = 0;
        int r;
        while ((r = reader.read()) != -1) {
            int i = Arrays.binarySearch(chars, (char)r);

            if (i < 0) {
                out.close();
                reader.close();
                throw new IncompatibleShemaException();
            }
            l0 = l;
            l = l0 + frequency[i - 1] * (h - l0 + 1) / sourceSize;
            h = l0 + frequency[i]     * (h - l0 + 1) / sourceSize - 1;
            h0 = h;

            while (true) {
                if (h < HALF) {
                    bitsPlusFollow(0);
                } else if (l >= HALF) {
                    bitsPlusFollow(1);
                    l -= HALF;
                    h -= HALF;
                } else if ((l >= FIRST_QUARTER) && (h < THIRD_QUARTER)) {
                    followingBits++;
                    l -= FIRST_QUARTER;
                    h -= FIRST_QUARTER;
                } else break;
                l <<= 1;
                h = (h << 1) + 1;
            }
        }

        if (followingBits > 0) {
            out.writeOneBit(1);
        }

       // System.out.println((byte) ((h + l) >> 24));
       /* out.writeByte((byte) (0xff & ((h0 - 1) >>> 24)));
        out.writeByte((byte) (0xff & ((h0 - 1) >>> 16)));
        out.writeByte((byte) (0xff & ((h0 - 1) >>> 8)));
        out.writeByte((byte) (0xff & (h0 - 1)));*/
        out.flush();
        out.close();
    }

    private void bitsPlusFollow(int val) throws IOException {
        out.writeOneBit(val);
        while (followingBits > 0) {
            out.writeOneBit(val ^ 1);
            followingBits--;
        }
    }

    public void decompress() throws IOException {
        byte[] buff = new byte[4];
        source.read(buff);
        //TODO: 
        long value = ((long) buff[0] << 24) + ((long) buff[1] << 16) + ((long) buff[2] << 8) + buff[3];
        value &= (1L << 32) - 1;

        byte[] next = new byte[1];
        source.read(next);
        int shift = 7;

        long l = 0;
        long h = INTERVAL_SIZE - 1;
        int freq;
        int j;
        for (int i = 0; i < sourceSize; i++) {
            freq = (int) (((value - l + 1) * sourceSize - 1) / (h - l + 1));

            for (j = 0; frequency[j] <= freq; j++);
            dest.write(chars[j]);
            System.out.println(chars[j]);

            long l0 = l;
            l = l0 + frequency[j - 1] * (h - l0 + 1) / sourceSize;
            h = l0 + frequency[j]     * (h - l0 + 1) / sourceSize - 1;

            while (true) {
                if (h < HALF) {}
                else if (l >= HALF) {
                    l -= HALF;
                    h -= HALF;
                    value -= HALF;
                } else if ((l >= FIRST_QUARTER) && (h < THIRD_QUARTER)) {
                    l -= FIRST_QUARTER;
                    h -= FIRST_QUARTER;
                    value -= FIRST_QUARTER;
                } else break;
                l <<= 1; h = (h << 1) + 1;
                value <<= 1;
                value += (next[0] >>> shift) & 1;

                shift--;
                if (shift < 0) {
                    source.read(next);
                    shift = 7;
                }
            }
        }
        dest.flush();
        dest.close();

    }

}
