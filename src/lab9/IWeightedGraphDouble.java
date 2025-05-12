package lab9;
import java.io.IOException;

class WeightedEdgeDouble implements Comparable<WeightedEdgeDouble> {
    private final int either;
    private final int other;
    private final double weight;

    public WeightedEdgeDouble(int either, int other, double weight) {
        this.either = either;
        this.other = other;
        this.weight = weight;
    }

    public int either() {
        return either;
    }

    public int other(int v) {
        return (v == either) ? other : either;
    }

    public double weight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.6f", either, other, weight);
    }

    @Override
    public int compareTo(WeightedEdgeDouble that) {
        return Double.compare(this.weight, that.weight);
    }
}


public interface IWeightedGraphDouble {
    int getNrVertices();
    int getNrEdges();
    void addUndirectedEdge(int either, int other, double weight);
    Iterable<Integer> nodesAdjacentTo(int node);
    Iterable<WeightedEdgeDouble> edgesAdjacentTo(int node);
    Iterable<WeightedEdgeDouble> allEdges();
    boolean hasVertex(int node);
    boolean hasEdge(int either, int other);
    void initFromFile(String file) throws IOException;

}
