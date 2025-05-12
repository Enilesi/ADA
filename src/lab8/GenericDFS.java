package lab8;

import java.io.*;

import java.util.*;

public class GenericDFS<T> {
    private enum Color { WHITE, GREY, BLACK }
    private GenericISimpleGraph<T> G;
    private Map<T,Color> color;
    private Map<T,T> parent;
    private List<T> component;

    public GenericDFS(GenericISimpleGraph<T> G, T verti) {
        this.G = G;
        color= new HashMap<>();
        parent= new HashMap<>();
        component= new ArrayList<>();

        for (T v : G.getAllVertices())
            color.put(v, Color.WHITE);

        if (!G.hasVertex(verti))
            throw new IllegalArgumentException("vertex " + verti + " not in graph");

        dfs(verti);
    }

    private void dfs(T verti) {

        color.put(verti, Color.GREY);

        component.add(verti);

        for (T v : G.nodesAdjacentTo(verti)) {

            if (color.get(v) == Color.WHITE) {
                parent.put(v, verti);
                dfs(v);
            }

        }
        color.put(verti, Color.BLACK);
    }



    public List<T> getComponent() {
        return component;
    }

}