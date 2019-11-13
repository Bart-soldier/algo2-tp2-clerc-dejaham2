package main;

import java.util.ArrayList;

public class Main {

    // This function corrects every word in a file, given that there is one word per line in a file
    public static void correctFile(Dictionary dictionary, String fileName) {
        Reader reader = new Reader(fileName);
        String word;
        int index = 1; // To keep track of file line

        // Goes through every line of the file
        while(reader.hasNext()) {
            word = reader.readNextWord();

            // If the word is the in the dictionary, print it
            if(dictionary.isWord(word)) {
                System.out.println(index + ". " + word);
            }
            // Otherwise, print the closest word to it in terms of Levenshtein distance
            else {
                ArrayList<WordWithValue> words = dictionary.similarWords(word);
                System.out.println(index + ". " + Levenshtein.closestWord(words, word));
            }
            index++;
        }

        reader.closeReader();
    }

    public static void test(Dictionary dictionary, String correctWord, String wrongWord) {
        // Test 1 : word in dictionary
        System.out.println(correctWord + " is in the dictionary ? " + dictionary.isWord(correctWord) + "\n");

        // Test 2 : word not in dictionary
        System.out.println(wrongWord + " is in the dictionary ? " + dictionary.isWord(wrongWord) + "\n");

        // Test 3 : 5 closest words of Alexndrie

        ArrayList<WordWithValue> words = dictionary.similarWords(wrongWord);
        String[] closest5Words = Levenshtein.closest5Words(words, wrongWord);
        int index = 1;
        System.out.println("Closest 5 words in terms of Levenshtein distance : ");
        for(String word : closest5Words) {
            System.out.println(index + ". " + word);
            index++;
        }
        System.out.println();

        // Test 4 : closest word of Alexndrie
        System.out.println("Closest word in terms of Levenshtein distance : ");
        System.out.println(Levenshtein.closestWord(words, wrongWord) + "\n");
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Dictionary dictionary = new Dictionary();

        //test(dictionary, "Alexandrie", "Alexndrie");
        test(dictionary, "abréviation", "abbréviation");

        //correctFile(dictionary, "fautes.txt");

        long finishTime = System.nanoTime();
        System.out.println("Total time in nanoseconds : " + (finishTime - startTime));
    }
}
