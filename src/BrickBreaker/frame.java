package BrickBreaker;
import javax.swing.*;


//The javax.swing.JFrame class is a type of container which inherits the java.awt.Frame class.
// JFrame works like the main window where components like labels, buttons, text fields are added to create a GUI.
public class frame {

    public static void main(String[] args) {

        JFrame Frame = new JFrame("Brick Breaker Game");//the title of frame
        panel gameArea = new panel(); //object of class Panel
        Frame.setSize(910,600); //frame size(width,height)
        Frame.setResizable(false); //make the frame can not be resized
        Frame.setVisible(true);  // make the frame visible!
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//make sure to stop running when we close the frame

        // Adds a component to a specified container (window)
        Frame.add(gameArea);// add object of class Panel in frame class but must extend JFrame in Panel class

        Frame.setLocationRelativeTo(null); //make the frame at center in screen





    }
}





