package ru.rusalex.graph.base;

import java.util.List;

public interface Graph<V,E> {

    Graph<V,E> addVertex(V newVertex);

    Graph<V,E> addEdge(V firstVertex, V secondVertex);

    List<E> getPath(V startVertex, V finishVertex);

}
