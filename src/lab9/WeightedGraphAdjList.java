package lab9;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Implementation of undirected weighted graph. Uses adjacency lists.
 */
public class WeightedGraphAdjList implements IWeightedGraph {

    protected int V; // number of nodes
    protected int E; // number of edges

    /**
     * The graph is represented as an adjacency structure.
     * At g[i] there is the collection of nodes j that are adjacent to i
     */
    protected Map<Integer, Integer>[] g = null;

    /**
     * Constructs a graph having V nodes and no edges.
     *
     * @param V number of nodes
     */
    public WeightedGraphAdjList(int V) {
        this.V = V;
        E = 0;
        g = (Map<Integer, Integer>[]) new HashMap[V];
        for (int v = 0; v < V; v++) {
            g[v] = new HashMap<>() {
            };
        }
    }

    /**
     * Constructs a graph with V nodes and E edges, reading from file.
     *
     * The expected format of the file is:
     * On the first line, the number of nodes.
     * On the next lines, the edges given as node1 node2 weight
     *
     * @param file
     * @throws IOException
     */
    public WeightedGraphAdjList(String file) throws IOException {
        initFromFile(file);
    }

    public int getNrVertices() {
        return V;
    }

    public int getNrEdges() {
        return E;
    }

    public boolean hasVertex(int node) {
        if ((node >= 0) && (node < V))
            return true;
        else
            return false;
    }

    public boolean hasEdge(int from, int to) {
        if (hasVertex(from) && hasVertex(to))
            if (g[from].containsKey(to))
                return true;
        return false;
    }

    /**
     * Adds an edge in a undirected graph by adding two directed edges.
     */
    public void addUndirectedEdge(int either, int other, int w) {
        if (hasVertex(either) && hasVertex(other)) {
            g[either].put(other, w);
            g[other].put(either, w); // in undirected graphs add 2
            // entries in the adjacency structure
            E++; // increase edge counter
        }
    }

    /**
     * Returns all edges of an undirected graph. An edge i-j is
     * reported only once, when i<j.
     */
    public Iterable<WeightedEdge> allEdges() {
        Set<WeightedEdge> edgeSet = new HashSet<>();
        for (int node = 0; node < V; node++)
            for (Integer e : g[node].keySet()) {
                if (e> node) { // in undirected graphs make sure to
                    // list every edge only once
                    WeightedEdge ed = new WeightedEdge(node, e, g[node].get(e));
                    edgeSet.add(ed);
                }
            }
        return edgeSet;
    }


    /**
     *
     * @param node
     * @return all nodes adjacent to node
     */
    public Iterable<Integer> nodesAdjacentTo(int node) {
        if ((node >= 0) && (node < V))
            return g[node].keySet();
        return null;
    }

    /**
     *
     * @param node
     * @return all edges adjacent to node
     */

    public Iterable<WeightedEdge> edgesAdjacentTo(int node) {
        Set<WeightedEdge> edgeSet = new HashSet<WeightedEdge>();
        for (Integer e : g[node].keySet()) {
            WeightedEdge ed = new WeightedEdge(node, e, g[node].get(e));
            edgeSet.add(ed);
        }
        return edgeSet;
    }

    /**
     *
     * @param file name of file. Initializes graph with nodes and edges from file.
     * @throws IOException
     */
    public void initFromFile(String file) throws IOException {
        File input = new File(file);
        Scanner is = new Scanner(input);

        V = is.nextInt();
        E = 0;
        g = (Map<Integer, Integer>[]) new HashMap[V];
        for (int v = 0; v < V; v++) {
            g[v] = new HashMap<>();
        }

        System.out.println("Reading graph with " + V + " nodes from file "
                + file + " ...");

        int from, to;
        int weight;

        while (is.hasNext()) {
            from = is.nextInt();
            to = is.nextInt();
            weight = is.nextInt();
            addUndirectedEdge(from, to, weight);

        }
        is.close();
    }

    /**
     * Print all edges. Every undirected edge is print once.
     */
    public void printGraph() {
        for (int s = 0; s < V; s++) {
            for (Integer t : g[s].keySet()) {
                if (t>s) //print undirected edges only once
                    System.out.println(s + "-" +t+"   w="+g[s].get(t));
            }
        }
    }
}