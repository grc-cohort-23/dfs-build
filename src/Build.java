import java.util.ArrayDeque;
import java.util.Deque;
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
    if (vertex == null) {
      return;
    }
    Set<Vertex<String>> visited = new HashSet<>();
    Deque<Vertex<String>> stack = new ArrayDeque<>();
    stack.push(vertex); // Add the starting vertex to the stack
    visited.add(vertex); // Mark the starting vertex as visited
    while (!stack.isEmpty()) {
      Vertex<String> current = stack.pop(); // Get the next vertex from the stack
      if (current.data != null && current.data.length() < k) {
        System.out.println(current.data); // Print the word if it meets the length criteria
      }
      for (Vertex<String> neighbor : current.neighbors) { // loops through the neighbors of the current vertex
        if (neighbor != null && !visited.add(neighbor)) { // Check if the neighbor is not null + not visited
          stack.push(neighbor); //add neighbor to stack
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
    if (vertex == null) return ""; {
  }
  String longest = "";
    Set<Vertex<String>> visited = new HashSet<>();
    Deque<Vertex<String>> stack = new ArrayDeque<>();
    stack.push(vertex);
    visited.add(vertex);
    while (!stack.isEmpty()) {
      Vertex<String> current = stack.pop();
      if (current.data != null && current.data.length() > longest.length()) {
        longest = current.data;
      }
      for (Vertex<String> neighbor : current.neighbors) {
        if (neighbor != null && visited.add(neighbor)) {
          stack.push(neighbor);
        }
      }
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
    if (vertex == null) {
      return;
    }
    Set<Vertex<T>> visited = new HashSet<>();
    Deque<Vertex<T>> stack = new ArrayDeque<>();
    stack.push(vertex);
    visited.add(vertex);
    while (!stack.isEmpty()) {
      Vertex<T> current = stack.pop();
      if (current.neighbors.contains(current)) {
        System.out.println(current.data);
  }
  for (Vertex<T> neighbor : current.neighbors) {
        if (neighbor != null && visited.add(neighbor)) {
          stack.push(neighbor);
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
    if (start == null || destination == null) {
    
    return false;
  }
  Set<Airport> visited = new HashSet<>();
  Deque<Airport> stack = new ArrayDeque<>();
  stack.push(start);
  visited.add(start);
  while (!stack.isEmpty()) {
    Airport current = stack.pop();
    if (current.equals(destination)) {
      return true;
    }
    if (current.getOutboundFlights() == null) { //check if curr has outbound flights
      continue;
    }
    for (Airport neighbor : current.getOutboundFlights()) {
      if (neighbor != null && visited.add(neighbor)) {
        stack.push(neighbor);
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
   Set<T> reachable = new HashSet<>();
   Deque<T> stack = new ArrayDeque<>();
    stack.push(starting); // add start val to stack
    reachable.add(starting); // mark start val as reachable
    while (!stack.isEmpty()) { // while stack is not empty
      T current = stack.pop(); // get next val from stack
      List<T> neighbors = graph.get(current); // get neighbors of current val
      if (neighbors != null) { // check if neighbors is not null
        continue; // if neighbors is null -> skip to next 
 }
// for (T neighbor : neighbors) { // loop through neighbors of current val
//           if (neighbor != null && reachable.add(neighbor)) { // check if neighbor is not null + not reachable
//             stack.push(neighbor); // add neighbor to stack
//           }
//         }
//       }
//     }
//     Set<T> unreachable = new HashSet<>(graph.keySet()); // create set of all keys in graph
//     unreachable.removeAll(reachable); // remove all reachable vals from set of all keys
//     return unreachable; // return set of unreachable vals
//   }
// }

for (T neighbor : neighbors) {
  if (reachable.add(neighbor)) { // check if neighbor is not already reachable
    stack.push(neighbor); // add neighbor to stack
  }
}
    }
    Set<T> result = new HashSet<>();
    for (T key : graph.keySet()) {
      if (!reachable.contains(key)) {
        result.add(key); // add unreachable key to result set
      }
    }
  for (List<T> neighbors : graph.values()) {
    for (T neighbor : neighbors) {
      if (!reachable.contains(neighbor)) {
        result.add(neighbor); // add unreachable neighbor to result set
      }
    }
  }
    return result; // return set of unreachable vals
  }
}
      
