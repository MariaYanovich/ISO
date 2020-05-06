const SIZE = 6;
let edges = new vis.DataSet([]);
let nodes = new vis.DataSet([]);
let contents = new vis.DataSet([]);

for (let n = 1; n <= SIZE; n++) {
    nodes.add({
        id: n,
        label: ' ' + n + ' ',
        color: {background: "#85DDDA", border: "#283e6a"},
    });
}

function createGraph() {
    let html = "<table class='table table-info' style='text-align: center'>";
    let matrix = [];
    for (let i = 0; i < SIZE; i++) {
        let borderMatrix = [];
        for (let j = 0; j < SIZE; j++) {
            borderMatrix.push(0);
        }
        matrix.push(borderMatrix);
        if (i !== 0) {
            html += "<th>" + (i) + "</th>";
        } else {
            html += "<th>" + " " + "</th>";
        }
    }
    html += "<th>" + (matrix.length) + "</th>";
    // matrix = [[0, 16, 13, 0, 0, 0],
    //     [0, 0, 10, 12, 0, 0],
    //     [0, 4, 0, 0, 14, 0],
    //     [0, 0, 9, 0, 0, 20],
    //     [0, 0, 0, 7, 0, 4],
    //     [0, 0, 0, 0, 0, 0]];
    // matrix = [[0, 39, 10, 23, 0, 0, 0, 0],
    //     [0, 0, 0, 0, 25, 0, 0, 0],
    //     [0, 81, 0, 0, 0, 61, 15, 0],
    //     [0, 0, 20, 0, 0, 0, 0, 0],
    //     [0, 0, 18, 0, 0, 0, 0, 44],
    //     [0, 0, 0, 0, 16, 0, 0, 53],
    //     [0, 0, 0, 33, 0, 71, 0, 95],
    //     [0, 0, 0, 0, 0, 0, 0, 0]];
    matrix = [[0, 10, 8, 0, 0, 0],
        [0, 0, 5, 5, 0, 0],
        [0, 4, 0, 0, 10, 0],
        [0, 0, 9, 0, 10, 3],
        [0, 0, 0, 6, 0, 14],
        [0, 0, 0, 0, 0, 0]];
    for (let i = 0; i < SIZE; i++) {
        html += "<tr>";
        html += "<td style='font-weight: bold'>" + (i + 1) + "</td>";
        for (let j = 0; j < SIZE; j++) {
            if (matrix[i][j] !== 0) {
                edges.add({
                    from: i + 1,
                    to: j + 1,
                    label: " " + matrix[i][j] + " ",
                    arrows: "to"
                });
            }
            html += "<td>" + matrix[i][j] + "</td>";
        }
        html += "</tr>";
    }
    html += "</table>";
    document.getElementById("matrix").innerHTML = html;
    document.getElementById("displayMaxFlow").innerHTML = "Max flow of the graph : " + algorithm(matrix, 0, SIZE - 1);
}

function showGraph() {
    let options = {};
    let container = document.querySelector('.network');
    let data = {
        nodes: nodes,
        edges: edges,
        content: contents
    };
    let network = new vis.Network(container, data, options);
}

function bfs(rGraph, s, t, parent) {
    let visited = [];
    let queue = [];
    let V = rGraph.length;
    for (let i = 0; i < V; i++) {
        visited[i] = false;
    }
    queue.push(s);
    visited[s] = true;
    parent[s] = -1;
    while (queue.length !== 0) {
        let u = queue.shift();
        for (let v = 0; v < V; v++) {
            if (visited[v] === false && rGraph[u][v] > 0) {
                queue.push(v);
                parent[v] = u;
                visited[v] = true;
            }
        }
    }
    return (visited[t] === true);
}

function algorithm(graph, s, t) {
    if (s < 0 || t < 0 || s > graph.length - 1 || t > graph.length - 1) {
        throw new Error("Invalid sink or source.");
    }
    if (graph.length === 0) {
        throw new Error("Invalid graph.");
    }
    let rGraph = [];
    for (let u = 0; u < graph.length; u++) {
        let temp = [];
        if (graph[u].length !== graph.length) {
            throw new Error("Graph should be nxn");
        }
        for (v = 0; v < graph.length; v++) {
            temp.push(graph[u][v]);
        }
        rGraph.push(temp);
    }
    let parent = [];
    let maxFlow = 0;
    let i = 0;
    while (bfs(rGraph, s, t, parent)) {
        let arr = [];
        let flowPath = Number.MAX_VALUE;
        for (let v = t; v !== s; v = parent[v]) {
            u = parent[v];
            flowPath = Math.min(flowPath, rGraph[u][v]);
            arr[i] = [(v + 1), (u + 1)];
            i++;
        }
        for (v = t; v !== s; v = parent[v]) {
            u = parent[v];
            rGraph[u][v] -= flowPath;
            rGraph[v][u] += flowPath;
        }
        console.log("PATH FLOW    |" + arr + "|   " + flowPath);
        maxFlow += flowPath;
    }
    return maxFlow;
}