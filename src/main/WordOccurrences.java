package main;

public class WordOccurrences {

    public final String word;
    public int occurrences;

    public WordOccurrences(String word) {
        this.word = word;
        this.occurrences = 1;
    }

    public void incrementOccurrences(){
        occurrences++;
    }
}