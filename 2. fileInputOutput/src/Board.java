import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class represents the board. A chess board may contain up to 32 chess
 * pieces, 16 of each color. Each piece has a unique position on the board, between
 * a1 and h8. The letters a - h represent columns, while the numbers 1 through 8
 * represent rows. The lower left corner of the board is a1.
 * 
 * Implement save and load methods as described below.
 * 
 * @author Stephen J. Sarma-Weierman
 * @author YOUR NAME HERE
 */
public class Board {
    public static final int BOARD_SIZE = 8;
    private Piece [][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
    private int numberOfPieces = 0;
    
    public void addPiece(Piece p, String ps) {
        //magic
        int col = ((int)ps.charAt(0)) - 97;
        int row = ((int)ps.charAt(1)) - 49;
        if (board[row][col] == null)
            numberOfPieces++;
        board[row][col] = p;
    }
    
    public Piece removePiece(String ps) {
        int col = ((int)ps.charAt(0)) - 97;
        int row = ((int)ps.charAt(1)) - 49;
        Piece p = board[row][col];
        if (p != null)
            numberOfPieces--;
        board[row][col] = null;
        return p;
    }
    
    public void printBoard() {
        for (int i = BOARD_SIZE-1; i >= 0; i--) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == null) {
                    System.out.print("-- ");
                } else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    
    //Write the contents of the board to the specified file.
    //Hint: create a new File, new ObjectOutputStream,
    //and write first the number of pieces on the board, then each piece as 
    //object and position as two byte values.
    public boolean save(String filename) {
        File file = new File(filename);
        try{
            ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(file));
            fout.writeInt(numberOfPieces);
        //System.out.println(numberOfPieces);
        
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if(board[i][j] != null) {
                        fout.writeByte(i);
                        fout.writeByte(j);
                        fout.writeObject(board[i][j]);
                    }   
                }
            }
            fout.close();
        } catch (IOException e) { 
            return false;
        }
        
        return true;
    }
    
    //Read the contents of the board from the specified file.
    //Hint: create a new File, new ObjectInputStream,
    //and read first the number of pieces on the board, then each piece as 
    //object and position as two byte values.
    public boolean load(String filename) {
        File file = new File(filename);
        try {
            ObjectInputStream fin = new ObjectInputStream(new FileInputStream(file));
            numberOfPieces = fin.readInt();
            
            for (int i = 0; i<numberOfPieces; i++) {
                int row = fin.readByte();
                int col = fin.readByte();
                board[row][col] = (Piece)fin.readObject();
            }
         
        } catch (IOException | ClassNotFoundException e) {
            
            return false;
        }
        return true;
    }
}
