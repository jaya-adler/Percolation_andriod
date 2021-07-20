package com.example.percolation.ui;

import com.example.percolation.Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] res;
   private final int simulation;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given n <= 0 || trials <= 0");
        }
         simulation=trials;
        res= new double[simulation];
        int row, col;
        Percolation[] p = new Percolation[trials];
        for (int j=0;j<trials;j++){
            p[j]=new Percolation(n);
        }
        for (int i = 0; i < trials; i++) {
            while (!p[i].percolates()) {
                row = StdRandom.uniform(1,n+1);
                col = StdRandom.uniform(1,n+1);
                p[i].open(row, col);
            }
            res[i]= (double) p[i].numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean(){
      return StdStats.mean(res);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
    return StdStats.stddev(res);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
    return (mean()-(1.96*StdStats.stddev(res))/Math.sqrt(simulation));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return  (mean()+(1.96*StdStats.stddev(res))/Math.sqrt(simulation));
    }

    // test client (see below)
    public static void main(String[] args){
PercolationStats p = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        StdOut.println("mean                        ="+ p.mean());
        StdOut.println("stddev                      ="+p.stddev());
        StdOut.println("95% confidence interval     =["+p.confidenceLo()+",  "+p.confidenceHi()+"]");
    }
}
