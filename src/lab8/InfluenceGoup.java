package lab8;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class InfluenceGoup {
    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);

        System.out.println("Give the name of the file to read:");

        String file = in.nextLine().trim();

        Scanner scanner = new Scanner(new File(file));


        int N = Integer.parseInt(scanner.nextLine().trim());

        List<String> names = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            names.add(scanner.nextLine().trim());
        }


        GenericGraphMatrix<String> graph = new GenericGraphMatrix<>(names);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] pers = line.split("\s+");
            if (pers.length >= 2) graph.addUndirectedEdge(pers[0], pers[1]);
        }

        scanner.close();

        Set<String> visited = new HashSet<>();

        int maxSize = 0;

        List<String> largestGroup = new ArrayList<>();
        for (String n : names) {

            if (!visited.contains(n)) {

                GenericDFS<String> dfs = new GenericDFS<>(graph, n);

                List<String> comp = dfs.getComponent();

                visited.addAll(comp);

                if (comp.size() > maxSize) {
                    maxSize = comp.size();
                    largestGroup = comp;
                }


            }
        }

        System.out.println("Biggest group size " + maxSize);
        System.out.println("Group membrs:");
        for (String m : largestGroup) System.out.println(m);
    }
}

