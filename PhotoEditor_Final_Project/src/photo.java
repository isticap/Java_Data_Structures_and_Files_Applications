import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public final class photo extends JFrame implements ActionListener {
    public BufferedImage picture;
    public String filename;
    public int[][] picArray;
    public int width, height, size;
    Stack<BufferedImage> stack = new VectorStack<>(15);
    Stack<BufferedImage> stack2 = new VectorStack<>(15);
    
    
    
    JLabel label = new JLabel("");
    JButton zoominbutton = new JButton("zoom in");
    JButton zoomoutbutton = new JButton("zoom out");
    JButton cropbutton = new JButton("crop");
    JButton addnewphoto = new JButton("add another photo");
    JButton previousbutton = new JButton("Previous photo");
    JButton nextbutton = new JButton("Next photo");
    JButton grayscalebutton = new JButton("grayscale");
    JButton saveimagebutton = new JButton("save image and exit");
    
    public photo(String filename) {
        try {
            File file = new File(filename);
            if (file.isFile()) {
                //picture = ImageIO.read(file);
                stack.push(ImageIO.read(file));
            } else {
                URL url = getClass().getResource(filename);
                if (url == null) {
                    url = new URL(filename);
                }
                //picture = ImageIO.read(url);
                stack.push(ImageIO.read(url));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not open file: " + filename + " Reason: " + e);
        }
        
        if (stack.peek() == null) {
            throw new RuntimeException("Invalid image file: " + filename);
        }
        
        ImageIcon icon = new ImageIcon(stack.peek());
        label.setIcon(icon);
        //width = stack.peek().getWidth();
        //height = stack.peek().getHeight();
        
        picArray = new int[stack.peek().getWidth()][stack.peek().getHeight()];

        for (int i = 0; i < stack.peek().getWidth(); i++) {
            for (int j = 0; j < stack.peek().getHeight(); j++) {
                picArray[i][j] = stack.peek().getRGB(i, j);
            }
        }
   
        
        JPanel panel = new JPanel();
        panel.add(label);
        zoominbutton.addActionListener(this);
        panel.add(zoominbutton);
        zoomoutbutton.addActionListener(this);
        panel.add(zoomoutbutton);
        cropbutton.addActionListener(this);
        panel.add(cropbutton);
        addnewphoto.addActionListener(this);
        panel.add(addnewphoto);
        previousbutton.addActionListener(this);
        panel.add(previousbutton);
        nextbutton.addActionListener(this);
        panel.add(nextbutton);
        grayscalebutton.addActionListener(this);
        panel.add(grayscalebutton);
        saveimagebutton.addActionListener(this);
        panel.add(saveimagebutton);
        add(panel);
        setSize(stack.peek().getWidth() + 100, stack.peek().getHeight() + 175);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent pushed) {
        if (pushed.getSource() == zoominbutton) {
            System.out.println("zoom+ functionality");
        }
        if (pushed.getSource() == zoomoutbutton) {
            System.out.println("zoom- functionality");
        }
        if (pushed.getSource() == cropbutton) {
            System.out.println("crop functionality");
            picture = stack.pop();
            stack.push(picture.getSubimage(0, 0, 150, 150));
            ImageIcon icon = new ImageIcon(stack.peek());
            label.setIcon(icon);
        }
        if (pushed.getSource() == addnewphoto) {
            System.out.println("add new photo functionality");
            addnewphoto();
        }
        if (pushed.getSource() == previousbutton) {
            System.out.println("previous photo functionality");
            previousphoto();
        }
        if (pushed.getSource() == nextbutton) {
            System.out.println("next photo functionality");
            nextphoto();
        }
        if (pushed.getSource() == grayscalebutton) {
            System.out.println("grayscale functionality");
            blackandwhite();
        }
        if (pushed.getSource() == saveimagebutton) {
            System.out.println("save image functionality");
            saveimage();
        }
    }
    
    private void addnewphoto() {
        System.out.println("please enter the pictures filename or enter a url pointing to an image: ");
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        try {
            File file = new File(string);
            if (file.isFile()) {
                //picture = ImageIO.read(file);
                stack.push(ImageIO.read(file));
            } else {
                URL url = getClass().getResource(string);
                if (url == null) {
                    url = new URL(string);
                }
                //picture = ImageIO.read(url);
                stack.push(ImageIO.read(url));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not open file: " + string + " Reason: " + e);
        }
        
        if (stack.peek() == null) {
            throw new RuntimeException("Invalid image file: " + string);
        }
        
        ImageIcon icon = new ImageIcon(stack.peek());
        label.setIcon(icon);
        setSize(stack.peek().getWidth() + 100, stack.peek().getHeight() + 175);
        
        //width = stack.peek().getWidth();
        //height = stack.peek().getHeight();
        
        picArray = new int[stack.peek().getWidth()][stack.peek().getHeight()];

        for (int i = 0; i < stack.peek().getWidth(); i++) {
            for (int j = 0; j < stack.peek().getHeight(); j++) {
                picArray[i][j] = stack.peek().getRGB(i, j);
            }
        }
    }
    
    public void previousphoto() {
//        if (stack2.empty()) {
            stack2.push(stack.pop());
            if (stack.empty()){
                System.out.println("no previous photos");
                stack.push(stack2.pop());
            }
            ImageIcon icon = new ImageIcon(stack.peek());
            label.setIcon(icon);
            setSize(stack.peek().getWidth() + 100, stack.peek().getHeight() + 175);
//        }
//        if (!stack2.empty()) {
//            
//        }
    }
    
    public void nextphoto() {
        if (stack2.empty()) {
            System.out.println("no photos up next");
        } else {
            stack.push(stack2.pop());
            ImageIcon icon = new ImageIcon(stack.peek());
            label.setIcon(icon);
            setSize(stack.peek().getWidth() + 100, stack.peek().getHeight() + 175);
        }
    }
    
    public void blackandwhite() {
        for (int i = 0; i < stack.peek().getWidth(); i++) {
            for (int j = 0; j < stack.peek().getHeight(); j++) {
                int p = stack.peek().getRGB(i,j);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                
                int avg = (r+g+b)/3;
                
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;
                
                stack.peek().setRGB(i, j, p);
            }
        }
        ImageIcon icon = new ImageIcon(stack.peek());
        label.setIcon(icon);
    }
    
    public void ScrollPane() {
        JScrollPane jsp = new JScrollPane(label);
        getContentPane().add(jsp);
        setVisible(true);
    }
    
    public void saveimage() {
//        System.out.println("enter name for savefile: ");
//        Scanner in = new Scanner(System.in);
//        try {
//        File file = new File(in.next() + ".png");
//        ImageIO.write(picture, "png", file);
//        } catch(IOException e) {
//            System.out.print(e);
//        }
        System.exit(0);
    }
    
    
    public static void main(String[] args) {
        System.out.println("please enter the pictures filename or enter a url pointing to an image: ");
        Scanner in = new Scanner(System.in);
        photo pic = new photo(in.nextLine());
        
    }

    
    
}
