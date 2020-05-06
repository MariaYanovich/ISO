package hungarian;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[][] matrix = {
                {1,2,1,0,5},
                {2,3,1,0,4},
                {1,1,3,0,3},
                {0,0,0,2,0},
                {2,1,3,0,4}};
        double[][] copy ={
                {1,2,1,0,5},
                {2,3,1,0,4},
                {1,1,3,0,3},
                {0,0,0,2,0},
                {2,1,3,0,4}};
        HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm(matrix);
        System.out.println(Arrays.deepToString(hungarianAlgorithm.execute()));
        System.out.println(hungarianAlgorithm.countMinC(copy));
    }
}
