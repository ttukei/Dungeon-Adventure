package view;

import controller.DungeonAdventure;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

import static java.awt.SystemColor.text;

public class Window  extends Canvas {

    //private final JTextArea myOutput;

    public Window(Dimension theDimensions, String theTitle, DungeonAdventure theDungeonAdventure){

        JFrame frame = new JFrame(theTitle);

//        // Initial Window values
//        frame.setPreferredSize(theDimensions);                      // Window Size
//        frame.setMaximumSize(theDimensions);                        // Actual maximum possible size
//        frame.setMinimumSize(theDimensions);                        // Actual minimum possible size
//        frame.setSize(theDimensions.width, theDimensions.height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Window closes when you press the 'x'
        frame.setResizable(false);                                  // Window is force sized at the games resolution
        frame.setLocationRelativeTo(null);                          // Opens window in the middle of the monitor
        frame.setVisible(true);                                     // Show the window
        frame.add(theDungeonAdventure);
        theDungeonAdventure.start();

        /////////Philips testing///////
//        MainPanel mainPanel = new MainPanel();
//        frame.add(mainPanel);

        JTextArea jt = new JTextArea(10, 20);
        JPanel p = new JPanel();

        p.add(jt);
        p.setBackground(Color.DARK_GRAY);

        frame.add(p);
        frame.setSize(600, 400);

//        myOutput = new JTextArea(5, 10);
//
//        frame.add(myOutput);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//        frame.setSize(theDimensions.width, theDimensions.height);
//        final JScrollPane scrollPane = new JScrollPane(myOutput);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        frame.add(scrollPane);
    }

}
