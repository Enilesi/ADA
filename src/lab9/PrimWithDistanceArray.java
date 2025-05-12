package lab9;

import java.io.IOException;
import java.util.*;


public class PrimWithDistanceArray implements IMST {

    private static final int INF = Integer.MAX_VALUE;

    public PrimWithDistanceArray() {}

    public Iterable<WeightedEdge> mstEdgeList(IWeightedGraph G) {
        int V = G.getNrVertices();
        boolean[] inMST = new boolean[V];
        List<WeightedEdge> treeEdges = new ArrayList<>();
        primWithDistanceArray(G, 0, inMST, treeEdges);
        return treeEdges;
    }

    protected int primWithDistanceArray(IWeightedGraph G, int src, boolean[] inMST, List<WeightedEdge> treeEdges) {
        int V = G.getNrVertices();
        int[] dist = new int[V];
        int[] parent = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        dist[src] = 0;

        for (int count = 0; count < V; count++) {
            int minkey = Integer.MAX_VALUE;
            int v = -1;
            for (int i = 0; i < V; i++) {
                if (!inMST[i] && dist[i] < minkey) {
                    minkey = dist[i];
                    v = i;
                }
            }

            if (v == -1) break;

            inMST[v] = true;

            for (WeightedEdge e : G.edgesAdjacentTo(v)) {
                int vv = e.other(v);
                int weight = e.weight();

                if (!inMST[vv] && weight < dist[vv]) {
                    dist[vv] = weight;
                    parent[vv] = v;
                }
            }
        }

        int cost = 0;
        for (int v = 0; v < V; v++) {
            if (parent[v] != -1) {
                treeEdges.add(new WeightedEdge(parent[v], v, dist[v]));
                cost += dist[v];
            }
        }

        return cost;
    }
}



class PrimForestMatrix extends PrimWithDistanceArray {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Must add an <input-file>");
            System.exit(1);
        }

        IWeightedGraph G = new WeightedGraphAdjMatrix(args[0]);
        int V = G.getNrVertices();
        boolean[] inMST = new boolean[V];
        int treeCount = 0;

        PrimForestMatrix forest = new PrimForestMatrix();

        for (int src = 0; src < V; src++) {
            if (!inMST[src]) {
                List<WeightedEdge> treeEdges = new ArrayList<>();

                int cost = forest.primWithDistanceArray(G, src, inMST, treeEdges);
                treeCount++;

                System.out.printf("MST %d with cost = %d%n", treeCount, cost);

                for (WeightedEdge e : treeEdges) {
                    System.out.println("  " + e);
                }
                System.out.println();
            }
        }
    }
}