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
    if(vertex == null){
      return;
    }
    Set<Vertex<String>>visited = new HashSet<>();
    Stack<Vertex<String>> stack = new Stack<>();

    visited.add(vertex);
    stack.push(vertex);

    while(!stack.isEmpty()){
      Vertex<String> cur = stack.pop();
      visited.add(cur);
      if(cur.data.length() < k){
        System.out.print(cur.data + " ");
      }
      for(Vertex<String> v : cur.neighbors){
        if(!visited.contains(v)){
          stack.push(v);

        } 
      }
    }
  }

  /**
   * Returns the longest word reachable from the given vertex, including its own value.
   *
   * @param vertex the starting vertex
   * @return the longest reachable word, or an empty string if the vertex is null
   */
  public static String longestWord(Vertex<String> vertex) {
    if(vertex == null){
      return "";
    }
    Set<Vertex<String>>visited = new HashSet<>();
    Stack<Vertex<String>> stack = new Stack<>();
    String max = vertex.data;
    visited.add(vertex);
    stack.push(vertex);

    while(!stack.isEmpty()){
      Vertex<String> cur = stack.pop();
      visited.add(cur);

      for(Vertex<String> v : cur.neighbors){
        if(!visited.contains(v)){
        stack.push(v);
        }
      }
      max = max.length() < cur.data.length() ? cur.data : max;
    }

    return max;
  }

  /**
   * Prints the values of all vertices that are reachable from the given vertex and 
   * have themself as a neighbor.
   *
   * @param vertex the starting vertex
   * @param <T> the type of values stored in the vertices
   */
  public static <T> void printSelfLoopers(Vertex<T> vertex) {

    if(vertex == null){
      return;
    }
    Set<Vertex<T>>visited = new HashSet<>();
    Stack<Vertex<T>> stack = new Stack<>();

    visited.add(vertex);
    stack.push(vertex);

    while(!stack.isEmpty()){
      Vertex<T> cur = stack.pop();
      visited.add(cur);
         // System.out.print(cur.data + " ");

      for(Vertex<T>v : cur.neighbors){
        if(!visited.contains(v)){
          stack.push(v);
        } 
        if(cur.equals(v)){
          System.out.print(cur.data + " ");
        }
      }
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
    Stack<Airport> stack = new Stack<>();

    visited.add(start);
    stack.push(start);

    while(!stack.isEmpty()){
      Airport cur = stack.pop();
      visited.add(cur);

      if(cur == destination){
        return true;
      }
      for(Airport v : cur.getOutboundFlights()){
        if(!visited.contains(v)){
          stack.push(v);
        } 
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
    if(!graph.containsKey(starting)){
      return graph.keySet();
    }
    Set<T> answer = new HashSet<>();
    Set<T> visited = new HashSet<>();
    Stack<T> stack = new Stack<>();
    stack.push(starting);
    visited.add(starting);
    while(!stack.isEmpty()){
      T cur = stack.pop();
      visited.add(cur);

      for(T v : graph.get(cur)){
        if(!visited.contains(v)){
          stack.push(v);
        } 
      }
    }
    for(var key : graph.keySet()){
      if(!visited.contains(key)){
        answer.add(key);
      }
    }
    

    return answer;
  }
}
