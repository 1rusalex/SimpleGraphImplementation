package ru.rusalex.graph.base;

import ru.rusalex.exceptions.IncorrectVertexIndexException;
import java.util.*;

public abstract class AbstractGraph<V> implements Graph<V, Edge<V>> {

    private List<V> vertices = new ArrayList<>();

    private Set<int[]> edges = new HashSet<>();

    public List<V> getVertices() {
        return vertices;
    }

    public void setVertices(List<V> vertices) {
        this.vertices = vertices;
    }

    protected Set<int[]> getEdges() {
        return edges;
    }

    protected void setEdges(Set<int[]> edges) {
        this.edges = edges;
    }

    public Graph<V, Edge<V>> addVertex(V newVertex) {
        if (newVertex == null) {
            throw new NullPointerException("New vertex can not be Null.");
        }
        if (!vertices.contains(newVertex)) {
            vertices.add(newVertex);
        }
        return this;
    }

    @Override
    public Graph<V, Edge<V>> addEdge(V firstVertex, V secondVertex) {
        int firstVertexIndex = getVertices().indexOf(firstVertex);
        int secondVertexIndex = getVertices().indexOf(secondVertex);
        if (firstVertexIndex < 0 || secondVertexIndex < 0) {
            throw new IncorrectVertexIndexException("Passes objects were not found.");
        }
        this.addEdge(firstVertexIndex, secondVertexIndex);
        return this;
    }


    @Override
    public List<Edge<V>> getPath(V startVertex, V finishVertex) {
        int startVertexIndex = getVertices().indexOf(startVertex);
        int finishVertexIndex = getVertices().indexOf(finishVertex);
        if (startVertexIndex < 0 || finishVertexIndex < 0) {
            throw new IncorrectVertexIndexException("Passes objects were not found.");
        }
        int[][] indexEdgeChain = getPathInternal(startVertexIndex, finishVertexIndex);
        return convertIndexEdgeChainIntoObjectEdgeChain(indexEdgeChain);
    }

    /**
     * In order to avoid StackOverflowError in case of large number of vertices I use a while loop instead of recursion.
     *
     * @param startVertexIndex
     * @param finishVertexIndex
     * @return
     */
    private int[][] getPathInternal(int startVertexIndex, int finishVertexIndex) {
        boolean[] visited = new boolean[getVertices().size()];
        Queue<Integer> traversedIndexes = new LinkedList<>();
        Queue<int[][]> traversedEdgeChains = new LinkedList<>();
        traversedIndexes.add(startVertexIndex);
        while (traversedIndexes.size() > 0) {
            int vertexIndex = traversedIndexes.remove();
            final int[][] edgeChain = (traversedEdgeChains.size() > 0) ? traversedEdgeChains.remove() : new int[0][];
            visited[vertexIndex] = true;
            if (vertexIndex == finishVertexIndex) {
                return edgeChain;
            }
            getEdges().parallelStream().filter(e -> (isVerticesConnectedAndNotVisited(visited, e, vertexIndex))).forEach(e -> {
                int[][] nextEdgeChain = new int[edgeChain.length + 1][];
                System.arraycopy(edgeChain, 0, nextEdgeChain, 0, edgeChain.length);
                nextEdgeChain[nextEdgeChain.length - 1] = e;
                int vertexIndexForTraversing = (e[0]==vertexIndex)?e[1]:e[0];
                traversedIndexes.offer(vertexIndexForTraversing);
                traversedEdgeChains.offer(nextEdgeChain);
            });
        }
        return null;
    }

    protected abstract boolean isVerticesConnectedAndNotVisited(boolean[] visited, int[] edge, int vertexIndex);

    protected abstract boolean isEdgeExists(int[] newEdge);


    private void addEdge(int firstVertexIndex, int secondVertexIndex) {
        int vertexSize = getVertices().size();
        if (firstVertexIndex >= vertexSize || secondVertexIndex >= vertexSize) {
            throw new IndexOutOfBoundsException("Incorrect vertex index.");
        }
        if (firstVertexIndex == secondVertexIndex) {
            throw new IncorrectVertexIndexException("The first and second vertex indices cannot be the same.");
        }
        int[] edge = {firstVertexIndex, secondVertexIndex};
        if (!isEdgeExists(edge)) {
            getEdges().add(edge);
        }
    }

    private List<Edge<V>> convertIndexEdgeChainIntoObjectEdgeChain(int[][] indexEdgeChain) {
        if (indexEdgeChain == null) {
            return null;
        }
        List<Edge<V>> edgeList = new ArrayList<>();
        for (int[] indexEdge : indexEdgeChain) {
            Edge<V> edge = new Edge<>(getVertices().get(indexEdge[0]), getVertices().get(indexEdge[1]));
            edgeList.add(edge);
        }
        return edgeList;
    }

}
