package lab4;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class VisualizationWindow extends AlgorithmWindow {
    @FXML
    public static Button buildBut;
    @FXML
    private Pane panel;

    @FXML
    public void graphButton() {
        for (int i = 0; i < graph.n; i++) {
            Ellipse ellipse = new Ellipse(graph.visual.get(i).x, graph.visual.get(i).y, 7, 10);
            ellipse.setFill(Color.YELLOW);
            panel.getChildren().add(ellipse);
            Label label = new Label(Integer.toString(graph.visual.get(i).name));
            label.setTextFill(Color.GREEN);
            label.setLayoutX((double) graph.visual.get(i).x - 3);
            label.setLayoutY((double) graph.visual.get(i).y - 9);
            panel.getChildren().add(label);
        }
        for (int i = 0; i < graph.m; i++) {
            Line q = new Line(graph.visual.get(graph.list.get(i).v1).x, (double) graph.visual.get(graph.list.get(i).v1).y - 9, graph.visual.get(graph.list.get(i).v2).x, (double) graph.visual.get(graph.list.get(i).v2).y - 9);
            q.setStrokeWidth(0.5);
            q.setFill(Color.LIGHTGREY);
            panel.getChildren().add(q);
            this.arrow(graph.visual.get(graph.list.get(i).v1).x, graph.visual.get(graph.list.get(i).v2).x, graph.visual.get(graph.list.get(i).v1).y, graph.visual.get(graph.list.get(i).v2).y, Color.BLACK);
        }
        if (graph.vertex != -1) {
            if (graph.ways.get(graph.vertex) == Integer.MAX_VALUE) {
                Label label = new Label("Из " + (graph.startVertex + 1) + " в " + (graph.vertex + 1) + ": NO\n");
                label.setTextFill(Color.RED);
                label.setLayoutX(0);
                label.setLayoutY(0);
                panel.getChildren().add(label);
            } else {
                List<Integer> path = new ArrayList<>();
                for (int cur = graph.vertex; cur != -1; cur = graph.road.get(cur))
                    path.add(cur);
                for (int i = path.size() - 1; i >= 1; i--) {
                    int l = (path.get(i));
                    int k = (path.get(i - 1));
                    Label label1 = new Label(Integer.toString(graph.ways.get(graph.vertex)));
                    label1.setTextFill(Color.RED);
                    label1.setLayoutX((double) (graph.visual.get(graph.vertex)).x + 10);
                    label1.setLayoutY((graph.visual.get(graph.vertex).y - 12));
                    panel.getChildren().add(label1);
                    Line q = new Line(graph.visual.get(l).x, (double) graph.visual.get(l).y - 9, graph.visual.get(k).x, (double) graph.visual.get(k).y - 9);
                    q.setStroke(Color.RED);
                    q.setStrokeWidth(1);
                    panel.getChildren().add(q);
                    this.arrow(graph.visual.get(l).x, graph.visual.get(k).x, graph.visual.get(l).y, graph.visual.get(k).y, Color.RED);
                }
                path.clear();
            }
        }

    }

    void arrow(int x, int x1, int y, int y1, Color color) {
        double beta = Math.atan2((double) y - (y1 - 9), (double) x1 - x);
        double alfa = Math.PI / 10;
        int r1 = 10;
        int x2 = (int) Math.round(x1 - r1 * Math.cos(beta + alfa));
        int y2 = (int) Math.round((y1 - 9) + r1 * Math.sin(beta + alfa));
        int x3 = (int) Math.round(x1 - r1 * Math.cos(beta - alfa));
        int y3 = (int) Math.round((y1 - 9) + r1 * Math.sin(beta - alfa));
        Line q1 = new Line(x1, (double) y1 - 9, x2, y2);
        Line q2 = new Line(x1, (double) y1 - 9, x3, y3);
        q1.setStroke(color);
        panel.getChildren().add(q1);
        q2.setStroke(color);
        panel.getChildren().add(q2);
    }
}
