import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author APC
 */
public class CheckFile {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("usage: java CheckFile filename");
            System.exit(1);
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            System.err.println("FIle not found!");
            System.exit(2);
        }
        try {
            DataInputStream fin = new DataInputStream(new FileInputStream(file));
            int b;
            do {
                b = fin.read();
                if (b == 0) {
                    System.out.println("File is binary");
                    break;
                }
                if ((char) b == '\r') {
                    b = fin.read();
                    if ((char) b == '\n') {
                        System.out.println("File is text(windows)");
                        break;
                    }
                    System.out.println("File is text(unix)");
                    break;
                }
            } while (true);
            
            
        } catch (FileNotFoundException e) {
            System.out.println("File read error");
        }
    }
}
