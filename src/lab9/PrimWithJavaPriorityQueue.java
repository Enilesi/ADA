package lab9;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PrimWithJavaPriorityQueue  {


    class NodePriority implements Comparable<NodePriority> {
        int node;
        double priority;

        NodePriority(int node, double priority) {
            this.node = node;
            this.priority = priority;
        }

        @Override
        public int compareTo(NodePriority other) {
            return Double.compare(this.priority, other.priority);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof NodePriority)
                return this.node == ((NodePriority) obj).node;
            return false;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(node);
        }
    }


    private static final double INF = Double.MAX_VALUE;


    private boolean[] inMST;
    private int[] parent;
    private double[] key;



    public Iterable<WeightedEdgeDouble> mstEdgeList(IWeightedGraphDouble G) {
        int V = G.getNrVertices();

        inMST = new boolean[V];
        key = new double[V];
        parent = new int[V];

        Arrays.fill(inMST, false);
        Arrays.fill(key, INF);
        Arrays.fill(parent, -1);

        for (int v = 0; v < V; v++)     // needed if graph is disconnected
            if (!inMST[v])               // restart Prim once in every connected component
                doPrimWithJavaPQ(G, v);  // fills out parent array

        //restore edges that form the mst
        Queue<WeightedEdgeDouble> mst = new LinkedList<>(); // edges in the MST
        for (int u = 0; u < V; u++)
            if (parent[u] != -1) {  // u is not a root of a MST tree
                WeightedEdgeDouble e = new WeightedEdgeDouble(parent[u], u, key[u]);
                mst.add(e);
            }
        return mst;
    }


    public void summing(IWeightedGraphDouble G) {
        int V = G.getNrVertices();
        inMST = new boolean[V];
        key = new double[V];
        parent = new int[V];

        Arrays.fill(key, INF);
        Arrays.fill(parent, -1);

        int treeNr = 1;
        for (int v = 0; v < V; v++) {
            if (!inMST[v]) {



                doPrimWithJavaPQ(G, v);

                double s = 0;
                Queue<WeightedEdgeDouble> mst = new LinkedList<>();
                for (int u = 0; u < V; u++) {
                    if (parent[u] != -1) {
                        s+=key[u];
                        mst.add(new WeightedEdgeDouble(parent[u], u, key[u]));
                    }
                }
                System.out.println("Sum is = "+ s);
                Arrays.fill(parent, -1);
            }
        }
    }


    private void doPrimWithJavaPQ(IWeightedGraphDouble G, int src)  {
        // Priority queue to store vertices being processed
        PriorityQueue<NodePriority> pq = new PriorityQueue<>();

        // Insert source vertex into priority queue and set its priority key to 0
        key[src] = 0.0;
        pq.add(new NodePriority(src, key[src]));

        // Loop until priority queue becomes empty
        while (!pq.isEmpty()) {
            int v = pq.remove().node; // Extract vertex with minimum key

            inMST[v] = true; // Include vertex v in MST

            // Traverse all adjacent vertices of v
            for (WeightedEdgeDouble  e : G.edgesAdjacentTo(v)) {
                int vv = e.other(v);
                double weight = e.weight();

                // If vv is not in MST and weight of (v,vv) is smaller than current key of vv
                if (!inMST[vv] && key[vv] > weight) {
                    // Update key of vv - realize equivalent of decreaseKey
                    pq.remove(new NodePriority(vv, key[vv]));
                    key[vv] = weight;
                    pq.add(new NodePriority(vv, key[vv]));
                    parent[vv] = v;  //
                }
            }
        }
        // in this point we have the MST of the connected component of src
    }

    public static void main(String[] args) throws IOException {

            IWeightedGraphDouble G = new EuclideanGraph("points_10000.txt");

            PrimWithJavaPriorityQueue prim = new PrimWithJavaPriorityQueue();
            prim.summing(G);
        }



}

