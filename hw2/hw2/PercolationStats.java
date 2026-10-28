package hw2;
import edu.princeton.cs.introcs.StdStats;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private double[] thresholds;
    private int T;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if(N<=0 || T<=0) {
            throw new IllegalArgumentException("T and N must be positive,but got: " + T + N);
        }
        this.T = T;
        thresholds = new double[T];
        for(int i=0;i<T;i++) {
            thresholds[i] = (double) simulation(N, pf) / (N * N);
        }

    }

    private int simulation(int N,PercolationFactory pf){
        Percolation p = pf.make(N);
        while(!p.percolates()) {
            int i = StdRandom.uniform(N);
            int j = StdRandom.uniform(N);
            if(p.isOpen(i,j)) {
                continue;
            }else{
                p.open(i,j);
            }
        }
        return p.numberOfOpenSites();
    }

    public double mean(){
        // sample mean of percolation threshold
        return StdStats.mean(thresholds);
    }

    public double stddev(){
        // sample standard deviation of percolation threshold
        return StdStats.stddev(thresholds);
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return mean() - (1.96 * stddev()) / Math.sqrt(T);
    }

    public double confidenceHigh(){
        return mean() + (1.96 * stddev()) / Math.sqrt(T);
    }
}
