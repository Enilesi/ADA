package lab8;

import java.io.IOException;
import java.io.File;
import java.util.*;
class GenericSimpleEdge<T> {
    private final T either;
    private final T other;

    public GenericSimpleEdge(T either, T other) {
        this.either = either;
        this.other = other;
    }

    public T either() {
        return either;
    }

    public T other(T v) {
        if (v.equals(either)) return other;
        else return either;
    }

    @Override
    public String toString() {
        return either + "–" + other;
    }
}

interface GenericISimpleGraph<T> {
    void addUndirectedEdge(T either, T other);
    Iterable<T> nodesAdjacentTo(T node);
    Iterable<T> getAllVertices();
    boolean hasVertex(T node);
}

class GenericGraphMatrix<T> implements GenericISimpleGraph<T> {
    private int V;
    private int[][] adj;
    private Map<T,Integer> index;
    private List<T> vertices;

    public GenericGraphMatrix(Collection<T> coll) {
        this.V = coll.size();
        this.adj = new int[V][V];
        this.index = new HashMap<>();
        this.vertices = new ArrayList<>(V);

        int i = 0;

        for (T v : coll) {
            index.put(v, i++);
            vertices.add(v);
        }
    }

    @Override
    public void addUndirectedEdge(T v, T w) {
        Integer i = index.get(v), j = index.get(w);

        if (i != null && j != null && adj[i][j] == 0) {
            adj[i][j] = adj[j][i] = 1;
        }
    }

    @Override
    public Iterable<T> nodesAdjacentTo(T v) {
        Integer i = index.get(v);
        if (i == null) return Collections.emptyList();
        List<T> neighbours = new ArrayList<>();
        for (int j = 0; j < V; j++)
            if (adj[i][j] == 1)
                neighbours.add(vertices.get(j));
        return neighbours;
    }

    @Override
    public Iterable<T> getAllVertices() {
        return Collections.unmodifiableList(vertices);
    }

    @Override
    public boolean hasVertex(T v) {
        return index.containsKey(v);
    }
}