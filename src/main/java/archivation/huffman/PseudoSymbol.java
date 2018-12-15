package archivation.huffman;

public class PseudoSymbol extends SchemaMember {
    private SchemaMember lessLikely;
    private SchemaMember moreLikely;

    public PseudoSymbol(SchemaMember memberOne, SchemaMember memberTwo) {
        super(memberOne.getFrequency() + memberTwo.getFrequency());

        if (memberOne.getFrequency() < memberTwo.getFrequency()) {
            this.lessLikely = memberOne;
            this.moreLikely = memberTwo;
        } else {
            this.lessLikely = memberTwo;
            this.moreLikely = memberOne;
        }
    }

    void addBit(byte val) {
        lessLikely.addBit(val);
        moreLikely.addBit(val);
    }

    @Override
    void finalizeCalculation() {
        lessLikely.finalizeCalculation();
        moreLikely.finalizeCalculation();
    }
}
