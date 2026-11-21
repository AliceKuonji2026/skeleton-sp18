import java.util.*;

public class Trie {
    private final Node root;

    static class Node {
        private boolean isWord;
        private String word;
        private final Map<Character, Node> next;

        public Node() {
            isWord = false;
            next = new HashMap<>();
        }

        public boolean isWord() {
            return this.isWord;
        }

        public String getWord() {
            if (!this.isWord) {
                return null;
            }
            return this.word;
        }

        public Set<Character> getNextChars() {
            return this.next.keySet();
        }

        public Node getNextNode(Character c) {
            if(this.next.containsKey(c)) { return next.get(c);}
            return null;
        }
    }

    public Trie() {
        root = new Node();
    }

    public List<Character> getFirstChars() {
        return new ArrayList<>(root.next.keySet());
    }
    public Node getFirstNode(char c) {
        if(root.next.containsKey(c)) { return root.next.get(c);}
        return null;
    }

    public void insert(String original) {
        if (original == null || original.isEmpty() || original.toCharArray()[0] == '\n') {
            return;
        }
        String word = original.toLowerCase();
        Node currentNode = root;
        for (char c : word.toCharArray()) {
            if (!currentNode.next.containsKey(c)) {
                currentNode.next.put(c, new Node());
            }
            currentNode = currentNode.next.get(c);
        }
        currentNode.isWord = true;
        currentNode.word = original;
    }

    public boolean findWord(String original) {
        String word = original.toLowerCase();
        Node currentNode = root;
        for (char c : word.toCharArray()) {
            if (!currentNode.next.containsKey(c)) {
                return false;
            }
            currentNode = currentNode.next.get(c);
        }
        return currentNode.isWord && currentNode.word.equals(original);
    }

    public List<String> getMatches(String prefix) {
        String word = prefix.toLowerCase();
        Node currnetNode = root;
        for (char c : word.toCharArray()) {
            if (currnetNode.next.containsKey(c)) {
                currnetNode = currnetNode.next.get(c);
            } else {
                return new ArrayList<>();
            }
        }
        List<String> res = new ArrayList<>();
        collectWords(currnetNode, res);
        return res;
    }

    private void collectWords(Node node, List<String> matches) {
        if (node.isWord) {
            matches.add(node.word);
        }
        for (Node child : node.next.values()) {
            collectWords(child, matches);
        }
    }
}
