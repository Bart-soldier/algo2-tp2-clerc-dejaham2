package main;

public class Main {

    public static void main(String[] args) {
        long time1 = System.nanoTime();
        Dictionary dico = new Dictionary();
        //System.out.println(dico.isWord("Alexandrie"));
        Levenshtein.best5Levenshtein(dico.worldFilter(dico.sameTrigramWords("Alexndrie")), "Alexndrie");

       // System.out.println(Levenshtein.distance("marseille", "massilia"));
        long time2 = System.nanoTime();
        System.out.println(time2 - time1);

    }
}
