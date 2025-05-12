package lab9;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EuclideanGraph implements IWeightedGraphDouble {

    private int V;
    private int E;
    private double[][] weights;
    private int[][] coords;

    public EuclideanGraph(String filename) throws IOException {
        initFromFile(filename);
    }

    public int getNrVertices() {
        return V;
    }

    public int getNrEdges() {
        return E;
    }

    public boolean hasVertex(int node) {
        return node >= 0 && node < V;
    }

    public boolean hasEdge(int either, int other) {
        return hasVertex(either) && hasVertex(other) && weights[either][other] > 0;
    }

    public void addUndirectedEdge(int either, int other, double weight) {
        if (hasVertex(either) && hasVertex(other)) {
            if (weights[either][other] == 0) E++;
            weights[either][other] = weight;
            weights[other][either] = weight;
        }
    }

    public Iterable<Integer> nodesAdjacentTo(int node) {
        List<Integer> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (weights[node][i] > 0) adj.add(i);
        }
        return adj;
    }

    public Iterable<WeightedEdgeDouble> edgesAdjacentTo(int node) {
        List<WeightedEdgeDouble> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (weights[node][i] > 0)
                edges.add(new WeightedEdgeDouble(node, i, weights[node][i]));
        }
        return edges;
    }

    public Iterable<WeightedEdgeDouble> allEdges() {
        Set<WeightedEdgeDouble> edges = new HashSet<>();
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (weights[i][j] > 0)
                    edges.add(new WeightedEdgeDouble(i, j, weights[i][j]));
            }
        }
        return edges;
    }

    public void initFromFile(String filename) throws IOException {
        Scanner sc = new Scanner(new File(filename));
        V = sc.nextInt();
        coords = new int[V][2];
        weights = new double[V][V];
        E = 0;

        for (int i = 0; i < V; i++) {
            coords[i][0] = sc.nextInt();
            coords[i][1] = sc.nextInt();
        }

        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                int dx = coords[i][0] - coords[j][0];
                int dy = coords[i][1] - coords[j][1];
                double dist = Math.sqrt(dx * dx + dy * dy);
                addUndirectedEdge(i, j, dist);
            }
        }
        sc.close();
    }

}