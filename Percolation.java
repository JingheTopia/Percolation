/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private final int n;
   private final WeightedQuickUnionUF uf;
   private final WeightedQuickUnionUF ufBackwash;
   private boolean[][] openIf;
   private  int count;
   public Percolation(int n)
    {
        if (n <= 0)
          throw new IllegalArgumentException("n is<=0 and n should be greater than O");
        uf = new WeightedQuickUnionUF(n*n+2);
        ufBackwash = new WeightedQuickUnionUF(n*n+1);

        if (n > 1) {
            for (int i = 1; i <= n; i++) {
                uf.union(i, 0);
            }
            for (int i = 1; i <= n; i++) {
                ufBackwash.union(i, 0);
            }
            for (int j = n * n; j <= n * n && j >= n * n + 1 - n; j--) {
                uf.union(j, n * n + 1);
            }
        }
        this.n = n;
        openIf = new boolean[n+1][n+1];
        for (int i = 1; i <= n; i++)
        { for (int j = 1; j <= n; j++)
            { this.openIf[i][j] = false; }
        }
    } // create n-by-n grid, with all sites blocked
    public void open(int i, int j)
    {
        if (i < 1 || i > n)
            throw new  java.lang.IllegalArgumentException("row is out of bound");
        if (j < 1 || j > n)
            throw new  java.lang.IllegalArgumentException("col is out of bound");
        int id;
        id = (i-1)*n+j;
        if (!isOpen(i, j))
        { openIf[i][j] = true;
            count++; }
        if (j > 1 && openIf[i][j-1])
        { uf.union(id, id-1);
          ufBackwash.union(id, id-1); }
        if (j < n && openIf[i][j+1])
        { uf.union(id, id+1);
        ufBackwash.union(id, id+1); }
        if (i > 1 && openIf[i-1][j])
        { uf.union(id, id-n);
        ufBackwash.union(id, id-n); }
        if (i < n && openIf[i+1][j])
        { uf.union(id, id+n);
        ufBackwash.union(id, id+n); }
    }   // open site (row, col) if it is not open already

    public boolean isOpen(int i, int j)
    {   if (i < 1 || i > n)
            throw new  java.lang.IllegalArgumentException("row is out of bound");
        if (j < 1 || j > n)
            throw new  java.lang.IllegalArgumentException("col is out of bound");
        return openIf[i][j]; }  // is site (row, col) open?

    public boolean isFull(int i, int j)
    {  if (i < 1 || i > n || j < 1 || j > n)
        throw new java.lang.IllegalArgumentException();
    if (n == 1) return isOpen(1, 1);
    return ufBackwash.connected((i-1)*n+j, 0) && isOpen(i, j);
    } // is site (row, col) full?

    public  int numberOfOpenSites()
    { return  count; }       // number of open sites

    public boolean percolates()
    { if (n == 1) {return openIf[1][1]; }
        else if (n == 2) {
            return uf.connected(1, 3)|| uf.connected(1, 4) || uf.connected(2, 3)|| uf.connected(2, 4);
    }
        else
        return uf.connected(0, n*n+1); }              // does the system percolate?

    public static void main(String[] args)
    {
     }
}
