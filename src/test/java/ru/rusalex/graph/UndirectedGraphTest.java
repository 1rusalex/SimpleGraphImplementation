package ru.rusalex.graph;

import org.junit.Test;
import ru.rusalex.graph.base.Edge;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class UndirectedGraphTest {


    @Test
    public void checkPath() {
        UndirectedGraph<Integer> graph = new UndirectedGraph<>();
        graph
                .addVertex(0)
                .addVertex(4)
                .addVertex(3)
                .addVertex(-3)
                .addVertex(-8)
                .addVertex(9)
                .addVertex(8)
                .addVertex(-5)
                .addVertex(-4)
                .addVertex(-9)
                .addVertex(-10);
        graph
                .addEdge(0, 3)
                .addEdge(0, 4)
                .addEdge(3, -3)
                .addEdge(-3, -8)
                .addEdge(3, -4)
                .addEdge(-4, -10)
                .addEdge(0, 9)
                .addEdge(-9, 9)
                .addEdge(-5, 9)
                .addEdge(-5, -9)
                .addEdge(8, -5)
                .addEdge(9, 8);
        List<Edge<Integer>> edges = graph.getPath(0, -9);
        Edge<Integer> edge1 = new Edge<>(0, 9);
        Edge<Integer> edge2 = new Edge<>(-9, 9);
        assertThat(edges, hasItems(edge1, edge2));
    }

}
