package archivation.huffman;

public class Symbol extends SchemaMember {
    private char symbol;
    private HuffmanCode huffCode = new HuffmanCode();
    private boolean codeComputed = false;

    public Symbol(char symbol, int frequency) {
        super(frequency);
        this.symbol = symbol;
    }

    HuffmanCode getHuffCode() {
        if (codeComputed) {
            return huffCode;
        } else throw new CodeNotComputedException();
    }

    boolean isCodeComputed() {
        return codeComputed;
    }

    void finalizeCalculation() {
        codeComputed = true;
    }

    void addBit(byte val) {
        if (!codeComputed) {
            huffCode.addBit(val);
        } else throw new AlreadyComputedException();
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "symbol: " + symbol + ", frequency: " + getFrequency();
    }
}
