package lab3;

import lab3.visualization.Panel;

import javax.swing.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Kruscal Algorithm");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(new Panel("test1.txt"));
        frame.pack();
        frame.setVisible(true);
    }
}
