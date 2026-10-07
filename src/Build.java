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
    printShortWords(vertex,k,new HashSet<>());
  }

  private  static void printShortWords(Vertex<String> current, int max, Set<Vertex<String>> visited){
      if(visited.contains(current)||current==null) return;
      visited.add(current);

      if(current.data.length()<max){
        System.out.println(current.data);
      }
      for(var neighbor: current.neighbors){
        printShortWords(neighbor, max, visited);
      }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    Set<Vertex<String>> set = new HashSet<>();
    return longestWord(vertex,set);
  }

  private static String longestWord(Vertex<String> vertex, Set<Vertex<String>> set) {
    if(vertex == null || set.contains(vertex))return "";
    set.add(vertex);

    String longest = vertex.data;
    for(Vertex<String> neighbor : vertex.neighbors){
      String string = longestWord(neighbor,set);
      if(string.length() > longest.length()) longest = string;
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

  private  static <T> void printSelfLoopers(Vertex<T> vertex, Set<Vertex<T>> visited){
    if(vertex==null||visited.contains(vertex)) return ;
    visited.add(vertex);
    if(vertex.neighbors.isEmpty())return;
    
    for(var neighbor: vertex.neighbors){
      
      if(neighbor == vertex) System.out.println(vertex.data);
      
      printSelfLoopers(neighbor,visited);
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
    Set<Airport> set = new HashSet<>();

    Stack<Airport> stack = new Stack<>();
    stack.push(start);

    while(!stack.isEmpty()){
      Airport current = stack.pop();
      if(set.contains(current))continue;
      set.add(current);

      if(current == destination)return true;

      for(Airport stop : current.getOutboundFlights()){
        stack.add(stop);
      } 

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
    if(graph == null) return new HashSet<>();

    Set<T> visited = new HashSet<>();
    reachable(graph,starting,visited);

    Set<T> unreached = new HashSet<>();
    for(var vertex : graph.keySet()){
      if(!visited.contains(vertex)){
        unreached.add(vertex);
      }
    }
    return unreached;
  }

  private static <T> void reachable(Map<T, List<T>> graph, T current, Set<T> set){
    if(set.contains(current))return;
    set.add(current);

    if(graph.get(current) == null)return;
    for(var next : graph.get(current)){
      reachable(graph, next, set);
    }
  }
}
