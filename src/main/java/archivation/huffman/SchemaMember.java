package archivation.huffman;

public abstract class SchemaMember implements Comparable<SchemaMember> {
    private int frequency;

    public SchemaMember(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(SchemaMember o) {
        return (frequency < o.frequency) ? -1 : ((frequency == o.frequency) ? 0 : 1);
    }

    abstract void finalizeCalculation();

    abstract void addBit(byte val);
}
