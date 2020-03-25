package hungarian;


import java.util.ArrayList;

public class HungarianAlgorithm {

    private int size;
    private boolean[][] primes;
    private boolean[][] mark;
    private boolean[] coveredRow;
    private boolean[] coveredColumn;
    private double[][] matrix;

    public HungarianAlgorithm(double[][] matrix) {
        this.matrix = matrix;
        size = this.matrix.length;
        primes = new boolean[size][size];
        mark = new boolean[size][size];
        coveredRow = new boolean[size];
        coveredColumn = new boolean[size];
        initialization();
    }

    private void initialization() {
        initializeBooleanArrByFalse(coveredRow);
        initializeBooleanArrByFalse(coveredColumn);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                primes[i][j] = false;
                mark[i][j] = false;
            }
        }
    }

    private void initializeBooleanArrByFalse(boolean[] arr) {
        for (int i = 0; i < size; i++) {
            arr[i] = false;
        }
    }

    private void subtractMinFromRow() {
        for (int i = 0; i < size; i++) {
            double rowMin = findMinElementInRow(i);
            for (int j = 0; j < size; j++) {
                matrix[i][j] = matrix[i][j] - rowMin;
            }
        }
    }

    private double findMinElementInRow(int i) {
        double rowMin = Double.MAX_VALUE;
        for (int j = 0; j < size; j++) {
            if (matrix[i][j] < rowMin) {
                rowMin = matrix[i][j];
            }
        }
        return rowMin;
    }


    private void subtractMinFromColumn() {
        for (int j = 0; j < size; j++) {
            double colMin = findMinElementInColumn(j);
            for (int i = 0; i < size; i++) {
                matrix[i][j] = matrix[i][j] - colMin;
            }
        }
    }

    private double findMinElementInColumn(int j) {
        double colMin = Double.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (matrix[i][j] < colMin) {
                colMin = matrix[i][j];
            }
        }
        return colMin;
    }

    public void setMarks() {
        boolean[] rowMark = new boolean[size];
        boolean[] columnMark = new boolean[size];
        initializeBooleanArrByFalse(rowMark);
        initializeBooleanArrByFalse(columnMark);
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (matrix[i][j] == 0 && !rowMark[i] && !columnMark[j]) {
                    mark[i][j] = true;
                    rowMark[i] = true;
                    columnMark[j] = true;
                    break;
                }
            }
        }
    }

    private void setCoveredColumns() {
        for (int j = 0; j < size; j++) {
            coveredColumn[j] = false;
            for (int i = 0; i < size; i++) {
                if (mark[i][j]) {
                    coveredColumn[j] = true;
                    break;
                }
            }
        }
    }

    private boolean isAllColumnsCovered() {
        for (int j = 0; j < size; j++) {
            if (!coveredColumn[j]) {
                return false;
            }
        }
        return true;
    }

    public void resetPrimes() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                primes[i][j] = false;
            }
        }
    }

    private void findMinUncoveredValueInMatrix() {
        double minUncoveredValue = Double.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (coveredRow[i]) {
                continue;
            }
            for (int j = 0; j < size; j++) {
                if (!coveredColumn[j] && matrix[i][j] < minUncoveredValue) {
                    minUncoveredValue = matrix[i][j];
                }
            }
        }

        if (minUncoveredValue > 0) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (coveredRow[i] && coveredColumn[j]) {
                        matrix[i][j] += minUncoveredValue;
                    } else if (!coveredRow[i] && !coveredColumn[j]) {
                        matrix[i][j] -= minUncoveredValue;
                    }
                }
            }
        }
    }

    private int[] findMainZero() {
        for (int i = 0; i < size; i++) {
            if (!coveredRow[i]) {
                for (int j = 0; j < size; j++) {
                    if (!coveredColumn[j] && matrix[i][j] == 0) {
                        primes[i][j] = true;
                        return new int[]{i, j};
                    }
                }
            }
        }
        return new int[2];
    }

    private void augmentPathStartingAtPrime(int[] location) {
        ArrayList<int[]> primeLocations = new ArrayList<>(2 * size);
        ArrayList<int[]> markLocations = new ArrayList<>(2 * size);
        primeLocations.add(location);

        int currentRow;
        int currentCol = location[1];
        while (true) {
            int starRow = findMarkRowInCol(currentCol);
            if (starRow == -1) {
                break;
            }
            int[] markLocation = new int[]{
                    starRow, currentCol
            };
            markLocations.add(markLocation);
            currentRow = starRow;

            int primeCol = findPrimeColInRow(currentRow);
            int[] primeLocation = new int[]{
                    currentRow, primeCol
            };
            primeLocations.add(primeLocation);
            currentCol = primeCol;
        }

        unMarkLocations(markLocations);
        markLocations(primeLocations);
    }

    private void markLocations(ArrayList<int[]> locations) {
        for (int[] location : locations) {
            int row = location[0];
            int col = location[1];
            mark[row][col] = true;
        }
    }

    private void unMarkLocations(ArrayList<int[]> starLocations) {
        for (int[] starLocation : starLocations) {
            int row = starLocation[0];
            int col = starLocation[1];
            mark[row][col] = false;
        }
    }


    private int findPrimeColInRow(int theRow) {
        for (int j = 0; j < size; j++) {
            if (primes[theRow][j]) {
                return j;
            }
        }
        return -1;
    }


    public int findMarkRowInCol(int theCol) {
        for (int i = 0; i < size; i++) {
            if (mark[i][theCol]) {
                return i;
            }
        }
        return -1;
    }


    public int findMarkColInRow(int theRow) {
        for (int j = 0; j < size; j++) {
            if (mark[theRow][j]) {
                return j;
            }
        }
        return -1;
    }

    public int[][] execute() {
        subtractMinFromRow();
        subtractMinFromColumn();
        setMarks();
        setCoveredColumns();
        while (!isAllColumnsCovered()) {
            int[] mainZero = findMainZero();
            while (mainZero[1] == 0) {
                findMinUncoveredValueInMatrix();
                mainZero = findMainZero();
            }
            int starCol = findMarkColInRow(mainZero[0]);
            if (starCol != -1) {
                coveredRow[mainZero[0]] = true;
                coveredColumn[starCol] = false;
            } else {
                augmentPathStartingAtPrime(mainZero);
                initializeBooleanArrByFalse(coveredRow);
                initializeBooleanArrByFalse(coveredColumn);
                resetPrimes();
                setCoveredColumns();
            }
        }
        int[][] toReturn = new int[size][];
        for (int j = 0; j < size; j++) {
            toReturn[j] = new int[]{findMarkRowInCol(j), j};
        }
        return toReturn;
    }

    public double countMinC(double[][] initMatrix) {
        int[][] index = execute();
        double toReturn = 0;
        for (int i = 0; i < initMatrix.length; i++) {
            toReturn += initMatrix[index[i][0]][index[i][1]];
        }
        return toReturn;
    }


}