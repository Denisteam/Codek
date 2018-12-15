package utils.nums_compresors;

import java.util.Arrays;

public class ZigZagVagIntConverter {
    private static byte[] buff = new byte[8];

    public static byte[] toVarInt(int value) {
        value = (value << 1) ^ (value >>> 31);
        int i = 0;
        while((value >>> 1) > 63) {
            buff[i] = (byte) (0x80 | (value & 0x7f));
            value >>>= 7;
            i++;
        }
        buff[i] = (byte) value;


        return Arrays.copyOf(buff, i + 1);
    }

    public static int fromVarInt32(byte[] value) {
        int result = 0;
        short shift = 0;
        for (byte part: value) {
            result |= ((0x7f & part) << shift);
            shift += 7;
        }

        result = (result >>> 1) ^ (result << 31);
        return result;
    }

    public static byte[] toVarInt(long value) {
        value = (value << 1) ^ (value >>> 31);
        int i = 0;
        while((value >>> 1) > 63) {
            buff[i] = (byte) (0x80 | (value & 0x7f));
            value >>>= 7;
            i++;
        }
        buff[i] = (byte) value;


        return Arrays.copyOf(buff, i + 1);
    }

    public static long fromVarInt64(byte[] value) {
        long result = 0;
        short shift = 0;
        for (byte part: value) {
            result |= ((0x7f & part) << shift);
            shift += 7;
        }

        result = (result >>> 1) ^ (result << 31);
        return result;
    }
}
