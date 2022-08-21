package view;

import java.awt.*;
import javax.swing.*;

/**
 * This is the Window that shows when the game is first ran to show
 * an intro screen and the hero selection stats.
 */
public class IntroPanel extends JPanel {

    /** The Warrior area for its stats. */
    private final JTextArea myWarrior;

    /** The Priestess area for its stats. */
    private final JTextArea myPriestess;

    /** The Thief area for its stats. */
    private final JTextArea myThief;

    /** The welcome title. */
    private final JTextArea myTitle;

    /**
     * This constructs everything into intro panel to be added to a frame.
     */
    public IntroPanel() {
        setBackground(new Color(28, 58, 74));
        setPreferredSize(new Dimension (623, 524));
        setLayout(null);

        //instantiates all the fields and calls its respected methods
        myTitle = new JTextArea();
        makeTitle();
        myWarrior = new JTextArea();
        makeWarrior();
        myPriestess = new JTextArea();
        makePriestess();
        myThief = new JTextArea();
        makeThief();

        //add components to the JPanel
        add(myWarrior);
        add(myPriestess);
        add(myThief);
        add(myTitle);

        //set component bounds so they are cool clean in window
        myWarrior.setBounds(40, 290, 150, 160);
        myPriestess.setBounds(240, 290, 150, 160);
        myThief.setBounds(430, 290, 150, 160);
        myTitle.setBounds(160, 15, 600, 325);
    }

    /**
     * Makes the Welcome title.
     */
    private void makeTitle() {
        myTitle.setEditable(false);
        myTitle.setLineWrap(true);
        myTitle.setOpaque(false);
        myTitle.setFont(new Font("Monospaced", Font.PLAIN, 50));
        myTitle.setText("Welcome to\n Dungeon\nAdventure");
        myTitle.setForeground(new Color(130, 140, 164));
    }

    /**
     * Make the Warrior section with its stats.
     */
    private void makeWarrior() {
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
        myWarrior.setForeground(new Color(130, 140, 165));
    }

    /**
     * Make the Priestess section with its stats
     */
    private void makePriestess() {
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
        myPriestess.setForeground(new Color(130, 140, 165));
    }

    /**
     * Make the Thief section with its stats
     */
    private void makeThief() {
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
        myThief.setForeground(new Color(130, 140, 165));
    }
}
