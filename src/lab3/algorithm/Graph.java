package lab3.algorithm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph implements Serializable {
    private int vertexNumber;
    private int edgesNumber;

    private List<Edge> edgeList;

    public Graph(int v, int e) {
        vertexNumber = v;
        edgesNumber = e;
        edgeList = new ArrayList<>(e);
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void setVertexNumber(int vertexNumber) {
        this.vertexNumber = vertexNumber;
    }

    public int getEdgesNumber() {
        return edgesNumber;
    }

    public void setEdgesNumber(int edgesNumber) {
        this.edgesNumber = edgesNumber;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public void addEdge(Edge e) {
        edgeList.add(e);
    }

    public void sorting() {
        Collections.sort(edgeList);
    }

    public boolean hasEdge(int from, int to) {
        for (Edge e : edgeList) {
            if (from == e.getV1() && to == e.getV2()) {
                return true;
            }
            if (from == e.getV2() && to == e.getV1()) {
                return true;
            }
        }
        return false;
    }
}
