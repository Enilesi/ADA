package lab8;

import java.io.*;

import java.util.*;

class DFS {
    enum Color {WHITE, GREY, BLACK}
    private ISimpleGraph G;
    private Color[] color;
    private int[] parent;

    public DFS(ISimpleGraph G, int s) {
        this.G = G;
        int V = G.getNrVertices();

        if (!G.hasVertex(s))
            throw new IllegalArgumentException("vertex " + s + " is not between 0 and " + (V - 1));


        color = new Color[V];
        parent = new int[V];

        for (int v = 0; v < G.getNrVertices(); v++) {
            color[v] = Color.WHITE;
        }
        dfs(G, s);
    }

    private void dfs(ISimpleGraph G, int source) {
        color[source] = Color.GREY;
        System.out.print(source + " ");
        for (int v : G.nodesAdjacentTo(source)) {
            if (color[v] == Color.WHITE) {
                parent[v] = source;
                dfs(G, v);
            }
        }
        color[source] = Color.BLACK;
    }

    public boolean isConnectedTo(int v) {
        int V = G.getNrVertices();
        if (!G.hasVertex(v))
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        return (color[v] == Color.BLACK);
    }
}

class DFSMain {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Give the name of the file to read:");
        String file = in.nextLine().trim();

        System.out.println("Choose graph implementation: 1=Adj matrix, 2=Adj lists");
        int choice = in.nextInt();
        try {

            ISimpleGraph graph;

            if (choice == 1) {

                graph = new SimpleGraphMatrix(file);

            } else if (choice == 2) {

                graph = new SimpleGraphAdjList(file);

            } else {

                System.err.println("Wrong choice!");
                in.close();
                return;

            }

            System.out.println("The graph from file " + file + ":");
            graph.printGraph();

            System.out.print("Enter source vertex for DFS: ");
            int src = in.nextInt();

            System.out.println("DFS order:");
            new DFS(graph, src);

            System.out.println();

            in.close();
        } catch (IOException e) {

            System.err.println("Error reading file: " + e.getMessage());

        } catch (IllegalArgumentException e) {

            System.err.println(e.getMessage());

        }
    }
}