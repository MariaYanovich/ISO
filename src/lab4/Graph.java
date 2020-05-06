package lab4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
    List<Edge> list = new ArrayList<>();
    List<Integer> ways = new ArrayList<>();
    List<Integer> road = new ArrayList<>();
    List<Way> visual = new ArrayList<>();
    int n;
    int m = 0;
    int startVertex;
    int vertex = -1;
    private int negativeCircle = 0;

    public void readFromFile() {
        negativeCircle = 0;
        list.clear();
        visual.clear();
        try (Scanner sc = new Scanner(new File("in.txt"))) {
            if (sc.hasNextInt()) {
                n = sc.nextInt();
            } else System.out.println("В файле недостаточно данных.");
            if (sc.hasNextInt()) {
                m = sc.nextInt();
            } else System.out.println("В файле недостаточно данных.");
            addWay();
            for (int j = 0; j < m; j++) {
                Edge edge = new Edge();
                if (sc.hasNextInt()) {
                    edge.v1 = sc.nextInt();
                    edge.v1--;
                }
                if (sc.hasNextInt()) {
                    edge.v2 = sc.nextInt();
                    edge.v2--;
                }
                if (sc.hasNextInt()) {
                    edge.weight = sc.nextInt();
                }
                list.add(edge);
            }
        } catch (Exception ex) {
            System.out.println("Пустой файл.");
        }
    }

    public void searchAlgorithm(AlgorithmWindow graph) {
        for (int i = 0; i < n; i++) {
            ways.add(Integer.MAX_VALUE);
            road.add(-1);
        }
        startVertex--;
        ways.set(startVertex, 0);
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j < m; ++j) {
                if ((ways.get(list.get(j).v1) < Integer.MAX_VALUE) && ((ways.get(list.get(j).v1) + list.get(j).weight) < ways.get(list.get(j).v2))) {
                    if (i == n) {
                        negativeCircle = 1;
                        graph.result.clear();
                        graph.result.appendText("Есть отрицательные циклы.");
                        return;
                    } else {
                        ways.set(list.get(j).v2, (ways.get(list.get(j).v1) + list.get(j).weight));
                        road.set(list.get(j).v2, list.get(j).v1);
                    }
                }
            }

        }
    }

    public void outputWays(AlgorithmWindow graph) {
        if (negativeCircle == 0) {
            graph.result.clear();
            List<Integer> path = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (j != (startVertex)) {
                    if (ways.get(j) == Integer.MAX_VALUE) {
                        graph.result.appendText("Из " + (startVertex + 1) + " в " + (j + 1) + ": NO\n");
                    } else {
                        path.clear();
                        for (int cur = j; cur != -1; cur = road.get(cur))
                            path.add(cur);
                        graph.result.appendText("Из " + (startVertex + 1) + " в " + (j + 1) + ": вес = " + ways.get(j) + "\nкратчайший путь ");
                        for (int i = path.size() - 1; i >= 1; i--) {
                            int l = (path.get(i) + 1);
                            graph.result.appendText(l + "->");
                        }
                        graph.result.appendText((path.get(0) + 1) + ";\n");
                        path.clear();
                    }
                }
            }
        }
    }

    public void addWay() {
        double fi = (double) 360 / n;
        for (int i = 0; i < n; i++) {
            Way way = new Way();
            int x = 130;
            int vertexX = 120;
            way.x = x + (int) (long) (vertexX * Math.cos(i * fi * Math.PI / 180));
            int y = 105;
            int vertexY = 105;
            way.y = y + (int) (long) (vertexY * Math.sin(i * fi * Math.PI / 180));
            way.name = i + 1;
            visual.add(way);
        }
    }

    public static class Edge {
        int v1;
        int v2;
        int weight;
    }

    public static class Way {
        int x;
        int y;
        int name;
    }

}
