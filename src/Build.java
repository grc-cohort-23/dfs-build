import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


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
    
    if (vertex.data.length() < k) System.out.println(vertex.data);
    for (var v : vertex.neighbors) printShortWords(v,k,visited);
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

    String longestWord = vertex.data;

    for (var v : vertex.neighbors) {
      String neighborWord = longestWord(v, visited);
      if (longestWord.length() < neighborWord.length()) {
        longestWord = neighborWord;
      }
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
    printSelfLoopers(vertex, new HashSet<>(), new HashSet<>());
  }

  private static <T> void printSelfLoopers(Vertex<T> vertex, HashSet<Vertex<T>> visited, HashSet<Vertex<T>> printed) {
    if (vertex == null) return;
    if (printed.contains(vertex)) return;
    if (visited.contains(vertex)) {
      printed.add(vertex);
      System.out.println(vertex.data);
    }
    visited.add(vertex);

    for (var v : vertex.neighbors) {
      printSelfLoopers(v,visited,printed);
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
    if (start.getAirportCode() == destination.getAirportCode()) return true;
    return canReach(start,destination, new HashSet<>());
  }

  private static boolean canReach(Airport current, Airport destination, Set<Airport> visited) {
    if (current == null || visited.contains(current)) return false;
    visited.add(current);

    for (var airport : current.getOutboundFlights()) {
      if (airport.getAirportCode() == destination.getAirportCode()) return true;
      if (canReach(airport, destination, visited)) return true;
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
    if (graph == null) return visited;
    Stack<T> stack = new Stack<>();
    stack.add(starting);

    while (!stack.isEmpty()) {
      T vertex = stack.pop();
      visited.add(vertex);

      for (var neighbor : graph.getOrDefault(vertex,new ArrayList<T>())) {
        if (!visited.contains(neighbor)) stack.add(neighbor);
      }
    }
  
    // Not ideal, should instead be a copy of keySet, but not necessary either
    graph.keySet().removeAll(visited);

    return graph.keySet();
  }
}
