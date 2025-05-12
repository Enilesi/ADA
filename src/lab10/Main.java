package lab10;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("graph.txt"));
        int V = Integer.parseInt(br.readLine().trim());


        List<WeightedEdge> edges = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null && !line.isBlank()) {

            String[] nr = line.trim().split("\\s+");
            int u = Integer.parseInt(nr[0]);
            int v = Integer.parseInt(nr[1]);
            int w = Integer.parseInt(nr[2]);
            edges.add(new WeightedEdge(u, v, w));

        }


        Collections.sort(edges);


        int forestMonth=0;
        
        long timeStart = System.nanoTime();
        
        UnionFindUpForest uniForest = new UnionFindUpForest(V);
        uniForest.init(V);
        
        
        for (WeightedEdge e : edges) {
            int u = e.either(), v = e.other(u);
            
            if (uniForest.find(u) != uniForest.find(v)) {
                
                uniForest.union(u, v);
                
                if (uniForest.count() ==1) {
                    forestMonth=e.weight();
                    break;
                };
            }
        }
        
        double dtForest = (System.nanoTime() - timeStart) / 1e6;


        int linkedMonth=0;
        
        timeStart = System.nanoTime();
        
        IUnionFind uniList = new UnionFindLinkedList(V);
        uniList.init(V);
        
        List<WeightedEdge> mstList = new ArrayList<>();
        
        for (WeightedEdge e : edges) {
            int u = e.either(), v = e.other(u);
            
            
            if (uniList.find(u) != uniList.find(v)) {
                uniList.union(u, v);
                
                mstList.add(e);
                
                if (uniList.count() ==1) {
                    linkedMonth=e.weight();
                    break;
                };
            }
        }
        double dtList = (System.nanoTime() - timeStart) / 1e6;


        System.out.printf("Kruskal with forest: %.3f ms, edges=%d%n", dtForest,forestMonth);
        System.out.printf("Kruskal with list:   %.3f ms, edges=%d%n", dtList,linkedMonth);
    }
}
