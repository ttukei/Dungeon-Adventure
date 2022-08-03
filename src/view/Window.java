package view;

import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;

public class Window  extends Canvas {

    public Window(Dimension theDimensions, String theTitle, DungeonAdventure theDungeonAdventure){

        JFrame frame = new JFrame(theTitle);

        // Initial Window values
        frame.setPreferredSize(theDimensions);                       // Window Size
        frame.setMaximumSize(theDimensions);                         // Actual maximum possible size
        frame.setMinimumSize(theDimensions);                         // Actual minimum possible size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Window closes when you press the 'x'
        frame.setResizable(false);                                  // Window is force sized at the games resolution
        frame.setLocationRelativeTo(null);                          // Opens window in the middle of the monitor

        frame.setVisible(true);


        /////////Philips testing///////
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel);

    }

}
