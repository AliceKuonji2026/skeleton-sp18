import java.util.*;

public class Boggle {

    // File path of dictionary file
    static String dictPath = "words.txt";
    static Trie trie;
    private static int height;
    private static int width;
    private static final int[] DX = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] DY = {-1, 0, 1, -1, 1, -1, 0, 1};

    private static void buildTrie() {
        trie = new Trie();
        if (dictPath == null || dictPath.isEmpty()) {
            return;
        }
        In in = new In(dictPath);
        while (true) {
            String word = in.readLine();
            if (!in.isEmpty()) {
                trie.insert(word);
            } else {
                break;
            }
        }
    }

    /**
     * Solves a Boggle puzzle.
     *
     * @param k             The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     * The Strings are sorted in descending order of length.
     * If multiple words have the same length,
     * have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        // YOUR CODE HERE
        if (boardFilePath == null || boardFilePath.isEmpty()) {
            return null;
        }
        buildTrie();
        In in = new In(boardFilePath);
        String[] lines = in.readAllLines();

        height = lines.length;
        width = lines[0].length();
        char[][] board = new char[height][width];

        for (int i = 0; i < height; i++) {
            if (lines[i].length() != width) {
                throw new IllegalArgumentException("Board is not rectangular");
            }
            board[i] = lines[i].toLowerCase().toCharArray();
        }

        boolean[][] visited = new boolean[height][width];
        HashSet<String> found = new HashSet<>();
        List<Character> firstChars = trie.getFirstChars();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                char c = board[y][x];
                if (firstChars.contains(c)) {
                    dfs(board, x, y, trie.getFirstNode(c),visited,found);
                }
            }
        }
        List<String> res = new ArrayList<>(found);
        res.sort((a,b) -> {
            if(a.length() != b.length()) {
                return Integer.compare(b.length(),a.length());
            }
            return a.compareTo(b);
        });
        return new ArrayList<>(res.subList(0, Math.min(k, res.size())));
    }

    private static void dfs(char[][] board, int x, int y, Trie.Node n, boolean[][] visited, HashSet<String> found) {
        if(visited[y][x]) { return;}
        if (n.isWord() && n.getWord().length() > 2) {
            found.add(n.getWord());
        }
        visited[y][x] = true;
        Set<Character> nextChars = n.getNextChars();
        List<int[]> neighbors = getNeighbors(x, y);
        for (int[] p : neighbors) {
            char c = board[p[1]][p[0]];
            if (nextChars.contains(c)) {
                dfs(board, p[0], p[1], n.getNextNode(c), visited, found);
            }
        }
        visited[y][x] = false;
    }

    private static List<int[]> getNeighbors(int x, int y) {
        List<int[]> res = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int nx = x + DX[i];
            int ny = y + DY[i];
            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                res.add(new int[]{nx, ny});
            }
        }
        return res;
    }

}
