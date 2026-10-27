package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int[][] fullGrid;
    private int n;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if(N<=0){
            throw new IllegalArgumentException("N must be positive,but got: " + N);
        }
        grid = new int[N][N];//  1 mean open
        fullGrid = new int[N][N]; // 1 mean full
        n=N;
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        try{
            grid[row][col] = 1;
        }catch (IndexOutOfBoundsException e){
            System.out.println("Index out of bounds!");
        }
    }

    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        try{
            return grid[row][col] == 1;
        }catch(IndexOutOfBoundsException e){
            System.out.println("Index out of bounds!");
            return false;
        }
    }

    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        try{
            return grid[row][col] == 1;
        }catch(IndexOutOfBoundsException e){
            System.out.println("Index out of bounds!");
            return false;
        }
    }
    private void buildFullGrid() {
        for(int j=0;j<n;j++) {
            buildHelper(0,j);
        }
    }
    private void buildHelper(int row,int col){
        if(grid[row][col] == 0) { return; }

        fullGrid[row][col] = 1;
        if(row-1 >= 0) { buildHelper(row-1,col);}
        if(row+1 <= n-1) { buildHelper(row+1,col);}
        if(col-1 >= 0) { buildHelper(row,col-1);}
        if(col+1 <= n-1) { buildHelper(row,col+1);}
    }

    public int numberOfOpenSites() {
        // number of open sites
        int res=0;
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                res+=grid[i][j];
            }
        }
        return res;
    }

    public boolean percolates(){
        // does the system percolate?
        buildFullGrid();
        for(int i=0;i<n;i++) {
            if(fullGrid[n-1][i] == 1) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        // use for unit testing (not required)
        int n=5;
        Percolation p = new Percolation(n);
        p.grid = new int[][] {
                {1, 1, 1, 0, 1},
                {0, 1, 0, 0, 0},
                {0, 1, 1, 1, 1},
                {0, 1, 1, 0, 1},
                {1, 0, 0, 0, 1}};
        p.buildFullGrid();
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                System.out.printf(p.grid[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                System.out.printf(p.fullGrid[i][j]+" ");
            }
            System.out.println();
        }
    }
}
