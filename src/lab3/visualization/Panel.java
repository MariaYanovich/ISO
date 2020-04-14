package lab3.visualization;

import lab3.algorithm.Edge;
import lab3.algorithm.Graph;
import lab3.algorithm.KruscalAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Panel extends JPanel {

    private KruscalAlgorithm kruscalAlgorithm;
    private String filePath;

    public Panel(String filePath) {
        super(new BorderLayout());
        this.filePath = filePath;
        add(createGraphPanel(), BorderLayout.CENTER);
    }

    private JPanel createGraphPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        JPanel next = new JPanel(new FlowLayout());
        add(next, BorderLayout.SOUTH);
        next.add(new JButton(new AbstractAction("Start") {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        }));
        return panel;
    }

    private void start() {
        try (Scanner input = new Scanner(new File(filePath))) {
            int n = input.nextInt();
            int m = input.nextInt();
            Graph graph = new Graph(n, m);
            for (int i = 0; i < m; i++) {
                int v1 = input.nextInt() - 1;
                int v2 = input.nextInt() - 1;
                int weight = input.nextInt();
                graph.addEdge(new Edge(v1, v2, weight));
            }
            removeAll();
            JButton nextStepButton = new JButton(new AbstractAction("Next step") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    kruscalAlgorithm.doStep();
                    repaint();
                }
            });
            kruscalAlgorithm = new KruscalAlgorithm(graph, nextStepButton);
            add(new PanelWithGraph(graph, kruscalAlgorithm));
            JPanel next = new JPanel(new FlowLayout());
            next.add(nextStepButton);
            add(next, BorderLayout.SOUTH);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.pack();
            repaint();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this,
                    "File not found",
                    "Format error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
