package lab9;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WeightedGraphAdjMatrix implements IWeightedGraph {

    private int[][] matrix;
    private int V;
    private int E;

    public WeightedGraphAdjMatrix(String file) throws IOException {
        initFromFile(file);
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

    public boolean hasEdge(int from, int to) {
        return matrix[from][to] > 0;
    }

    public void addUndirectedEdge(int either, int other, int w) {
        if (matrix[either][other] == 0) E++;
        matrix[either][other] = w;
        matrix[other][either] = w;
    }

    public Iterable<Integer> nodesAdjacentTo(int node) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (matrix[node][i] > 0) neighbors.add(i);
        }
        return neighbors;
    }

    public Iterable<WeightedEdge> edgesAdjacentTo(int node) {
        List<WeightedEdge> list = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (matrix[node][i] > 0)
                list.add(new WeightedEdge(node, i, matrix[node][i]));
        }
        return list;
    }

    public Iterable<WeightedEdge> allEdges() {
        Set<WeightedEdge> set = new HashSet<>();
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (matrix[i][j] > 0)
                    set.add(new WeightedEdge(i, j, matrix[i][j]));
            }
        }
        return set;
    }

    public int getWeight(int u, int v) {
        return matrix[u][v];
    }

    public void initFromFile(String file) throws IOException {
        Scanner in = new Scanner(new File(file));
        V = in.nextInt();
        E = 0;
        matrix = new int[V][V];
        while (in.hasNextInt()) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            addUndirectedEdge(u, v, w);
        }
        in.close();
    }

    public void printGraph() {
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (matrix[i][j] > 0) {
                    System.out.println(i + "-" + j + " w=" + matrix[i][j]);
                }
            }
        }
    }
}
