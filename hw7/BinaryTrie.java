import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable {
    private final Node root;

    static final class Node implements Comparable<Node>, Serializable {
        private final char ch;
        private final int freq;
        private final Node left;
        private final Node right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public static Node leaf(char ch, int freq) {
            return new Node(ch, freq, null, null);
        }

        public static Node internal(Node a, Node b) {
            if (a.freq <= b.freq) {
                return new Node('\0', a.freq + b.freq, a, b);
            }
            return new Node('\0', a.freq + b.freq, b, a);
        }

        private boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.freq, o.freq);
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        this.root = buildTrie(frequencyTable);
    }

    public Match longestPrefixMatch(BitSequence querySequence) {
        StringBuilder cur = new StringBuilder();
        Node currentNode = root;
        for (int i = 0; i < querySequence.length(); i++) {
            int bit = querySequence.bitAt(i);
            if (bit == 0) {
                if (currentNode.left != null) {
                    cur.append(0);
                    currentNode = currentNode.left;
                } else {
                    break;
                }
            } else {
                if (currentNode.right != null) {
                    cur.append(1);
                    currentNode = currentNode.right;
                } else {
                    break;
                }
            }
        }
        if (currentNode.isLeaf()) {
            return new Match(new BitSequence(cur.toString()), currentNode.ch);
        }
        return null;
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> table = new HashMap<>();
        dfs(root, "", table);
        return table;
    }

    private void dfs(Node n, String cur, Map<Character, BitSequence> table) {
        if (n.isLeaf()) {
            table.put(n.ch, new BitSequence(cur));
            return;
        }
        if (n.left != null) {
            dfs(n.left, cur + 0, table);
        }
        if (n.right != null) {
            dfs(n.right, cur + 1, table);
        }
    }

    private static Node buildTrie(Map<Character, Integer> frequencyTable) {
        if (frequencyTable == null || frequencyTable.isEmpty()) {
            return null;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> e : frequencyTable.entrySet()) {
            pq.add(Node.leaf(e.getKey(), e.getValue()));
        }
        while (pq.size() > 1) {
            pq.add(Node.internal(pq.poll(), pq.poll()));
        }
        return pq.poll();
    }
}
