package main;

public class WordWithValue {

    public String word;
    public int value;

    public WordWithValue(String word, int value) {
        this.word = word;
        this.value = value;
    }

    public void incrementValue(){
        value++;
    }
}