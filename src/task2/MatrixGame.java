package task2;

public class MatrixGame {

    private static void taskTwo(int[][] matrix) {
        for (int[] ints : matrix) {
            int tmp = 1;
            for (int anInt : ints) {
                if (anInt < 0) {
                    System.out.print(anInt + "y" + (tmp++));
                } else {
                    System.out.print("+" + anInt + "y" + (tmp++));
                }
            }
            System.out.println("<=1");
        }
    }

    private static void taskOne(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            int tmp = 1;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[j][i] < 0) {
                    System.out.print(matrix[j][i] + "x" + (tmp++));
                } else {
                    System.out.print("+" + matrix[j][i] + "x" + (tmp++));
                }
            }
            System.out.println(">=1");
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {6, 4, 1, 3},
                {1, 5, 2, 1},
                {-1, 3, 3, 3},
                {2, 5, -2, -2}
        };

        System.out.println("Первая двойственная задача: ");
        taskOne(matrix);
        System.out.println("Вторая двойственная задача: ");
        taskTwo(matrix);
    }


}
