package view;

import java.awt.*;
import javax.swing.*;

public class IntroPanel extends JPanel {
    private JTextArea myWarrior;
    private JTextArea myPriestess;
    private JTextArea myThief;
    private JTextArea myTitle;

    public IntroPanel() {
        setBackground(new Color(150, 69, 25));
        setPreferredSize(new Dimension (623, 524));
        setLayout(null);

        makeTitle();
        makeWarrior();
        makePriestess();
        makeThief();

        //add components
        add(myWarrior);
        add(myPriestess);
        add(myThief);
        add(myTitle);

        //set component bounds (only needed by Absolute Positioning)
        myWarrior.setBounds(40, 290, 150, 160);
        myPriestess.setBounds(240, 290, 150, 160);
        myThief.setBounds(430, 290, 150, 160);
        myTitle.setBounds(160, 15, 600, 325);
    }

    private void makeTitle() {
        myTitle = new JTextArea();
        myTitle.setEditable(false);
        myTitle.setLineWrap(true);
        myTitle.setOpaque(false);
        myTitle.setFont(new Font("Monospaced", Font.PLAIN, 50));
        myTitle.setText("Welcome to\n Dungeon\nAdventure");
    }

    private void makeWarrior() {
        myWarrior = new JTextArea();
        myWarrior.setEditable(false);
        myWarrior.setLineWrap(true);
        myWarrior.setOpaque(false);
        myWarrior.setFont(new Font("Monospaced", Font.BOLD, 14));
        myWarrior.setText("Warrior \uD83D\uDCAA" +
                "\n❤125" +
                "\nAttack Speed: 4" +
                "\nHit Chance: 80%" +
                "\nHit Range: 35-60" +
                "\nBlock Chance: 20%" +
                "\nEnter W to select");
    }

    private void makePriestess() {
        myPriestess = new JTextArea();
        myPriestess.setEditable(false);
        myPriestess.setLineWrap(true);
        myPriestess.setOpaque(false);
        myPriestess.setFont(new Font("Monospaced", Font.BOLD, 14));
        myPriestess.setText("Priestess \uD83D\uDCAB" +
                "\n❤75" +
                "\nAttack Speed: 5" +
                "\nHit Chance: 70%" +
                "\nHit Range: 25-45" +
                "\nBlock Chance: 30%" +
                "\nEnter P to select");
    }

    private void makeThief() {
        myThief = new JTextArea();
        myThief.setEditable(false);
        myThief.setLineWrap(true);
        myThief.setOpaque(false);
        myThief.setFont(new Font("Monospaced", Font.BOLD, 14));
        myThief.setText("Thief \uD83D\uDC00" +
                "\n❤75" +
                "\nAttack Speed: 6" +
                "\nHit Chance: 80%" +
                "\nHit Range: 20-40" +
                "\nBlock Chance: 40%" +
                "\nEnter T to select");
    }
}
