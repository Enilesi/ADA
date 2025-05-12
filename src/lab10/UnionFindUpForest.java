
package lab10;


public class UnionFindUpForest implements IUnionFind {

    private int[] parent;  // parent[i] = parent of i
    private int[] rank;   // rank[i] = rank of subtree rooted at i
    private int count;     // number of components(trees)


    public UnionFindUpForest(int n) {
        init(n);
    }

    public void init(int N){
        if (N < 0) throw new IllegalArgumentException();

        count = N;
        parent = new int[N];
        rank = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int p) {
        if (p < 0 || p >= parent.length)  throw new IllegalArgumentException();
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    public int count() {
        return count;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make root of smaller rank point to root of larger rank
        if (rank[rootP] < rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }
}
