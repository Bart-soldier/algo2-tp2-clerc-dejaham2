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
        ArrayList<String> commonWords = new ArrayList<>();
        ArrayList<WordOccurrences> wordOccurrences = new ArrayList<>();

        for(String string : trigrams){
            if(!dico.containsKey(string))
                continue;
            for(String dicoWord : dico.get(string)){
                if(!commonWords.contains(dicoWord)){
                    commonWords.add(dicoWord);
                    wordOccurrences.add(new WordOccurrences(dicoWord));
                }
                else{
                    for(WordOccurrences wordOccurrences1 : wordOccurrences) {
                        if(wordOccurrences1.word.equals(dicoWord)){
                            wordOccurrences1.incrementOccurrences();
                        }

                    }
                }
            }
        }
        return wordOccurrences;
    }

    public ArrayList worldFilter(ArrayList<WordOccurrences> wordOccurrences){
        ArrayList<WordOccurrences> best100 = new ArrayList();
        best100.addAll(wordOccurrences.subList(0,100));

        for(WordOccurrences item : wordOccurrences.subList(101, wordOccurrences.size())){
            for(WordOccurrences best100Word : best100){
                if(!(best100Word.occurrences < item.occurrences)) {
                    continue;
                }
                best100.set(best100.indexOf(best100Word), item);
                break;
            }
        }

        return best100;

    }
}
