package ru.rusalex.graph;

import ru.rusalex.graph.base.AbstractGraph;

public class UndirectedGraph<V> extends AbstractGraph<V> {

    @Override
    protected boolean isEdgeExists(int[] edge) {
        for (int[] existedEdge : getEdges()) {
            if (contains(existedEdge, edge[0]) && contains(existedEdge, edge[1])) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isVerticesConnectedAndNotVisited(boolean[] visited, int[] edge, int vertexIndex) {
        return ((edge[1] == vertexIndex && !visited[edge[0]]) || (edge[0] == vertexIndex && !visited[edge[1]]));
    }

    private boolean contains(final int[] array, final int v) {
        boolean result = false;
        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }
        return result;
    }

}
