package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int n;
    private WeightedQuickUnionUF ufFull;
    private WeightedQuickUnionUF uf;
    private int openSite = 0;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("N must be positive,but got: " + N);
        }
        grid = new int[N][N];//  1 mean open
        n = N;
        ufFull = new WeightedQuickUnionUF(N * N + 1);
        uf = new WeightedQuickUnionUF(N*N+2);
    }

    private int xyTo1D(int row, int col) {
        return row * n + col;
    }

    private void checkBounds(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        checkBounds(row, col);
        if(grid[row][col] == 1) { return;}
        grid[row][col] = 1;
        openSite++;
        if (row == 0) {
            ufFull.union(xyTo1D(row, col), n * n);
            uf.union(xyTo1D(row,col),n*n);
        }
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            ufFull.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            uf.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
        if (row + 1 <= n - 1 && isOpen(row + 1, col)) {
            ufFull.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            uf.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            ufFull.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (col + 1 <= n - 1 && isOpen(row, col + 1)) {
            ufFull.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if(row == n-1) {
            uf.union(xyTo1D(row,col),n*n+1);
        }
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        checkBounds(row, col);
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        checkBounds(row, col);
        if(grid[row][col] != 1) { return false;}
        return ufFull.find(xyTo1D(row, col)) == n * n;
    }

    public boolean percolates() {
        return uf.connected(n*n,n*n+1);
    }


    public int numberOfOpenSites() {
        // number of open sites
        return openSite;
    }

    public static void main(String[] args) {
        // use for unit testing (not required)
        int n = 10;
        Percolation p = new Percolation(n);
        p.grid = new int[n][n];

        for (int i = 0; i < 80; i++) {
            int row = StdRandom.uniform(n);
            int col = StdRandom.uniform(n);
            p.open(row, col);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf(p.grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(p.ufFull.find(p.xyTo1D(i, j)) + " ");
            }
            System.out.println();
        }
        System.out.println(p.ufFull.find(n * n));
        System.out.println(p.percolates());
    }
}
