import java.util.HashSet;
import java.util.LinkedList;
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
    
    private static void printShortWords(Vertex<String> vertex, int k, Set<Vertex<String>> visited){
      if (vertex == null || visited.contains(vertex)) return;
      visited.add(vertex);

    if (vertex.data.length() < k) {
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
    //Workzone
    if(vertex == null) return "";
    List<Vertex> traveled = new LinkedList<>();
    return longestWord(vertex, traveled);
  }

  private static String longestWord(Vertex<String> vertex, List<Vertex> traveled){
    if(vertex == null || traveled.contains(vertex)) return "";
    String longest = vertex.data;
    traveled.add(vertex);
    for(Vertex<String> n : vertex.neighbors){
      String check = longestWord(n, traveled);
      if(check.length() > longest.length()) longest = check;
    }
    //Workzone End
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
    if(vertex == null) return;
    List<Vertex> a = new LinkedList<>();
    printSelfLoopers(vertex, a);
  }
  public static <T> void printSelfLoopers(Vertex<T> vertex, List<Vertex> traveled) {
    if(vertex == null) return;
    if(traveled.contains(vertex)){
      System.out.println(vertex.data);
      return;
    }
    traveled.add(vertex);
    for (Vertex n : vertex.neighbors) {
        printSelfLoopers(n, traveled);
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
    if(start == null || destination == null) return false;
    if(start == destination) return true;
    List<Airport> hist = new LinkedList<>();
    for(Airport n : start.getOutboundFlights()){
      if(canReach(n, destination, hist) == true) return true;
    }
    return false;
  }
  public static boolean canReach(Airport start, Airport destination, List<Airport> history) {
    if(start == null || destination == null || history.contains(start)) return false;
    if(start == destination) return true;
    history.add(start);
    for(Airport n : start.getOutboundFlights()){
      if(canReach(n, destination, history)) return true;
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
    if (graph == null) throw new NullPointerException("Missing Graph"); 

      Set<T> visited = new HashSet<>();
      unreachable(graph, starting, visited);
    
    Set<T> result = new HashSet<>();
    for (T key : graph.keySet()) {
      if (!visited.contains(key)) {
        result.add(key);
    }
  }
  return result;
  }
    private static <T> void unreachable(Map<T, List<T>> graph, T current, Set<T> visited  ) {
      if (visited.contains(current)) return;
      visited.add(current);

      List<T> neighbors = graph.get(current);
      if (neighbors == null) return;

    for (T neighbor : graph.get(current)) {
      unreachable(graph, neighbor, visited);
    }
  }
}
