package ru.rusalex.graph.base;

public class Edge<V> {

    private V firstVertex;

    private V secondVertex;

    public Edge(V firstVertex, V secondVertex) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
    }

    public V getFirstVertex() {
        return firstVertex;
    }

    public void setFirstVertex(V firstVertex) {
        this.firstVertex = firstVertex;
    }

    public V getSecondVertex() {
        return secondVertex;
    }

    public void setSecondVertex(V secondVertex) {
        this.secondVertex = secondVertex;
    }

    public boolean contains(V vertex) {
        return this.firstVertex.equals(vertex) || this.secondVertex.equals(vertex);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Edge) {
            final Edge edgeObj = (Edge) obj;
            return edgeObj.getFirstVertex().equals(this.getFirstVertex()) && edgeObj.getSecondVertex().equals(this.getSecondVertex());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getFirstVertex().hashCode() * this.getSecondVertex().hashCode();
    }
}
