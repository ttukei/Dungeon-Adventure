package view;

import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;

/**
 * This makes Main JPanel that has all GUI components of when the
 * game is running. The object created in Window class
 */
public class GamePanel extends JPanel {

    /** The text area for the dungeon map. */
    private final JTextArea myDungeonArea;

    /** The text area for the reports. */
    private final JTextArea myReportArea;

    /** The text area for the current room map. */
    private final JTextArea myRoomArea;

    /** The text area for users current inventory. */
    private final JTextArea myInventoryArea;

    /** The Scroll pane for the reports to be in. */
    private final JScrollPane myReportScroll;

    /** The label for the user's health points. */
    private final JLabel myHealthLabel;

    /** The label for the users inventory. */
    private final JLabel myInventoryLabel;

    /**
     * Constructs everything and calls the methods for the fields to be set
     * and adds it all to the JPanel when this class is called in Window.java.
     */
    public GamePanel() {
        //makes the labels for user's name and inventory label and area
        setBackground(new Color(150, 69, 25));
        final JLabel hero = new JLabel(DungeonAdventure.getMyHero().getMyCharacterName());
        hero.setFont(new Font("Default", Font.PLAIN, 20));
        myHealthLabel = new JLabel("❤ " + DungeonAdventure.getMyHero().getMyHealthPoints());
        myHealthLabel.setFont(new Font("Default", Font.PLAIN, 22));
        myInventoryLabel = new JLabel("Inventory \uD83C\uDF92:");
        myInventoryLabel.setFont(new Font("Default", Font.PLAIN, 20));
        myInventoryArea = new JTextArea(DungeonAdventure.getMyHero().displayInventory());
        myInventoryArea.setFont(new Font("Default", Font.PLAIN, 14));
        myInventoryArea.setEditable(false);
        myInventoryArea.setLineWrap(true);
        myInventoryArea.setOpaque(false);
        setPreferredSize (new Dimension(750, 562));
        setLayout(null);

        //Instantiate fields and calls its respected methods after
        myDungeonArea = new JTextArea(5, 5);
        dungeonPanel();
        myReportArea = new JTextArea(5, 5);
        reportPanel();
        myRoomArea = new JTextArea(5, 5);
        roomPanel();
        myReportScroll = new JScrollPane(myReportArea);
        myReportScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //sets the location and size of all the components
        myDungeonArea.setBounds(10, 10, 540, 365);
        myReportScroll.setBounds(10, 385, 540, 165);
        myRoomArea.setBounds(575, 150, 160, 165);
        hero.setBounds(575, 60, 170, 35);
        myHealthLabel.setBounds(575, 100, 175, 30);
        myInventoryLabel.setBounds(575, 355, 175, 30);
        myInventoryArea.setBounds(575, 385, 140, 150);

        //adds all the components to the JPanel
        add(myDungeonArea);
        add(myReportScroll);
        add(myRoomArea);
        add(hero);
        add(myHealthLabel);
        add(myInventoryLabel);
        add(myInventoryArea);
    }

    /**
     * Makes the Dungeon map textArea.
     */
    private void dungeonPanel() {
        myDungeonArea.setBackground(Color.LIGHT_GRAY);
        myDungeonArea.setEditable(false);
        myDungeonArea.setFont(new Font("Monospaced", Font.PLAIN, 28));
    }

    /**
     * Makes the report scroll textArea.
     */
    private void reportPanel() {
        myReportArea.setBackground(Color.LIGHT_GRAY);
        myReportArea.setEditable(false);
        myReportArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
    }

    /**
     * Makes the current room map textArea.
     */
    private void roomPanel() {
        myRoomArea.setOpaque(false);
        myRoomArea.setEditable(false);
        myRoomArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
    }

    /**
     * This is public for it to be called from window.java to update the
     * dungeon map when the user moves.
     */
    public void updateDungeonPanel(final String theMap) {
        myDungeonArea.setText("");
        myDungeonArea.append(theMap + "\n");
        updateHealth();     //these get called here since when the user moves
        updateInventory();  //there health and inventory should be updated too
    }

    /**
     * This is public for it to be called from window.java to update the
     * report area when the game is being played.
     */
    public void updateReportPanel(final String theReport){
        myReportArea.append(theReport + "\n");
        myReportArea.setCaretPosition(myReportArea.getDocument().getLength());
    }

    /**
     * This is public for it to be called from window.java to update the
     * current room map when the user moves.
     */
    public void updateRoomPanel(final String theRoom) {
        myRoomArea.setText("");
        myRoomArea.append(theRoom);
    }

    /**
     * This is called by the updateDungeonPanel when the user moves to also update
     * the health bar JLabel.
     */
    private void updateHealth() {
        myHealthLabel.setText("❤ " + DungeonAdventure.getMyHero().getMyHealthPoints());
    }

    /**
     * This is called by the updateDungeonPanel when the user moves to also update
     * the pillars in the user's inventory textArea.
     */
    private void updateInventory(){
        myInventoryArea.setText(DungeonAdventure.getMyHero().displayInventory());
    }
}
