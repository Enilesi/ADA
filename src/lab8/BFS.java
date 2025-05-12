package lab8;

import java.io.*;



import java.util.*;

class BFS {
    enum Color {WHITE, GREY, BLACK}
    private static final int INFINITY = Integer.MAX_VALUE;
    private ISimpleGraph G;
    private Color[] color;
    private int[] parent;
    private int[] distTo;

    public BFS(ISimpleGraph G, int s) {
        this.G = G;
        int V = G.getNrVertices();
        color = new Color[V];
        distTo = new int[V];
        parent = new int[V];
        if (!G.hasVertex(s))
            throw new IllegalArgumentException("vertex " + s + " is not between 0 and " + (V - 1));
        bfs(G, s);
    }

    private void bfs(ISimpleGraph G, int source) {

        int V = G.getNrVertices();

        Queue<Integer> q = new LinkedList<>();

        for (int v = 0; v < V; v++) {
            distTo[v] = INFINITY;
            color[v] = Color.WHITE;
            parent[v] = -1;
        }

        color[source] = Color.GREY;
        distTo[source] = 0;
        parent[source] = -1;

        q.add(source);
        while (!q.isEmpty()) {
            int v = q.remove();
            for (int w : G.nodesAdjacentTo(v)) {
                if (color[w] == Color.WHITE) {
                    parent[w] = v;
                    distTo[w] = distTo[v] + 1;
                    color[w] = Color.GREY;
                    q.add(w);
                    System.out.print("["+v + ","+w+"] , ");
                }

            }
            color[v] = Color.BLACK;
        }
        System.out.println();
    }
    public boolean hasPathTo(int v) {
        int V = G.getNrVertices();
        if (!G.hasVertex(v))
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        return (color[v] == Color.BLACK);
    }

}

class BSFMain {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Give the name of the file to read:");
        String filename = in.nextLine().trim();

        System.out.println("Choose graph implementation: 1=Adj matrix, 2=Adj lists");
        int choice = in.nextInt();
        ISimpleGraph G;


        try {

            if (choice == 1) {

                G = new SimpleGraphMatrix(filename);

            } else if (choice == 2) {

                G = new SimpleGraphAdjList(filename);

            } else {

                System.err.println("Wrong choice!");
                return;

            }

            System.out.println("The graph from file " + filename + ":");
            G.printGraph();

            System.out.println("Enter the source vertex:");
            int source = in.nextInt();

            System.out.println("Breadth First Search Tree (edges in order):");
            new BFS(G, source);

        } catch (IOException e) {

            System.err.println("Error reading file: " + e.getMessage());

        } catch (IllegalArgumentException e) {

            System.err.println(e.getMessage());


        }

    }
}