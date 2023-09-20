import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Jessie Xu
 * @userid jxu645
 * @GTID 903726541
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * ad=jacency list from graph. DO NOT create new instances of Map
 w  8787     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new java.lang.IllegalArgumentException("Inputs cannot be null.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in the graph");
        }
        List<Vertex<T>> visited = new LinkedList<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        visited.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex<T> curr = queue.remove();
]            for (VertexDistance<T> neighbors : adjList.get(curr)) {
                if (!visited.contains(neighbors.getVertex())) {
                    visited.add(neighbors.getVertex());
                    queue.add(neighbors.getVertex());
                }
            }
        }
        return visited;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new java.lang.IllegalArgumentException("Inputs cannot be null.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("Start doesn't exist in the graph");
        }
        List<Vertex<T>> visited = new LinkedList<>();
        visited = rDfs(start, graph, visited, adjList);
        return visited;
    }

    /**
     * Recursive helper method for DFS.
     *
     * @param start Starting vertex on graph
     * @param graph Graph to search
     * @param visited List of vertices visited
     * @param adjList Map contains vertex and distance info
     * @return List of vertices visited
     * @param <T> the generic typing of the data
     */
    private static <T> List<Vertex<T>> rDfs(Vertex<T> start, Graph<T> graph, List<Vertex<T>> visited,
                                            Map<Vertex<T>, List<VertexDistance<T>>> adjList) {
        visited.add(start);
        for (VertexDistance<T> neighbors : adjList.get(start)) {
            if (!visited.contains(neighbors.getVertex())) {
                rDfs(neighbors.getVertex(), graph, visited, adjList);
            }
        }
        return visited;
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new java.lang.IllegalArgumentException("Inputs cannot be null.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!(adjList.containsKey(start))) {
            throw new java.lang.IllegalArgumentException("Start doesn't exist in the graph");
        }
        Set<Vertex<T>> visited = new HashSet<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        Queue<VertexDistance<T>> pq = new PriorityQueue<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        pq.add(new VertexDistance<>(start, 0));
        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {
            VertexDistance<T> curr = pq.remove();
            if (!visited.contains(curr.getVertex())) {
                visited.add(curr.getVertex());
                distanceMap.put(curr.getVertex(), curr.getDistance());
                for (VertexDistance<T> neighbors : adjList.get(curr.getVertex())) {
                    if (!visited.contains(neighbors.getVertex())) {
                        pq.add(new VertexDistance<>(neighbors.getVertex(),
                                neighbors.getDistance() + curr.getDistance()));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new java.lang.IllegalArgumentException("Inputs cannot be null.");
        }
        Map<Vertex<T>, List<VertexDistance<T>>> adjList = graph.getAdjList();
        if (!(adjList.containsKey(start))) {
            throw new java.lang.IllegalArgumentException("Start doesn't exist in the graph");
        }
        Set<Edge<T>> mst = new HashSet<>();
        Set<Vertex<T>> visited = new HashSet<>();
        Queue<Edge<T>> pq = new PriorityQueue<>();
        for (VertexDistance<T> neighbor : adjList.get(start)) {
            pq.add(new Edge<>(start, neighbor.getVertex(), neighbor.getDistance()));
        }
        visited.add(start);
        while (!pq.isEmpty() && visited.size() < graph.getVertices().size()) {
            Edge<T> curr = pq.remove();
            if (!visited.contains(curr.getV())) {
                mst.add(new Edge<>(curr.getU(), curr.getV(), curr.getWeight()));
                mst.add(new Edge<>(curr.getV(), curr.getU(), curr.getWeight()));
                visited.add(curr.getV());
                for (VertexDistance<T> neighbor : adjList.get(curr.getV())) {
                    if (!visited.contains(neighbor.getVertex())) {
                        pq.add(new Edge<>(curr.getV(), neighbor.getVertex(), neighbor.getDistance()));
                    }
                }
            }
        }
        if (mst.size() == 2 * (Math.abs(graph.getVertices().size()) - 1)) {
            return mst;
        } else {
            return null;
        }
    }
}