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
    printShortWords(vertex, k, new HashSet<Vertex<String>>());
  }

  private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> visited){
    if(vertex == null || visited.contains(vertex)) return;
    visited.add(vertex);
    if(vertex.data.length() < k) System.out.println(vertex.data);
    for(Vertex<String> neighbor : vertex.neighbors){
      printShortWords(neighbor, k, visited);
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    return longestWord(vertex, new HashSet<Vertex<String>>());
  }

  private static String longestWord(Vertex<String> vertex, Set<Vertex<String>> visited){
    if(vertex == null || visited.contains(vertex)) return "";
    visited.add(vertex);
    String longest = vertex.data;
    for(Vertex<String> neighbor : vertex.neighbors){
      String longestNeighbor = longestWord(neighbor, visited);
      if(longestNeighbor.length() > longest.length()) longest = longestNeighbor;
    }
    return longest;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {
    printSelfLoopers(vertex, new HashSet<Vertex<T>>());
  }

  private static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> visited){
    if(vertex == null || visited.contains(vertex)) return;
    visited.add(vertex);
    if(vertex.neighbors.contains(vertex)) System.out.println(vertex.data);
    for(Vertex<T> neighbor : vertex.neighbors){
      printSelfLoopers(neighbor, visited);
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
    return canReach(start, destination, new HashSet<Airport>());
  }

  private static boolean canReach(Airport start, Airport destination, Set<Airport> visited){
    if(start == null || destination == null || visited.contains(start)) return false;
    if(start.equals(destination)) return true;
    visited.add(start);
    if(start.getOutboundFlights().contains(destination)) return true;
    boolean reachable = false;
    for(Airport neighbor : start.getOutboundFlights()){
      reachable = canReach(neighbor, destination, visited);
    }
    return reachable;
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
    Set<T> path = new HashSet<>();
    Set<T> result = new HashSet<>();
    findPath(graph, starting, path);
    for(T current : graph.keySet()){
      if(!path.contains(current)) result.add(current);
    }
    return result;
  }

  private static <T> void findPath(Map<T, List<T>> graph, T current, Set<T> visited){
    if(current == null || visited.contains(current)) return;
    visited.add(current);
    List<T> neighbors = graph.get(current);
    if(neighbors == null) return;
    for(T neighbor : neighbors) findPath(graph, neighbor, visited);
  }
}
