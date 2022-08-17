package view;

import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;

public class Window {

    private static GamePanel myGame;

    public Window(String theTitle, DungeonAdventure theDungeonAdventure){
        JFrame frame = new JFrame(theTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(myGame = new GamePanel());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(theDungeonAdventure);
        theDungeonAdventure.start();
        frame.setVisible(true);
    }

    public void updateDungeonPanel(String theMap) {
        myGame.updateDungeonPanel(theMap);
    }

    public void updateReportPanel(String theReport) {
        myGame.updateReportPanel(theReport);
    }

    public void updateRoomPanel(String theRoom) {
        myGame.updateRoomPanel(theRoom);
    }

    public void showEndPanel(boolean theReason) {

    }

}
