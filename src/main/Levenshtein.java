package main;

public class Levenshtein {
    public static int distance(String word1, String word2){

        int[][] distanceMatrix = new int[word2.length() +1][word1.length()+1];
        distanceMatrix = initializeMatrix(distanceMatrix);

        for(int row = 1; row < distanceMatrix.length; row++) {
            for(int col = 1; col < distanceMatrix[0].length; col++) {
                if(word1.charAt(col -1) == word2.charAt(row -1)) distanceMatrix[row][col] = distanceMatrix[row - 1][col - 1];
                else distanceMatrix[row][col] = 1 + Math.min(distanceMatrix[row - 1][col - 1], Math.min(distanceMatrix[row-1][col], distanceMatrix[row][col-1]));
            }
        }

        return distanceMatrix[word2.length()][word1.length()];
    }

    private static int[][] initializeMatrix(int[][] matrix){
        matrix[0][0] = 0;
        for(int i = 1; i < matrix[0].length; i++) matrix[0][i] = i;
        for(int i = 1; i < matrix.length; i++) matrix[i][0] = i;

        return matrix;
    }
}
