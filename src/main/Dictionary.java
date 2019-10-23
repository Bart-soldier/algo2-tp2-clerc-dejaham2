package main;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary {

    private HashMap<String, ArrayList<String>> dico;

    public Dictionary(){
        dico = new HashMap<>(17000);
        Reader reader = new Reader("dico.txt");
        String word;
        while (reader.hasNext()){
            word = reader.readNextWord();
            addTrigramsToHash(getTrigrams(word), word);
        }
    }


    private ArrayList<String> getTrigrams(String string) {
        ArrayList<String> trigrams = new ArrayList();
        for(int i = 0; i < string.length() - 2; i++){
            trigrams.add((String) string.subSequence(i, i+3));
        }
        return trigrams;
    }

    public void addTrigramsToHash(ArrayList<String> trigrams, String word){
        for(String string : trigrams){
            dico.computeIfAbsent(string, k -> new ArrayList<String>());
            dico.get(string).add(word);
        }
    }

    public boolean isWord(String word) {
        String trigram = "<";
        int i = 0;

        do {
            trigram = trigram.concat((String) word.subSequence(i, i+1));
            i++;
        } while ((i < word.length()) && (i < 2));

        if(word.length() == 1) trigram += ">";

        String wordWithSymbols = "<" + word + ">";
        return dico.get(trigram).contains(wordWithSymbols);
    }

    public ArrayList sameTrigramWords(String word){
        ArrayList<String> trigrams = getTrigrams(word);
        ArrayList<String> commonWords = new ArrayList<String>();

        for(String string : trigrams){
            commonWords.addAll(dico.get(string));
        }
        return commonWords;
    }

    public void wordsFilter(String word, ArrayList<String> commonWords) {

        ArrayList<String> wordTrigrams = getTrigrams(word);

        for(String string : commonWords) {
            
        }
    }
}
