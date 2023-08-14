import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MinimalNetwork {

  public static void main(String[] args) throws IOException {
    BufferedReader buf = new BufferedReader(
        new InputStreamReader(new FileInputStream("0107_network.txt")));
    var line = "";
    List<String> lines = new ArrayList<>();
    while ((line = buf.readLine()) != null) {
      lines.add(line);
    }
    buf.close();

    int[][] mtx = new int[lines.size()][lines.size()];
    int idx = 0;
    for (String row : lines) {
      List<Integer> weights = Arrays.stream(row.split(","))
          .map(w -> Objects.equals("-", w) ? "-1" : w)
          .map(Integer::parseInt).collect(Collectors.toList());
      for (int i = 0; i < mtx.length; i++) {
        mtx[idx][i] = weights.get(i);
      }
      idx++;
    }

    System.out.println(new Graph(mtx).getMaximumSavingForMinimalNetwork());
  }

  static class Graph {

    int vCount;
    List<Edge> edges;

    public Graph(int[][] mtx) {
      vCount = mtx.length;
      edges = new ArrayList<>();
      for (int i = 0; i < vCount; i++) {
        for (int j = i; j < vCount; j++) {
          if (mtx[i][j] > 0) {
            edges.add(new Edge(i, j, mtx[i][j]));
          }
        }
      }
    }

    public int getMaximumSavingForMinimalNetwork() {
      int output = 0;
      List<Edge> sortedEdges = edges.stream().sorted()
          .collect(Collectors.toList());

      List<Set<Integer>> markedVertex = new ArrayList<>();

      for (var edge : sortedEdges) {
        boolean cycleDetected = false;
        for (var cluster : markedVertex) {
          if (cluster.contains(edge.v1) && cluster.contains(edge.v2)) {
            // cycle detected
            cycleDetected = true;
            break;
          }
        }

        if (cycleDetected) {
          output += edge.weight;
          continue;
        }

        List<Integer> connectingClusterIdx = new ArrayList<>();
        for (int i = 0; i < markedVertex.size(); i++) {
          if (markedVertex.get(i).contains(edge.v1)
              || markedVertex.get(i).contains(edge.v2)) {
            connectingClusterIdx.add(i);
          }
        }

        if (connectingClusterIdx.isEmpty()) {
          markedVertex.add(new HashSet<>(Set.of(edge.v1, edge.v2)));
        } else if (connectingClusterIdx.size() == 1) {
          markedVertex.get(
              connectingClusterIdx.get(0)).addAll(List.of(edge.v1, edge.v2));
        } else {
          // merge cluster
          markedVertex.get(connectingClusterIdx.get(0))
              .addAll(markedVertex.get(connectingClusterIdx.get(1)));
          markedVertex.remove(connectingClusterIdx.get(1).intValue());
        }

        System.out.println(markedVertex);
      }

      return output;
    }
  }

  static class Edge implements Comparable<Edge> {

    int v1;
    int v2;
    int weight;

    public Edge(int v1, int v2, int weight) {
      this.v1 = v1;
      this.v2 = v2;
      this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
      return Integer.compare(this.weight, o.weight);
    }

    @Override
    public String toString() {
      return "(" + v1 + ", " + v2 + ", " + weight + ")";
    }
  }
}
