package view;

import controller.DungeonAdventure;

import javax.swing.*;

/**
 * This is the Main window GUI for when the game is being played.
 * And update methods for controller to call when game begins
 */
public class Window {

    /** The GamePanel that has the JPanel holding everything. */
    private static GamePanel myGame;

    /**
     * Constructs the JFrame and adds the myGame JPanel to it to display the GUI.
     * @param theTitle the name title of the GUI window.
     * @param theDungeonAdventure the game controller.
     */
    public Window(final String theTitle, final DungeonAdventure theDungeonAdventure){
        final JFrame frame = new JFrame(theTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(myGame = new GamePanel()); //adding the jpanel
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(theDungeonAdventure); //adds the game controller
        theDungeonAdventure.start();    //begins the game to play in GUI
        frame.setVisible(true);         //makes it viewable
    }

    /**
     * This updates the main large panel that shows the dungeon map each time
     * the user moves to another room.
     * @param theMap the Dungeon Map as a string
     */
    public void updateDungeonPanel(final String theMap) {
        //calls the updates from myGame for abstraction
        myGame.updateDungeonPanel(theMap);
    }

    /**
     * This updates the report panel that shows the reports that are happening
     * each time the user interacts with game.
     * @param theReport the reports as a string.
     */
    public void updateReportPanel(final String theReport) {
        //calls the updates from myGame for abstraction
        myGame.updateReportPanel(theReport);
    }

    /**
     * This updates the room panel that shows the user what doors are open in the
     * current room and what room items there are.
     * @param theRoom the current room map as a string.
     */
    public void updateRoomPanel(final String theRoom) {
        //calls the updates from myGame for abstraction
        myGame.updateRoomPanel(theRoom);
    }
}
