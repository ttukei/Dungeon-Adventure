package view;

import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;

public class Window {

    private static GamePanel myGame;

    public Window(String theTitle, DungeonAdventure theDungeonAdventure){
        JFrame frame = new JFrame (theTitle);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (myGame = new GamePanel());
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(theDungeonAdventure);
        theDungeonAdventure.start();
        frame.setVisible (true);
    }

    public void updateDungeonPanel(String theMap) {
        myGame.updateDungeonPanel(theMap);
    }

    public void updateReportPanel(String theReport) {
        myGame.updateReportPanel(theReport);
    }


//    static class GamePanel extends JPanel{
//        private JTextArea myDungeonArea;
//        private JTextArea myReportArea;
//        private JScrollPane myReportScroll;
//        private final JLabel myHealthLabel;
//
//        public Game() {
//            JLabel hero = new JLabel (DungeonAdventure.getMyHero().getMyCharacterName());
//            hero.setFont(new Font("Default", Font.PLAIN, 20));
//            myHealthLabel = new JLabel ("❤ " + DungeonAdventure.getMyHero().getMyHealthPoints());
//            myHealthLabel.setFont(new Font("Default", Font.PLAIN, 22));
//
//            setPreferredSize (new Dimension (711, 562));
//            setLayout (null);
//
//            dungeonPanel();
//            reportPanel();
//
//            myDungeonArea.setBounds (10, 10, 540, 365);
//            myReportScroll.setBounds (10, 385, 540, 165);
//            hero.setBounds (575, 60, 170, 35);
//            myHealthLabel.setBounds (575, 100, 175, 30);
//
//            add (myDungeonArea);
//            add (myReportScroll);
//            add (hero);
//            add (myHealthLabel);
//        }
//
//        private void dungeonPanel() {
//            Font font = new Font("Monospaced", Font.PLAIN, 28);
//            myDungeonArea = new JTextArea(5, 5);
//            myDungeonArea.setBackground(Color.LIGHT_GRAY);
//            myDungeonArea.setEditable(false);
//            myDungeonArea.setFont(font);
//        }
//
//        private void reportPanel() {
//            Font font = new Font("Monospaced", Font.PLAIN, 18);
//            myReportArea = new JTextArea(5, 5);
//            myReportArea.setBackground(Color.CYAN);
//            myReportArea.setEditable(false);
//            myReportArea.setFont(font);
//            myReportScroll = new JScrollPane(myReportArea);
//        }
//
//        public void updateDungeonPanel(String theMap) {
//            myDungeonArea.setText("");
//            myDungeonArea.append(theMap + "\n");
//            updateHealth();
//        }
//
//        public void updateReportPanel(String theReport){
//            myReportArea.append("hi\n");
//            myReportArea.setCaretPosition(myReportArea.getDocument().getLength());
//        }
//
//        public void updateHealth() {
//            myHealthLabel.setText("❤ " + DungeonAdventure.getMyHero().getMyHealthPoints());
//        }
//    }

}
