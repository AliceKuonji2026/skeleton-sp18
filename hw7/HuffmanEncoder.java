import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        if (inputSymbols == null) {
            throw new IllegalArgumentException("text should not be null");
        }
        Map<Character, Integer> freq = new HashMap<>();
        for (char inputSymbol : inputSymbols) {
            freq.merge(inputSymbol, 1, Integer::sum);
        }
        return freq;
    }

    public static void main(String[] args) {
        String fileName = args[0];
        char[] text = FileUtils.readFile(fileName);
        BinaryTrie trie = new BinaryTrie(buildFrequencyTable(text));
        ObjectWriter ow = new ObjectWriter(fileName + ".huf");
        ow.writeObject(trie);
        Map<Character, BitSequence> lookUpTable = trie.buildLookupTable();
        List<BitSequence> pieces = new ArrayList<>();
        for (char c : text) {
            pieces.add(lookUpTable.get(c));
        }
        BitSequence encoded = BitSequence.assemble(pieces);
        ow.writeObject(encoded);
    }
}