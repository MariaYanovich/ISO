package lab4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AlgorithmWindow {
    protected static int k;
    protected static Graph graph = new Graph();
    @FXML
    public TextArea result;
    @FXML
    private TextField textField1;
    @FXML
    private TextArea textGraph;
    @FXML
    private Label text1;
    @FXML
    private Label text2;
    @FXML
    private Label text3;
    @FXML
    private Button next;
    @FXML
    private Button fileGraph;
    @FXML
    private Button begin;
    @FXML
    private Button back;
    @FXML
    private Button graphWay;
    @FXML
    private Label vertex;
    @FXML
    private TextArea graphV;

    public void error(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Некорректный ввод.");
        alert.setHeaderText(s);
        alert.showAndWait();
    }

    public void beginAlgorithm() {
        k = 1;
        graph.searchAlgorithm(this);
        graph.outputWays(this);
    }

    @FXML
    public void workBegin() {
        if (graph.n == 0) {
            error("Граф не сгенерирован");
        } else {
            if (textField1.getText() == null || textField1.getText().length() == 0) {
                error("Введите вершину.");
            } else {
                try {
                    graph.startVertex = Integer.parseInt(textField1.getText());
                    if (graph.startVertex > graph.n || graph.startVertex <= 0)
                        error("Вершина задана неверно.");
                    else beginAlgorithm();
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    error("Введено некорректное значение начальной вершины. Вводите только цифры.");
                }
            }
        }
    }

    @FXML
    public void readGraph() {
        k = 0;
        graph.list.clear();
        graph.ways.clear();
        graph.road.clear();
        graph.vertex = -1;
        textGraph.clear();
        graph.readFromFile();
        for (int i = 0; i < graph.list.size(); i++) {
            textGraph.appendText("(" + (graph.list.get(i).v1 + 1) + "," + (graph.list.get(i).v2 + 1) + "): вес = " + graph.list.get(i).weight + ";\n");
        }
    }

    public void FXMLDocumentController(Stage stageWindow) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("GUI_prototype.fxml"));
        stageWindow.setTitle("Граф");
        Scene scene = new Scene(root);
        stageWindow.setScene(scene);
        stageWindow.show();
    }

    @FXML
    public void nextMenu() {
        if (k == 0)
            error("Алгоритм не начал свою работу.");
        else {
            next.setVisible(false);
            text3.setVisible(false);
            textField1.setVisible(false);
            begin.setVisible(false);
            fileGraph.setVisible(false);
            back.setVisible(true);
            graphWay.setVisible(true);
            vertex.setVisible(true);
            graphV.setVisible(true);
        }
    }

    @FXML
    public void backMenu() {
        next.setVisible(true);
        text3.setVisible(true);
        textField1.setVisible(true);
        begin.setVisible(true);
        fileGraph.setVisible(true);
        back.setVisible(false);
        graphWay.setVisible(false);
        vertex.setVisible(false);
        graphV.setVisible(false);
    }

    @FXML
    public void getWay() {
        if (graphV.getText() == null || graphV.getText().length() == 0) {
            error("Введите количество вершин.");
        } else {

            try {
                int x = Integer.parseInt(graphV.getText());
                x--;
                graph.vertex = x;
                if (x + 1 > graph.n || x + 1 < 0 || x == (graph.startVertex)) {
                    error("Граф может не отобразится.");
                } else {
                    Stage stageWindow = new Stage();
                    try {
                        FXMLDocumentController(stageWindow);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                error("Введено некорректное значение конечной вершины! Пожалуста, вводите только цифры.");
            }
        }
    }
}

