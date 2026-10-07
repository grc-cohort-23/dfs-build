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
    printShortWords(vertex, k, new HashSet<>());
  }

  private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return;
    visited.add(vertex);

    if (vertex.data != null && vertex.data.length() < k) {
        System.out.println(vertex.data);
    }

    for (Vertex<String> neighbor : vertex.neighbors) {
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
    return longestWord(vertex, new HashSet<>());
  }

  private static String longestWord(Vertex<String> vertex, Set<Vertex<String>> visited) {
    if (vertex == null || visited.contains(vertex)) return "";
    visited.add(vertex);

    String longest = vertex.data;

    for (Vertex<String> child : vertex.neighbors) {
      String greatestFromNeighbor = longestWord(child, visited);
      if (greatestFromNeighbor.length() > longest.length()) longest = greatestFromNeighbor;
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
    printSelfLoopers(vertex, new HashSet<>());
  }

  private static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> visited){
    if (vertex == null || !visited.add(vertex)) return;

    if(vertex.neighbors.contains(vertex)){
      System.out.println(vertex.data);
    }

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
    return canReach(start, destination, new HashSet<>());
  }
  
  private static boolean canReach(Airport current, Airport dest, Set<Airport> visited) {
    if (current == null || visited.contains(current)) return false;
    visited.add(current);

    // stop if destination reached
    if (current == dest) return true;

    // return true immediately if destination is found
    for (Airport outbound : current.getOutboundFlights()) {
      if (canReach(outbound, dest, visited) == true) return true;
    }

    // return false if destination is never reached
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
    Set<T> unreachableSet = new HashSet<>(graph.keySet());
    return unreachable(graph, starting, unreachableSet);
  }

  private static <T> Set<T> unreachable(Map<T, List<T>> graph, T current, Set<T> notVisited) {
    if (!notVisited.contains(current)) return notVisited;
    notVisited.remove(current);

    for (T neighbor : graph.get(current)) {
      Set<T> notNeighbors = unreachable(graph, neighbor, notVisited);
      for (T unvisited : notVisited) {
        if (!notNeighbors.contains(unvisited)) {
          notVisited.remove(unvisited);
        }
      }
    }

    return notVisited;
  }
}
