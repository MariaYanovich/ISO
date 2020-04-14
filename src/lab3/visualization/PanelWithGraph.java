package lab3.visualization;

import lab3.algorithm.Edge;
import lab3.algorithm.Graph;
import lab3.algorithm.KruscalAlgorithm;

import javax.swing.*;
import java.awt.*;

public class PanelWithGraph extends JPanel {
    private final KruscalAlgorithm kruscal;
    private final Graph graph;
    private int boxSize = 50;

    public PanelWithGraph(Graph graph, KruscalAlgorithm kruscalAlgorithm) {
        this.graph = graph;
        this.kruscal = kruscalAlgorithm;
        setPreferredSize(new Dimension(700, 600));
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (kruscal.getState() != KruscalAlgorithm.State.END) {
            drawTable(g);
        } else {
            g.setColor(Color.BLACK);
            Font font = new Font("Default", Font.PLAIN, 24);
            g.setFont(font);
            drawStringInCenter(g, "Sum of all weights: " + kruscal.getSumOfWeights(), getWidth() / 2, 100, 24);
        }
        for (int e = 0; e < graph.getEdgesNumber(); e++) {
            Edge edge = graph.getEdgeList().get(e);
            Point p1 = getVertexPoint(edge.getV1());
            Point p2 = getVertexPoint(edge.getV2());
            ((Graphics2D) g).setStroke(new BasicStroke(3));
            Color color = Color.GRAY;
            if (kruscal.getOst().hasEdge(edge.getV1(), edge.getV2())) {
                color = new Color(10, 155, 10);
            } else if (kruscal.getState() == KruscalAlgorithm.State.END) {
                continue;
            }
            if (e == kruscal.getEdgeIndex()) {
                if (kruscal.getState() == KruscalAlgorithm.State.CHECK_EDGE) {
                    color = Color.BLUE;
                } else if (kruscal.getState() == KruscalAlgorithm.State.COLLISION) {
                    color = Color.RED;
                } else if (kruscal.getState() == KruscalAlgorithm.State.ADD_EDGE) {
                    color = Color.GREEN;
                }
            }
            g.setColor(color);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
            int x = (p1.x * 3 + p2.x * 2) / 5;
            int y = (p1.y * 3 + p2.y * 2) / 5;
            g.setColor(Color.WHITE);
            g.fillOval(x - 15, y - 15, 30, 30);
            ((Graphics2D) g).setStroke(new BasicStroke(1));
            g.setColor(color);
            g.drawOval(x - 15, y - 15, 30, 30);
            drawStringInCenter(g, Integer.toString(edge.getWeight()), x, y, 10);
        }
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        for (int v = 1; v <= graph.getVertexNumber(); v++) {
            Point p = getVertexPoint(v - 1);
            g.setColor(new Color(200, 200, 200));
            g.fillOval(p.x - boxSize / 2, p.y - boxSize / 2, boxSize, boxSize);
            g.setColor(Color.BLACK);
            g.drawOval(p.x - boxSize / 2, p.y - boxSize / 2, boxSize, boxSize);
            drawStringInCenter(g, Integer.toString(v), p.x, p.y, 16);
        }
    }

    private void drawTable(Graphics g) {
        g.setColor(Color.BLACK);
        drawStringInBox(g, "v1", 10, 10, 70, 50);
        drawStringInBox(g, "v2", 10, 60, 70, 50);
        drawStringInBox(g, "weight", 10, 110, 70, 50);
        for (int i = 0; i < graph.getEdgeList().size(); i++) {
            if (i == kruscal.getEdgeIndex() && kruscal.getState() != KruscalAlgorithm.State.INITIAL) {
                g.setColor(Color.YELLOW);
                g.fillRect(80 + i * boxSize, 10, boxSize, boxSize * 3);
            }
            g.setColor(Color.BLACK);
            drawValueInBox(g, 80 + i * boxSize, 10, graph.getEdgeList().get(i).getV1() + 1);
            drawValueInBox(g, 80 + i * boxSize, 10 + boxSize, graph.getEdgeList().get(i).getV2() + 1);
            drawValueInBox(g, 80 + i * boxSize, 10 + 2 * boxSize, graph.getEdgeList().get(i).getWeight());
        }
    }

    private Point getVertexPoint(int v) {
        int x = (int) (300 + Math.cos(v * 2 * Math.PI / graph.getVertexNumber()) * 200) - boxSize / 2;
        int y = (int) (400 - Math.sin(v * 2 * Math.PI / graph.getVertexNumber()) * 200);
        return new Point(x, y);
    }

    private void drawValueInBox(Graphics g, int x, int y, int value) {
        String text = Integer.toString(value);
        drawStringInBox(g, text, x, y, boxSize, boxSize);
    }

    private void drawStringInBox(Graphics g, String text, int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
        drawStringInCenter(g, text, x + width / 2, y + height / 2, 16);
    }

    private void drawStringInCenter(Graphics g, String text, int x, int y, int size) {
        Font font = new Font("Default", Font.PLAIN, size);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        g.drawString(text,
                x - metrics.stringWidth(text) / 2,
                y - metrics.getHeight() / 2 + metrics.getAscent());
    }
}
