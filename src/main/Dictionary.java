package main;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {

    // The dictionary is a hash map that hashes words to their trigrams, i.e. the keys
    private HashMap<String, ArrayList<String>> dictionary;

    // This function creates the dictionary using a hash map, as detailed above
    public Dictionary(){
        dictionary = new HashMap<>(17000);
        Reader reader = new Reader("dico.txt");
        String word;

        while (reader.hasNext()){
            word = reader.readNextWord();
            word = "<" + word + ">";
            addTrigramsToHash(getTrigrams(word), word);
        }

        reader.closeReader();
    }

    // This function gets the trigrams for a given word
    private ArrayList<String> getTrigrams(String word) {
        ArrayList<String> trigrams = new ArrayList();
        for(int i = 0; i < word.length() - 2; i++){
            trigrams.add((String) word.subSequence(i, i+3));
        }
        return trigrams;
    }

    // This function, for each trigram of a word, after checking if the trigram is already a key, hashes the word to its trigram
    private void addTrigramsToHash(ArrayList<String> trigrams, String word){
        for(String string : trigrams){
            dictionary.computeIfAbsent(string, k -> new ArrayList<String>());
            dictionary.get(string).add(word);
        }
    }

    // This function checks if a word is already in the dictionary
    public boolean isWord(String word) {
        String trigram = "<";
        int index = 0;

        do {
            trigram = trigram.concat((String) word.subSequence(index, index+1));
            index++;
        } while ((index < word.length()) && (index < 2));

        if(word.length() == 1) trigram += ">";

        String wordWithSymbols = "<" + word + ">";

        if(dictionary.containsKey(trigram)) return dictionary.get(trigram).contains(wordWithSymbols);
        else return false;
    }

    // This function creates a list of words, as well as their number of occurrences,
    // that have at least one trigram in common with the word given as a parameter
    private ArrayList wordsWithSameTrigrams(String word){
        ArrayList<String> wordsWithCommonTrigrams = new ArrayList<>();
        ArrayList<WordWithValue> wordOccurrences = new ArrayList<>();
        // Gets the trigrams of a word
        ArrayList<String> trigrams = getTrigrams(word);

        // For every trigram of a word
        for(String trigram : trigrams){
            // If the trigram doesn't exist in the dictionary, we skip it
            if(!dictionary.containsKey(trigram))
                continue;
            // Otherwise, we add every word hashed to that trigram to the list wordsWithCommonTrigrams
            for(String wordWithCommonTrigram : dictionary.get(trigram)){
                // If the list doesn' contain this word, we add it with a value of 1 (for occurrences)
                if(!wordsWithCommonTrigrams.contains(wordWithCommonTrigram)){
                    wordsWithCommonTrigrams.add(wordWithCommonTrigram);
                    wordOccurrences.add(new WordWithValue(wordWithCommonTrigram, 1));
                }
                // If the list contains this word, we increment its value (of occurrences)
                else{
                    for(WordWithValue wordWithValue : wordOccurrences) {
                        if(wordWithValue.word.equals(wordWithCommonTrigram)){
                            wordWithValue.incrementValue();
                        }
                    }
                }
            }
        }
        return wordOccurrences;
    }

    // This function takes a list of words, as well as their number of occurrences,
    // and returns the 100 words that appear the most often (i.e. have the highest amount of occurrences)
    private ArrayList mostSimilarWords(ArrayList<WordWithValue> wordOccurrences){
        ArrayList<WordWithValue> mostSimilarWords = new ArrayList();
        // Adds the first 100 words of the list wordOccurrences to the list mostSimilarWords
        if(wordOccurrences.size() > 100) {
            mostSimilarWords.addAll(wordOccurrences.subList(0, 100));

            // For the other words of the list wordOccurrences
            for (WordWithValue word : wordOccurrences.subList(101, wordOccurrences.size())) {
                // Replaces it with a word in mostSimilarWords if its amount of occurrences (i.e. value) is bigger
                for (WordWithValue similarWord : mostSimilarWords) {
                    if (!(similarWord.value < word.value)) {
                        continue;
                    }
                    mostSimilarWords.set(mostSimilarWords.indexOf(similarWord), word);
                    break;
                }
            }

            return mostSimilarWords;
        }

        else return wordOccurrences;
    }

    // This function takes a word and returns the 100 most similar words
    public ArrayList similarWords(String word) {
        ArrayList<WordWithValue> similarWords = mostSimilarWords(wordsWithSameTrigrams(word));
        return similarWords;
    }
}
