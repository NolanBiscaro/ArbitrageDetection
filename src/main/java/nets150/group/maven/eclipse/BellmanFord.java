package nets150.group.maven.eclipse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BellmanFord {

    private Graph g;
    private double[] distance;
    private int[] parent;
    private boolean visited[];
    public Map<Integer, Double>[] potentials;
    public double closestDiff = Integer.MAX_VALUE;
    public int closestV1;
    public int closestV2;

    public double threshold;

    public BellmanFord(Graph g, Double threshold) {
        this.g = g;
        this.threshold = 1;
        distance = new double[g.getSize()];
        parent = new int[g.getSize()];
        visited = new boolean[g.getSize()];
        initDist(g);
        initParent(g);
    }

    public void run(int source) {
        distance[source] = 0;
        for (int i = 0; i < g.getSize() - 1; i++) {
            for (int u = 0; u < g.getSize(); u++) {
                for (int v : g.outNeighbors(u)) {
                  double candidateDiff = distance[u] + g.getWeight(u, v);
                    if (distance[u] != Integer.MAX_VALUE 
                        && candidateDiff < distance[v])  {
                        distance[v] = candidateDiff;
                        parent[v] = u;
                    }
                }
            }
        }
    }
    
    //final iteration of Bellman-Ford
    public int getNegativeCycleSource() {
        for (int u = 0; u < g.getSize(); u++) {
            for (int v : g.outNeighbors(u)) {
                double diff = ((distance[u] + g.getWeight(u, v)) - distance[v]);
                
                //Keeping track of smallest difference in case no arbitrage
                if (diff != 0 && diff > 0 && diff < closestDiff) {
                    closestV1 = u;
                    closestV2 = v;
                    closestDiff = diff;

                }
                //candidateDiff refers to the potential new smallest diff, indicating neg cycle
                double candidateDist = distance[u] + g.getWeight(u, v);
                if (distance[u] != Integer.MAX_VALUE 
                    && candidateDist < distance[v]) {
                    System.out.println(u + "," + v + distance[v] + "," + candidateDist + " " + (distance[v] - candidateDist));
                    parent[v] = u;
                    return v;
                }
            }
        }

        return -1;
    }

    private List<Integer> trimPath(int newSource, List<Integer> path) {
        while (path.get(0) != newSource) {
            path.remove(0);
        }
        return path;
    }

    public List<Integer> getNegativeCycles() {
        List<Integer> path = new ArrayList<>();
        int source = getNegativeCycleSource();
        if (source == -1) {
          return path;
        }
        path.add(source);
        visited[source] = true;
        int curr = parent[source];
        while (curr != source) { 
          if (visited[curr]){
            path.add(curr);
            path = trimPath(curr, path); 
            Collections.reverse(path); 
            return path;
          }
          path.add(curr);
          visited[curr] = true;
          curr = parent[curr];
        }
        path.add(curr);
        Collections.reverse(path);

        return path;
    }

    void initParent(Graph g) {
        parent = new int[g.getSize()];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
    }

    void initDist(Graph g) {
        distance = new double[g.getSize()];
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
    }

}
