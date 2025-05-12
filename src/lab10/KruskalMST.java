
package lab10;



import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;



public class KruskalMST implements IMST {

    @Override
    public Iterable<WeightedEdge> mstEdgeList(IWeightedGraph G) {

        int V = G.getNrVertices();
        int E = G.getNrEdges();

        Queue<WeightedEdge> mst = new LinkedList<>();
        WeightedEdge[] edges = new WeightedEdge[E];
        int t = 0;
        for (WeightedEdge e : G.allEdges()) {
            edges[t++] = e;
        }
        Arrays.sort(edges);


        IUnionFind uf = new UnionFindUpForest(V);
        for (int i = 0; i < E && mst.size() < V - 1; i++) {
            WeightedEdge e = edges[i];
            int v = e.either();
            int w = e.other(v);


            if (uf.find(v) != uf.find(w)) {
                uf.union(v, w);
                mst.add(e);
            }
        }
        return mst;
    }

}