package main;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Reader {

    private Scanner scanner;

    public Reader(String fileName) {
        try {
            scanner = new Scanner(new File("src/fichier/" + fileName));
        }catch(Exception e){System.out.println(e.toString());}
    }


    public String readNextWord() {
        String string = "<";
        string = string.concat(scanner.next());
        string = string.concat(">");
        return string;
    }

    public void closeScanner(){
        scanner.close();
    }

    public boolean hasNext(){
        return scanner.hasNext();
    }
}
