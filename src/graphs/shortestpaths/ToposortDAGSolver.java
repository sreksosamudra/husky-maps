package graphs.shortestpaths;

import graphs.Edge;
import graphs.Graph;
import minpq.DoubleMapMinPQ;
import minpq.MinPQ;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();

        // Perform DFS postorder traversal
        Set<V> visited = new HashSet<>();
        List<V> postorder = new ArrayList<>();
        dfsPostOrder(graph, start, visited, postorder);

        // Reverse the postorder list
        Collections.reverse(postorder);

        // Relax the edges
        relaxEdges(graph, start);


    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {

        if (!visited.contains(start)) {
            visited.add(start);
            if (!graph.neighbors(start).isEmpty()) {
                for (Edge<V> edge : graph.neighbors(start)) {
                    V to = edge.to;
                    if(!visited.contains(to)) {
                        dfsPostOrder(graph, to, visited, result);
                    }
                    result.add(start);
                }
            }
        }
    }

    private void relaxEdges(Graph<V> graph, V start) {
        MinPQ<V> perimeter = new DoubleMapMinPQ<>();
        perimeter.add(start, 0.0);

        // The shortest path from the start to the start requires no edges (0 cost).
        edgeTo.put(start, null);
        distTo.put(start, 0.0);

        Set<V> visited = new HashSet<>();

        while (!perimeter.isEmpty()) {
            V from = perimeter.removeMin();
            visited.add(from);

            for (Edge<V> edge : graph.neighbors(from)) {
                V to = edge.to;
                // oldDist is the weight of the best-known path not using this edge.
                double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
                // newDist is the weight of the shortest path using this edge.
                double newDist = distTo.get(from) + edge.weight;
                // Check that we haven't added the vertex to the SPT already...
                // AND the path using this edge is better than the best-known path.
                if (!visited.contains(to) && newDist < oldDist) {
                    edgeTo.put(to, edge);
                    distTo.put(to, newDist);
                    perimeter.addOrChangePriority(to, newDist);

//                    System.out.println(edgeTo);
                }
                // This entire if block is called "relaxing" an edge.
            }
        }
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
