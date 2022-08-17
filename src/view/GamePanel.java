package view;

import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private JTextArea myDungeonArea;
    private JTextArea myReportArea;
    private JTextArea myRoomArea;
    private JTextArea myInventoryArea;
    private JScrollPane myReportScroll;
    private final JLabel myHealthLabel;
    private final JLabel myInventoryLabel;


    public GamePanel() {
        JLabel hero = new JLabel(DungeonAdventure.getMyHero().getMyCharacterName());
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

        dungeonPanel();
        reportPanel();
        roomPanel();

        myDungeonArea.setBounds(10, 10, 540, 365);
        myReportScroll.setBounds(10, 385, 540, 165);
        myRoomArea.setBounds(575, 150, 160, 165);
        hero.setBounds(575, 60, 170, 35);
        myHealthLabel.setBounds(575, 100, 175, 30);
        myInventoryLabel.setBounds(575, 355, 175, 30);
        myInventoryArea.setBounds(575, 385, 140, 150);

        add(myDungeonArea);
        add(myReportScroll);
        add(myRoomArea);
        add(hero);
        add(myHealthLabel);
        add(myInventoryLabel);
        add(myInventoryArea);
    }

    private void dungeonPanel() {
        Font font = new Font("Monospaced", Font.PLAIN, 28);
        myDungeonArea = new JTextArea(5, 5);
        myDungeonArea.setBackground(Color.LIGHT_GRAY);
        myDungeonArea.setEditable(false);
        myDungeonArea.setFont(font);
    }

    private void reportPanel() {
        Font font = new Font("Monospaced", Font.PLAIN, 15);
        myReportArea = new JTextArea(5, 5);
        myReportArea.setBackground(Color.LIGHT_GRAY);
        myReportArea.setEditable(false);
        myReportArea.setFont(font);
        myReportScroll = new JScrollPane(myReportArea);
        myReportScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private void roomPanel() {
        Font font = new Font("Monospaced", Font.PLAIN, 20);
        myRoomArea = new JTextArea(5, 5);
        //myRoomArea.setBackground(Color.LIGHT_GRAY);
        myRoomArea.setOpaque(false);
        myRoomArea.setEditable(false);
        myRoomArea.setFont(font);
    }

    public void updateDungeonPanel(String theMap) {
        myDungeonArea.setText("");
        myDungeonArea.append(theMap + "\n");
        updateHealth();
        updateInventory();
    }

    public void updateReportPanel(String theReport){
        myReportArea.append(theReport + "\n");
        myReportArea.setCaretPosition(myReportArea.getDocument().getLength());
    }

    public void updateRoomPanel(String theRoom) {
        myRoomArea.setText("");
        myRoomArea.append(theRoom);
    }


    private void updateHealth() {
        myHealthLabel.setText("❤ " + DungeonAdventure.getMyHero().getMyHealthPoints());
    }

    private void updateInventory(){
        myInventoryArea.setText(DungeonAdventure.getMyHero().displayInventory());
    }
}
