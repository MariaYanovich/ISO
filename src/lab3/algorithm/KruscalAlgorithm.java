package lab3.algorithm;

import javax.swing.*;
import java.io.Serializable;

import static lab3.algorithm.KruscalAlgorithm.State.INITIAL;


public class KruscalAlgorithm implements Serializable {

    private final JButton button;
    private State state = INITIAL;
    private Graph ost;
    private int edgeIndex = 0;
    private int sumOfWeights = 0;
    private int[] set;
    private int[] rank;
    private Graph graph;

    public KruscalAlgorithm(Graph graph, JButton nextButton) {
        int size = graph.getVertexNumber();
        this.graph = graph;
        ost = new Graph(graph.getVertexNumber(), graph.getEdgesNumber());
        set = new int[size];
        rank = new int[size];
        button = nextButton;
        for (int i = 0; i < size; i++) {
            set[i] = i;
        }
    }

    public JButton getButton() {
        return button;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Graph getOst() {
        return ost;
    }

    public void setOst(Graph ost) {
        this.ost = ost;
    }

    public int getEdgeIndex() {
        return edgeIndex;
    }

    public void setEdgeIndex(int edgeIndex) {
        this.edgeIndex = edgeIndex;
    }

    public int getSumOfWeights() {
        return sumOfWeights;
    }

    public void setSumOfWeights(int sumOfWeights) {
        this.sumOfWeights = sumOfWeights;
    }

    public int[] getSet() {
        return set;
    }

    public void setSet(int[] set) {
        this.set = set;
    }

    public int[] getRank() {
        return rank;
    }

    public void setRank(int[] rank) {
        this.rank = rank;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /* Возвращает множество, которому принадлежит x */
    int set(int x) {
        return x == set[x] ? x : (set[x] = set(set[x]));
    }

    boolean union(int u, int v) {
        if ((u = set(u)) == (v = set(v)))
            return false;
        if (rank[u] < rank[v])
            set[u] = v;
        else {
            set[v] = u;
            if (rank[u] == rank[v])
                rank[u]++;
        }
        return true;
    }

    public void doStep() {
        if (state == INITIAL) {
            graph.sorting();
            state = State.SORTED;
        } else if (state == State.CHECK_EDGE) {
            Edge e = graph.getEdgeList().get(edgeIndex);
            if (union(e.getV1(), e.getV2())) {
                sumOfWeights += e.getWeight();
                state = State.ADD_EDGE;
                ost.getEdgeList().add(e);
            } else {
                state = State.COLLISION;
            }
        } else if (state == State.ADD_EDGE || state == State.COLLISION) {
            edgeIndex++;
            if (edgeIndex < graph.getEdgesNumber()) {
                state = State.CHECK_EDGE;
            } else {
                state = State.END;
                button.setEnabled(false);
            }
        } else if (state == State.SORTED) {
            state = State.CHECK_EDGE;
        }
    }

    public enum State {
        INITIAL,
        SORTED,
        CHECK_EDGE,
        ADD_EDGE,
        COLLISION,
        END
    }
}
