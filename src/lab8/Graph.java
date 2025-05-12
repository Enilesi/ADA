package lab8;

import java.io.*;



import java.util.*;

class SimpleEdge {
    private int either;
    private int other;
    public SimpleEdge(int either, int other) {
        this.either = either;
        this.other = other;
    }
    public int either() {
        return either;
    }
    public int other(int v) {
        if (v == either)
            return other;
        else
            return either;
    }
}

interface ISimpleGraph {
    int getNrVertices();
    int getNrEdges();
    void addUndirectedEdge(int either, int other);
    Iterable<Integer> nodesAdjacentTo(int node);
    Iterable<SimpleEdge> edgesAdjacentTo(int node);
    Iterable<SimpleEdge> allEdges();
    boolean hasVertex(int node);
    boolean hasEdge(int either, int other);
    void initFromFile(String file) throws IOException;
    void printGraph();
}

class SimpleGraphAdjList implements ISimpleGraph {
    protected int V;
    protected int E;
    protected Set<Integer>[] g = null;
    public SimpleGraphAdjList(int V) {
        this.V = V;
        E = 0;
        g = (Set<Integer>[]) new Set[V];
        for (int v = 0; v < V; v++) {
            g[v] = new HashSet<Integer>() {};
        }
    }
    public SimpleGraphAdjList(String file) throws IOException {
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
    public boolean hasEdge(int either, int other) {
        if (hasVertex(either) && hasVertex(other))
            if (g[either].contains(other))
                return true;
        return false;
    }
    public void addUndirectedEdge(int either, int other) {
        if (hasVertex(either) && hasVertex(other)) {
            g[either].add(other);
            g[other].add(either);
            E++;
        }
    }
    public Iterable<SimpleEdge> allEdges() {
        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
        for (int i = 0; i < V; i++)
            for (Integer j : g[i]) {
                if (j > i) {
                    SimpleEdge ed = new SimpleEdge(i, j);
                    edgeSet.add(ed);
                }
            }
        return edgeSet;
    }
    public void initFromFile(String file) throws IOException {
        File input = new File(file);
        Scanner is = new Scanner(input);
        V = is.nextInt();
        E = 0;
        g = (Set<Integer>[]) new Set[V];
        for (int v = 0; v < V; v++) {
            g[v] = new HashSet<Integer>();
        }
        System.out.println("Reading graph with " + V + " nodes from file " + file + " ...");
        int either, other;
        while (is.hasNext()) {
            either = is.nextInt();
            other = is.nextInt();
            addUndirectedEdge(either, other);
        }
    }
    public void printGraph() {
        System.out.println("Vertices (nodes): 0 .. " + (V - 1));
        System.out.println("Edges: E=" + E);
        for (int i = 0; i < V; i++) {
            for (Integer j : g[i]) {
                if (j > i)
                    System.out.println(i + "-" + j);
            }
        }
    }
    public Iterable<Integer> nodesAdjacentTo(int node) {
        if ((node >= 0) && (node < V))
            return g[node];
        return null;
    }
    public Iterable<SimpleEdge> edgesAdjacentTo(int node) {
        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
        for (Integer e : g[node]) {
            SimpleEdge ed = new SimpleEdge(node, e);
            edgeSet.add(ed);
        }
        return edgeSet;
    }
}

class SimpleGraphMatrix implements ISimpleGraph {
    protected int V;
    protected int E;
    protected int[][] g = null;
    public SimpleGraphMatrix(int V) {
        this.V = V;
        E = 0;
        g = new int[V][V];
        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++) {
                g[i][j] = 0;
            }
    }
    public SimpleGraphMatrix(String file) throws IOException {
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
    public boolean hasEdge(int either, int other) {
        if (hasVertex(either) && hasVertex(other))
            if (g[either][other] == 1)
                return true;
        return false;
    }
    public void addUndirectedEdge(int either, int other) {
        if (hasVertex(either) && hasVertex(other)) {
            g[either][other] = 1;
            g[other][either] = 1;
            E++;
        }
    }
    public Iterable<SimpleEdge> allEdges() {
        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
        for (int node1 = 0; node1 < V; node1++)
            for (int node2 = node1; node2 < V; node2++)
                if (g[node1][node2] == 1) {
                    SimpleEdge ed = new SimpleEdge(node1, node2);
                    edgeSet.add(ed);
                }
        return edgeSet;
    }
    public Iterable<Integer> nodesAdjacentTo(int node) {
        Set<Integer> nodeSet = new HashSet<>();
        if ((node >= 0) && (node < V)) {
            for (int i = 0; i < V; i++)
                if (g[node][i] == 1)
                    nodeSet.add(i);
            return nodeSet;
        }
        return null;
    }
    public Iterable<SimpleEdge> edgesAdjacentTo(int node) {
        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
        for (int i = 0; i < V; i++)
            if (g[node][i] == 1) {
                SimpleEdge ed = new SimpleEdge(node, i);
                edgeSet.add(ed);
            }
        return edgeSet;
    }
    public void initFromFile(String file) throws IOException {
        File input = new File(file);
        Scanner is = new Scanner(input);
        V = is.nextInt();
        E = 0;
        g = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++)
                g[i][j] = 0;
        }
        System.out.println("Reading graph with " + V + " nodes from file " + file + " ...");
        while (is.hasNext()) {
            int either = is.nextInt();
            int other = is.nextInt();
            addUndirectedEdge(either, other);
        }
    }
    public void printGraph() {
        System.out.println("Vertices (nodes): 0 .. " + (V - 1));
        System.out.println("Edges: E=" + E);
        for (int i = 0; i < V; i++)
            for (int j = i; j < V; j++)
                if (g[i][j] == 1)
                    System.out.println(i + "-" + j);
    }
}
