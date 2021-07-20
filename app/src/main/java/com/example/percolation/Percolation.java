package com.example.percolation;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int   len,lensqr;
    private final WeightedQuickUnionUF grid;
    private final int[] opened;
    //private int topvirtual,bottomvirtual;
    public Percolation(int n){
        if (n <= 0) {
            throw new IllegalArgumentException("Given n <= 0");
        }
        len=n;
        lensqr=n*n;
        opened = new int[lensqr];
        //topvirtual=StdRandom.uniform(0,len);
        //bottomvirtual=StdRandom.uniform(lensqr-len,lensqr);
        for(int i=0;i<lensqr;i++){
            opened[i]=0;
        }
         grid =new WeightedQuickUnionUF(lensqr+2);
    }
     private void join(int i){
         if (i+1<lensqr && (i+1)%len!=0){ if (opened[i+1]==1) grid.union(i,i+1); }
         if(i-1>=0 && i%len!=0){ if(opened[i-1]==1) grid.union(i,i-1);}
         if(i+len<lensqr){
             if(opened[i+len]==1) grid.union(i,i+len);
         }
         else { grid.union(i,lensqr+1);}
         if (i-len>=0){ if(opened[i-len]==1) grid.union(i,i-len);
         }
         else { grid.union(i,lensqr);}
   /*      if(trow-1 >=0){
            up=(trow-1)*len+tcol;
            if (opened[up]==1 && isFull(trow,col)) {
                grid.union(i,up);
            }
            else if (up==topvirtual || up==bottomvirtual){ grid.union(i,up);}
        }else {
            topvirtual=i;
        }
        if (trow+1 < len){
            down=(trow+1)*len+tcol;
            if (opened[down]==1 && isFull(row+1, col)) {
                grid.union(down,i);
            }
            else if (down==topvirtual || down==bottomvirtual){ grid.union(i,down);}
        }
        else {
            bottomvirtual=i;
        }
         if (tcol+1<len){
            right=trow*len+tcol+1;
            if (opened[right]==1 && isFull(row, col+1)) {
                grid.union(i,right);
            }

        }
         if (tcol-1 >=0 ){
            left=trow*len+tcol-1;
            if (opened[left]==1 && isFull(row, tcol)) {
                grid.union(i,left);
            }
         }**/

     }
    // opens the site (row, col) if it is not open already
    private void validate(int p) {
        if (p < 0 || p > len) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (len-1));
        }
    }
    public void open(int row, int col){
        int trow=row-1;
        int tcol=col-1;
        validate(trow);
        validate(tcol);
        int i=trow*len+tcol;
     if(isOpen(row, col)){
         if(!isFull(row, col)) join(i);
     }
     else {
        opened[i]=1;
        join(i);
     }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        int trow=row-1;
        int tcol=col-1;
        validate(trow);
        validate(tcol);
        int i= trow*len+tcol;
        return opened[i]==1;
    }

    // is the site (row, col) full?
     public boolean isFull(int row, int col){
         int trow=row-1;
         int tcol=col-1;
         validate(trow);
         validate(tcol);
         int i=trow*len+tcol;
        int t1=grid.find(i);
         if(opened[i]==1) return  t1==grid.find(lensqr);
         return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        int tem=0;
       for(int j=0;j<lensqr;j++){
           if(opened[j]==1) tem+=1;
       }
      return tem;
    }

    // does the system percolate?
    public boolean percolates(){
        return grid.find(lensqr)==grid.find(lensqr + 1);
    }
    static public void main(String[] args){
        int n=Integer.parseInt(args[0]);
        int row, col;
        double res ;
        com.example.percolation.Percolation p = new com.example.percolation.Percolation(n);
            while (p.percolates()) {
                row = StdRandom.uniform(1,n+1);
                col = StdRandom.uniform(1,n+1);
                p.open(row, col);
                StdOut.print(p.opened[0]+" ");
                for(int gr=1;gr< p.lensqr;gr++){
                    if(gr%n==0){
                        StdOut.print("\n"+p.opened[gr]+" ");
                    }
                    else {StdOut.print(p.opened[gr]+" ");}
                }
                StdOut.println("\n-----------");
            }
        StdOut.print(p.opened[0]+" ");
        for(int gr=1;gr< p.lensqr;gr++){
            if(gr%n==0){
                StdOut.print("\n"+p.opened[gr]+" ");
            }
            else {StdOut.print(p.opened[gr]+" ");}
        }
        StdOut.println("\n");
            res= (double) p.numberOfOpenSites() / (n * n);
        StdOut.println(res);
    }

}
