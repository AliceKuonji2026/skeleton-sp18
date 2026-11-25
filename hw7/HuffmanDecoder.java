public class HuffmanDecoder {
    public static void main(String[] args) {
        String fileName = args[0];
        ObjectReader or = new ObjectReader(fileName);
        BinaryTrie trie = (BinaryTrie) or.readObject();
        BitSequence bs = (BitSequence) or.readObject();
        StringBuilder sBuilder = new StringBuilder();
        while (bs.length() > 0) {
            Match m = trie.longestPrefixMatch(bs);
            bs = bs.allButFirstNBits(m.getSequence().length());
            sBuilder.append(m.getSymbol());
        }
        FileUtils.writeCharArray(fileName.substring(0, fileName.length() - 4),
                sBuilder.toString().toCharArray());
    }
}
