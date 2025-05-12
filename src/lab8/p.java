//import java.io.*;
//import java.util.*;
//
//class SimpleEdge {
//    private int either;
//    private int other;
//    public SimpleEdge(int either, int other) {
//        this.either = either;
//        this.other = other;
//    }
//    public int either() {
//        return either;
//    }
//    public int other(int v) {
//        if (v == either)
//            return other;
//        else
//            return either;
//    }
//}
// interface ISimpleGraph {
//    int getNrVertices();
//    int getNrEdges();
//    void addUndirectedEdge(int either, int other);
//    Iterable<Integer> nodesAdjacentTo(int node);
//    Iterable<SimpleEdge> edgesAdjacentTo(int node);
//    Iterable<SimpleEdge> allEdges();
//    boolean hasVertex(int node);
//    boolean hasEdge(int either, int other);
//    void initFromFile(String file) throws IOException;
//    void printGraph();
//}
//
//class SimpleGraphAdjList implements ISimpleGraph {
//    protected int V;
//    protected int E;
//    protected Set<Integer>[] g = null;
//    public SimpleGraphAdjList(int V) {
//        this.V = V;
//        E = 0;
//        g = (Set<Integer>[]) new Set[V];
//        for (int v = 0; v < V; v++) {
//            g[v] = new HashSet<Integer>() {};
//        }
//    }
//    public SimpleGraphAdjList(String file) throws IOException {
//        initFromFile(file);
//    }
//    public int getNrVertices() {
//        return V;
//    }
//    public int getNrEdges() {
//        return E;
//    }
//    public boolean hasVertex(int node) {
//        if ((node >= 0) && (node < V))
//            return true;
//        else
//            return false;
//    }
//    public boolean hasEdge(int either, int other) {
//        if (hasVertex(either) && hasVertex(other))
//            if (g[either].contains(other))
//                return true;
//        return false;
//    }
//    public void addUndirectedEdge(int either, int other) {
//        if (hasVertex(either) && hasVertex(other)) {
//            g[either].add(other);
//            g[other].add(either);
//            E++;
//        }
//    }
//    public Iterable<SimpleEdge> allEdges() {
//        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
//        for (int i = 0; i < V; i++)
//            for (Integer j : g[i]) {
//                if (j > i) {
//                    SimpleEdge ed = new SimpleEdge(i, j);
//                    edgeSet.add(ed);
//                }
//            }
//        return edgeSet;
//    }
//    public void initFromFile(String file) throws IOException {
//        File input = new File(file);
//        Scanner is = new Scanner(input);
//        V = is.nextInt();
//        E = 0;
//        g = (Set<Integer>[]) new Set[V];
//        for (int v = 0; v < V; v++) {
//            g[v] = new HashSet<Integer>();
//        }
//        System.out.println("Reading graph with " + V + " nodes from file " + file + " ...");
//        int either, other;
//        while (is.hasNext()) {
//            either = is.nextInt();
//            other = is.nextInt();
//            addUndirectedEdge(either, other);
//        }
//    }
//    public void printGraph() {
//        System.out.println("Vertices (nodes): 0 .. " + (V - 1));
//        System.out.println("Edges: E=" + E);
//        for (int i = 0; i < V; i++) {
//            for (Integer j : g[i]) {
//                if (j > i)
//                    System.out.println(i + "-" + j);
//            }
//        }
//    }
//    public Iterable<Integer> nodesAdjacentTo(int node) {
//        if ((node >= 0) && (node < V))
//            return g[node];
//        return null;
//    }
//    public Iterable<SimpleEdge> edgesAdjacentTo(int node) {
//        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
//        for (Integer e : g[node]) {
//            SimpleEdge ed = new SimpleEdge(node, e);
//            edgeSet.add(ed);
//        }
//        return edgeSet;
//    }
//}
//
// class SimpleGraphMatrix implements ISimpleGraph {
//    protected int V;
//    protected int E;
//    protected int[][] g = null;
//    public SimpleGraphMatrix(int V) {
//        this.V = V;
//        E = 0;
//        g = new int[V][V];
//        for (int i = 0; i < V; i++)
//            for (int j = 0; j < V; j++) {
//                g[i][j] = 0;
//            }
//    }
//    public SimpleGraphMatrix(String file) throws IOException {
//        initFromFile(file);
//    }
//    public int getNrVertices() {
//        return V;
//    }
//    public int getNrEdges() {
//        return E;
//    }
//    public boolean hasVertex(int node) {
//        if ((node >= 0) && (node < V))
//            return true;
//        else
//            return false;
//    }
//    public boolean hasEdge(int either, int other) {
//        if (hasVertex(either) && hasVertex(other))
//            if (g[either][other] == 1)
//                return true;
//        return false;
//    }
//    public void addUndirectedEdge(int either, int other) {
//        if (hasVertex(either) && hasVertex(other)) {
//            g[either][other] = 1;
//            g[other][either] = 1;
//            E++;
//        }
//    }
//    public Iterable<SimpleEdge> allEdges() {
//        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
//        for (int node1 = 0; node1 < V; node1++)
//            for (int node2 = node1; node2 < V; node2++)
//                if (g[node1][node2] == 1) {
//                    SimpleEdge ed = new SimpleEdge(node1, node2);
//                    edgeSet.add(ed);
//                }
//        return edgeSet;
//    }
//    public Iterable<Integer> nodesAdjacentTo(int node) {
//        Set<Integer> nodeSet = new HashSet<>();
//        if ((node >= 0) && (node < V)) {
//            for (int i = 0; i < V; i++)
//                if (g[node][i] == 1)
//                    nodeSet.add(i);
//            return nodeSet;
//        }
//        return null;
//    }
//    public Iterable<SimpleEdge> edgesAdjacentTo(int node) {
//        Set<SimpleEdge> edgeSet = new HashSet<SimpleEdge>();
//        for (int i = 0; i < V; i++)
//            if (g[node][i] == 1) {
//                SimpleEdge ed = new SimpleEdge(node, i);
//                edgeSet.add(ed);
//            }
//        return edgeSet;
//    }
//    public void initFromFile(String file) throws IOException {
//        File input = new File(file);
//        Scanner is = new Scanner(input);
//        V = is.nextInt();
//        E = 0;
//        g = new int[V][V];
//        for (int i = 0; i < V; i++) {
//            for (int j = 0; j < V; j++)
//                g[i][j] = 0;
//        }
//        System.out.println("Reading graph with " + V + " nodes from file " + file + " ...");
//        while (is.hasNext()) {
//            int either = is.nextInt();
//            int other = is.nextInt();
//            addUndirectedEdge(either, other);
//        }
//    }
//    public void printGraph() {
//        System.out.println("Vertices (nodes): 0 .. " + (V - 1));
//        System.out.println("Edges: E=" + E);
//        for (int i = 0; i < V; i++)
//            for (int j = i; j < V; j++)
//                if (g[i][j] == 1)
//                    System.out.println(i + "-" + j);
//    }
//}
//
// class BFS {
//    enum Color {WHITE, GREY, BLACK}
//    private static final int INFINITY = Integer.MAX_VALUE;
//    private ISimpleGraph G;
//    private Color[] color;
//    private int[] parent;
//    private int[] distTo;
//    public BFS(ISimpleGraph G, int s) {
//        this.G = G;
//        int V = G.getNrVertices();
//        color = new Color[V];
//        distTo = new int[V];
//        parent = new int[V];
//        if (!G.hasVertex(s))
//            throw new IllegalArgumentException("vertex " + s + " is not between 0 and " + (V - 1));
//        bfs(G, s);
//    }
//    private void bfs(ISimpleGraph G, int source) {
//        int V = G.getNrVertices();
//        Queue<Integer> q = new LinkedList<>();
//        for (int v = 0; v < V; v++) {
//            distTo[v] = INFINITY;
//            color[v] = Color.WHITE;
//            parent[v] = -1;
//        }
//        color[source] = Color.GREY;
//        distTo[source] = 0;
//        parent[source] = -1;
//        q.add(source);
//        while (!q.isEmpty()) {
//            int v = q.remove();
//            for (int w : G.nodesAdjacentTo(v)) {
//                if (color[w] == Color.WHITE) {
//                    parent[w] = v;
//                    distTo[w] = distTo[v] + 1;
//                    color[w] = Color.GREY;
//                    q.add(w);
//                }
//            }
//            System.out.print(v + " ");
//            color[v] = Color.BLACK;
//        }
//        System.out.println();
//    }
//    public boolean hasPathTo(int v) {
//        int V = G.getNrVertices();
//        if (!G.hasVertex(v))
//            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
//        return (color[v] == Color.BLACK);
//    }
//    public int distTo(int v) {
//        int V = G.getNrVertices();
//        if (!G.hasVertex(v))
//            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
//        return distTo[v];
//    }
//    public Iterable<Integer> pathTo(int v) {
//        int V = G.getNrVertices();
//        if (!G.hasVertex(v))
//            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
//        if (!hasPathTo(v)) return null;
//        LinkedList<Integer> path = new LinkedList<>();
//        int x;
//        for (x = v; distTo[x] != 0; x = parent[x]) {
//            path.addFirst(x);
//        }
//        path.addFirst(x);
//        return path;
//    }
//}
//
// class DepthFirstSearch {
//    enum Color {WHITE, GREY, BLACK}
//    private ISimpleGraph G;
//    private Color[] color;
//    private int[] parent;
//    public DepthFirstSearch(ISimpleGraph G, int s) {
//        this.G = G;
//        int V = G.getNrVertices();
//        if (!G.hasVertex(s))
//            throw new IllegalArgumentException("vertex " + s + " is not between 0 and " + (V - 1));
//        color = new Color[V];
//        parent = new int[V];
//        for (int v = 0; v < G.getNrVertices(); v++) {
//            color[v] = Color.WHITE;
//        }
//        dfs(G, s);
//    }
//    private void dfs(ISimpleGraph G, int source) {
//        color[source] = Color.GREY;
//        System.out.print(source + " ");
//        for (int v : G.nodesAdjacentTo(source)) {
//            if (color[v] == Color.WHITE) {
//                parent[v] = source;
//                dfs(G, v);
//            }
//        }
//        color[source] = Color.BLACK;
//    }
//    public boolean isConnectedTo(int v) {
//        int V = G.getNrVertices();
//        if (!G.hasVertex(v))
//            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
//        return (color[v] == Color.BLACK);
//    }
//}
//
// class SimpleGraphTest {
//    public static void main(String[] args) throws IOException {
//        ISimpleGraph G = null;
//        String filename = "demo1.txt";
//        System.out.println("Choose graph implementation: 1=Adj matrix, 2=Adj lists");
//        System.out.println("Enter your choice:");
//        Scanner in = new Scanner(System.in);
//        int choice = in.nextInt();
//        if (choice == 1)
//            G = new SimpleGraphMatrix(filename);
//        else if (choice == 2)
//            G = new SimpleGraphAdjList(filename);
//        else {
//            System.out.println("Wrong choice!");
//            System.exit(1);
//        }
//        System.out.println("The graph from file " + filename);
//        G.printGraph();
//        int source = G.getNrVertices() / 2;
//        System.out.println("Depth First Search starting from " + source);
//        DepthFirstSearch search = new DepthFirstSearch(G, source);
//        System.out.println();
//        for (int v = 0; v < G.getNrVertices(); v++) {
//            if (v != source) {
//                if (search.isConnectedTo(v))
//                    System.out.println(source + " and " + v + " are connected");
//                else System.out.println(source + " and " + v + " are not connected");
//            }
//        }
//        System.out.println();
//        System.out.println("Breadth First Search starting from " + source);
//        BFS bfs = new BFS(G, source);
//        for (int v = 0; v < G.getNrVertices(); v++) {
//            if (bfs.hasPathTo(v)) {
//                System.out.print("path from " + source + " to " + v + " distance=" + bfs.distTo(v) + " Nodes: ");
//                for (int x : bfs.pathTo(v)) {
//                    System.out.print(x + " ");
//                }
//                System.out.println();
//            } else {
//                System.out.println("Nodes " + source + "-" + v + " are not connected through any path");
//            }
//        }
//    }
//}
