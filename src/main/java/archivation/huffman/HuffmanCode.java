package archivation.huffman;

public class HuffmanCode {
    private int value = 0;
    private byte length = 0;

    public void addBit(byte val) {
        if (val != 0 && val != 1) {
            throw new IllegalArgumentException(String.valueOf(val));
        }
        value <<= 1;
        value |= val & 0b1;
        length++;
    }

    public int getValue() {
        return value;
    }

    public byte getLength() {
        return length;
    }
}
