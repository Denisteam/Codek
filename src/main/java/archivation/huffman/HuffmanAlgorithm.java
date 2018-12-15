package archivation.huffman;

import java.util.Collections;
import java.util.List;

public class HuffmanAlgorithm {
    private List<SchemaMember> members;

    public HuffmanAlgorithm(List<Symbol> members) {
        this.members.addAll(members);
        Collections.sort(this.members);
    }

    public void calculateHaffCodes() {
        if (members.size() == 1) {
            members.get(0).finalizeCalculation();
            return;
        } else {
            int lastIdx = members.size() - 1;
            SchemaMember preLast = members.get(lastIdx);
            SchemaMember last = members.get(lastIdx);
            members.remove(preLast);
            members.remove(last);
            members.add(new PseudoSymbol(preLast, last));
        }

        calculateHaffCodes();
    }
}
