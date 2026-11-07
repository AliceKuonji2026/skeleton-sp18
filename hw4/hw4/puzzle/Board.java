package hw4.puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board implements WorldState {
    private int[][] tiles;
    private int N;
    private int[][] goal;
    private int i0;
    private int j0;
    private int hashCodeCache = -1;

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] t) {
        N = t.length;
        this.tiles = new int[N][N];
        for(int i=0;i<N;i++) {
            this.tiles[i] = t[i].clone();
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (t[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }
            }
        }
        goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goal[i][j] = i * N + j + 1;
            }
        }
        goal[N - 1][N - 1] = 0;
    }

    /**
     * returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        if (i < 0 || i >= N || j < 0 || j >= N) {
            throw new IndexOutOfBoundsException("Index out of bounds: (" + i + ", " + j + ")");
        }
        return tiles[i][j];
    }

    /**
     * Returns the board size N
     */
    public int size() {
        return N;
    }

    /**
     * Returns the neighbors of the current board
     */
    public Iterable<WorldState> neighbors() {
        Set<WorldState> neighbors = new HashSet<>();
        int[][] list = {{i0 - 1, j0}, {i0, j0 + 1}, {i0 + 1, j0}, {i0, j0 - 1}};
        for (int[] position : list) {
            if (inBound(position[0],position[1])) {
                int[][] t = new int[N][N];
                for (int r = 0; r < N; r++) {
                    t[r] = tiles[r].clone();
                }
                swap(t, i0, j0, position[0],position[1]);
                Board neighbor = new Board(t);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private void swap(int[][] t, int i1, int j1, int i2, int j2) {
        int tmp = t[i1][j1];
        t[i1][j1] = t[i2][j2];
        t[i2][j2] = tmp;
    }

    private boolean inBound(int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < N;
    }

    /**
     * Hamming estimate described below
     */
    public int hamming() {
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                res += (tiles[i][j] != 0 && tiles[i][j] != goal[i][j]) ? 1 : 0;
            }
        }
        return res;
    }

    /**
     * Manhattan estimate described below
     */
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int n = tiles[i][j];
                if (n == 0) {
                    continue;
                }
                int x = (n - 1) / N, y = (n - 1) % N;
                res += Math.abs(x - i) + Math.abs(y - j);
            }
        }
        return res;
    }

    /**
     * Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's
     *
     * @param y the reference object with which to compare.
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (tiles != null ? !Arrays.deepEquals(tiles, other.tiles) : other.tiles != null) {
            return false;
        }
        return goal != null ? Arrays.deepEquals(goal, other.goal) : other.goal == null;
    }


    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode() {
        if (hashCodeCache == -1) {
            // 利用 Java 自带的 Arrays.hashCode() 算二维数组，极其方便
            hashCodeCache = Arrays.deepHashCode(tiles);
        }
        return hashCodeCache;
    }
}
