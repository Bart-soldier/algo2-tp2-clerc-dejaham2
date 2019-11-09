package main;

import java.util.ArrayList;

public class Levenshtein {

    // This function creates the dynamic programming matrix used to calculate the Levenshtein distance between two words
    public static int distance(String word1, String word2){
        int[][] distanceMatrix = new int[word2.length()+1][word1.length()+1];
        distanceMatrix = initializeMatrix(distanceMatrix);

        for(int row = 1; row < distanceMatrix.length; row++) {
            for(int col = 1; col < distanceMatrix[0].length; col++) {
                if(word1.charAt(col-1) == word2.charAt(row-1)) distanceMatrix[row][col] = distanceMatrix[row-1][col-1];
                else distanceMatrix[row][col] = 1 + Math.min(distanceMatrix[row-1][col-1], Math.min(distanceMatrix[row-1][col], distanceMatrix[row][col-1]));
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

    // This function returns the 5 words that has the lowest Levenshtein distance with a given word from a list of words
    public static String[] closest5Words(ArrayList<WordWithValue> mostSimilarWords, String word) {
        ArrayList<WordWithValue> closest5Words = new ArrayList<>();

        for(WordWithValue similarWord : mostSimilarWords) {
            int levenshteinDistance = distance(similarWord.word, word);
            if(closest5Words.size() < 5) {
                closest5Words.add(new WordWithValue(similarWord.word, levenshteinDistance));
            }
            else {
                for (WordWithValue closestWord : closest5Words) {
                    if ((levenshteinDistance >= closestWord.value)) {
                        continue;
                    }
                    closest5Words.set(closest5Words.indexOf(closestWord), new WordWithValue(similarWord.word, levenshteinDistance));
                    break;
                }
            }
        }

        String[] stringList = new String[5];
        for(int i = 0; i < 5; i++) {
            stringList[i] = closest5Words.get(i).word.substring(1, closest5Words.get(i).word.length() - 1);
        }

        return stringList;
    }

    // This function returns the word that has the lowest Levenshtein distance with a given word from a list of words
    public static String closestWord(ArrayList<WordWithValue> mostSimilarWords, String word) {
        WordWithValue closestWord = new WordWithValue(null, 1000);

        for(WordWithValue similarWord : mostSimilarWords) {
            int levenshteinDistance = distance(similarWord.word, word);
            if ((levenshteinDistance < closestWord.value)) {
                closestWord = new WordWithValue(similarWord.word, levenshteinDistance);
            }
        }

        closestWord.word = closestWord.word.substring(1, closestWord.word.length() - 1);

        return closestWord.word;
    }
}
