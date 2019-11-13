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

    public static void test(Dictionary dictionary, String word) {
        // Test 1 : word in dictionary ?
        System.out.println(word + " is in the dictionary ? " + dictionary.isWord(word) + "\n");

        // Test 2 : 5 closest words

        ArrayList<WordWithValue> words = dictionary.similarWords(word);
        String[] closest5Words = Levenshtein.closest5Words(words, word);
        int index = 1;
        System.out.println("Closest 5 words in terms of Levenshtein distance : ");
        for(String closestWord : closest5Words) {
            System.out.println(index + ". " + closestWord);
            index++;
        }
        System.out.println();

        // Test 3 : closest word
        System.out.println("Closest word in terms of Levenshtein distance : ");
        System.out.println(Levenshtein.closestWord(words, word) + "\n");
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Dictionary dictionary = new Dictionary();

        test(dictionary, "Alexndrie");
        //test(dictionary, "abbréviation");

        //correctFile(dictionary, "fautes.txt");

        long finishTime = System.nanoTime();
        System.out.println("Total time in nanoseconds : " + (finishTime - startTime));
    }
}
