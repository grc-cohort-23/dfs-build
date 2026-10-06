import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Build {

  /**
   * Prints words that are reachable from the given vertex and are strictly shorter than k characters.
   * If the vertex is null or no reachable words meet the criteria, prints nothing.
   *
   * @param vertex the starting vertex
   * @param k the maximum word length (exclusive)
   */
  public static void printShortWords(Vertex<String> vertex, int k) {
    Set<Vertex<String>> visited = new HashSet<>();
    printShortWords(vertex, k, visited);
  }

  private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return;
    visited.add(vertex);
    if (vertex.data.length() < k) System.out.println(vertex.data);

    for (Vertex<String> stringVertex : vertex.neighbors) {
      printShortWords(stringVertex, k, visited);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<Vertex<String>> visited = new HashSet<>();
    return longestWord(vertex, visited);
  }

  private static String longestWord(Vertex<String> vertex, Set<Vertex<String>> visited){
    if(vertex==null || visited.contains(vertex)) return "";
    visited.add(vertex);
    String longestWord = vertex.data;

    for(Vertex<String> neighbor : vertex.neighbors){
      String longestFromNeighbor = longestWord(neighbor, visited);
      if(longestFromNeighbor.length() > longestWord.length()) longestWord = longestFromNeighbor;
    }

    return longestWord;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    Set<Vertex<T>> visited = new HashSet<>();
    printSelfLoopers(vertex, visited);
  }

  private static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> visited) {
    if (vertex == null || visited.contains(vertex)) return;
    visited.add(vertex);
    if (vertex.neighbors.contains(vertex)) System.out.println(vertex.data);

    for (Vertex<T> v : vertex.neighbors) {
      printSelfLoopers(v, visited);
    }
  }

  /**
   * Determines whether it is possible to reach the destination airport through a series of flights
   * starting from the given airport. If the start and destination airports are the same, returns true.
   *
   * @param start the starting airport
   * @param destination the destination airport
   * @return true if the destination is reachable from the start, false otherwise
   */
  public static boolean canReach(Airport start, Airport destination) {
    Set<Airport> visited = new HashSet<>();
    return canReach(start, destination, visited);
  }

  private static boolean canReach(Airport start, Airport destination, Set<Airport> visited){
    if(start==destination || visited.contains(start)) return true;
    visited.add(start);

    for(Airport flight : start.getOutboundFlights()){
      boolean flightReach = canReach(flight, destination, visited);
      if(flightReach || flight==destination) return true; 
    }

    return false;
  }

  /**
   * Returns the set of all values in the graph that cannot be reached from the given starting value.
   * The graph is represented as a map where each vertex is associated with a list of its neighboring values.
   *
   * @param graph the graph represented as a map of vertices to neighbors
   * @param starting the starting value
   * @param <T> the type of values stored in the graph
   * @return a set of values that cannot be reached from the starting value
   */
  public static <T> Set<T> unreachable(Map<T, List<T>> graph, T starting) {
    Set<T> visited = new HashSet<>();

    unreachable(graph, starting, visited);

    Set<T> result = new HashSet<>(graph.keySet());
    result.removeAll(visited);

    return result;
  }

  private static <T> void unreachable(Map<T, List<T>> graph, T current, Set<T> visited) {
    if (!graph.containsKey(current)) return;
    visited.add(current);

    for (T neighbor : graph.get(current)) {
      if (!visited.contains(neighbor)) {
        unreachable(graph, neighbor, visited);
      }
    }
  }
}
