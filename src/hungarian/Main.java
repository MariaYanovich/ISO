package hungarian;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[][] matrix = {
                {12, 6, 9, 0},
                {8, 7, 3, 0},
                {5, 4, 6, 0},
                {9, 10, 8, 0}};
        double[][] copy = {
                {12, 6, 9, 0},
                {8, 7, 3, 0},
                {5, 4, 6, 0},
                {9, 10, 8, 0}};
        HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm(matrix);
        System.out.println(Arrays.deepToString(hungarianAlgorithm.execute()));
        System.out.println(hungarianAlgorithm.countMinC(copy));
    }
}
