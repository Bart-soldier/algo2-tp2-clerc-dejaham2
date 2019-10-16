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
            addTrigrammesToHash(getTrigrammes(word), word);
        }
    }


    private ArrayList<String> getTrigrammes(String string) {
        ArrayList<String> trigrammes = new ArrayList();
        for(int i = 0; i < string.length() - 2; i++){
            trigrammes.add((String) string.subSequence(i, i+3));
        }
        return trigrammes;
    }

    public void addTrigrammesToHash(ArrayList<String> trigrammes, String word){
        for(String string : trigrammes){
            dico.computeIfAbsent(string, k -> new ArrayList<String>());
            dico.get(string).add(word);
        }
    }
}
