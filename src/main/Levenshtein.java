package main;

import java.util.ArrayList;

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

    public static String[] best5Levenshtein (ArrayList<WordOccurrences> best100, String word) {
        ArrayList<WordDistance> best5 = new ArrayList<>();
        String[] stringList = new String[5];

        for(WordOccurrences best100Word : best100) {
            int levenshteinDistance = distance(best100Word.word, word);
            if(best5.size() < 5) {
                best5.add(new WordDistance(best100Word.word, levenshteinDistance));
            }
            else {
                for (WordDistance best5Word : best5) {
                    if ((levenshteinDistance >= best5Word.distance)) {
                        continue;
                    }
                    best5.set(best5.indexOf(best5Word), new WordDistance(best100Word.word, levenshteinDistance));
                    break;
                }
            }
        }

        for(int i = 0; i < 5; i++) {
            stringList[i] = best5.get(i).word;
        }

        return stringList;
    }

    private static class WordDistance {

        public final String word;
        public final int distance;

        public WordDistance(String word, int distance) {
            this.word = word;
            this.distance = distance;
        }
    }
}
