/* created by Andrew Stipcak */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Search {
    
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: java Search filename word");
            System.exit(0);
        }
        String filename = args[0];
        String word = args[1];
        File inFile = new File(filename);
        if(!inFile.exists()) {
            System.out.println("File not found");
            System.exit(1);
        }
        try {
            Scanner fin = new Scanner(inFile);
            int lineNumber = 1;
            while(fin.hasNext()) {
                String line = fin.nextLine();
                if (line.contains(word)) {
                    System.out.println(lineNumber + "\t: " + line);
                }
                lineNumber++;
            }
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("File read error");
        }
    }
    
}