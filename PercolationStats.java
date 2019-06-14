/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double sigma;
    private final double confidenceLo;
    private final double confidenceHi;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();
        double [] percents = new double[trials];
        for (int t = 0; t < trials; t++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);
                 perc.open(row, col);
            }
            percents[t] = (double) perc.numberOfOpenSites() / (double) (n * n);
        }

            mean = StdStats.mean(percents);
            sigma = StdStats.stddev(percents);
            confidenceLo = mean - (1.96 * sigma) / Math.sqrt(percents.length);
            confidenceHi = mean + (1.96 * sigma) / Math.sqrt(percents.length);
    }

    public double stddev()
    { return sigma; }

    public double confidenceLo()
    { return confidenceLo; }

    public double confidenceHi()
    { return confidenceHi; }

    public double mean()
    { return mean; }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats percSta = new PercolationStats(n, trails);
        double mean = percSta.mean();
        double stddev = percSta.stddev();
        double conLo = percSta.confidenceLo();
        double conHi = percSta.confidenceHi();
        StdOut.printf("mean%20s= %f\n", " ", mean);
        StdOut.printf("sigma%18s= %f\n", " ", stddev);
        StdOut.printf("95%% confidence interval = [%f, %f]", conLo, conHi);
    } // perform trials independent experiments on an n-by-n grid

}