
package lab10;

import lab10.WeightedEdge;

import java.io.IOException;


public interface IWeightedGraph {
    int getNrVertices();
    int getNrEdges();
    void addUndirectedEdge(int either, int other, int w);
    Iterable<Integer> nodesAdjacentTo(int node);
    Iterable<WeightedEdge> edgesAdjacentTo(int node);
    Iterable<WeightedEdge> allEdges();
    boolean hasVertex(int node);
    boolean hasEdge(int from, int to);
    void initFromFile(String file) throws IOException;
    void printGraph();
}