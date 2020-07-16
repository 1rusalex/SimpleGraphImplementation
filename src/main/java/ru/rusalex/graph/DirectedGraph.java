package ru.rusalex.graph;

import ru.rusalex.graph.base.AbstractGraph;
import java.util.*;

public class DirectedGraph<V> extends AbstractGraph<V> {

    @Override
    protected boolean isEdgeExists(int[] edge) {
        for (int[] existedEdge : getEdges()) {
            if (Arrays.equals(existedEdge, edge)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isVerticesConnectedAndNotVisited(boolean[] visited, int[] edge, int vertexIndex) {
        return edge[0] == vertexIndex && !visited[edge[1]];
    }

}
