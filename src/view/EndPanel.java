package view;

import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;

/**
 * This is the Window that shows when the game is ended to show
 * an end screen with the title for the reason on ending and the
 * users stats.
 */
public class EndPanel extends JPanel{

    /** The ending title message. */
    private final JTextArea myEndTitle;

    /** The stats of the user. */
    private final JTextArea myStats;

    /**
     * This constructs the JPanel with message and stats JComponent.
     * @param theReason the number which tells if user died or won(1 = won)
     */
    public EndPanel(final int theReason) {
        setBackground(new Color(28, 58, 74));
        setPreferredSize(new Dimension(623, 524));
        setLayout(null);

        //sets all fields and makes which title depending on what number was passed
        //then adds the two fields to the JPanel.
        myEndTitle = new JTextArea();
        if (theReason == 1) {
            makeTitleWin();
        } else {
            makeTitleLose();
        }
        myStats = new JTextArea();
        makeStats();
        add(myEndTitle);
        add(myStats);
    }

    /**
     * Makes the title for when the user wins.
     */
    private void makeTitleWin() {
        myEndTitle.setEditable(false);
        myEndTitle.setLineWrap(true);
        myEndTitle.setOpaque(false);
        myEndTitle.setFont(new Font("Monospaced", Font.PLAIN, 50));
        myEndTitle.setForeground(new Color(0, 210, 0));
        myEndTitle.setText("Congratulation!\uD83D\uDE04 \nyou found the exit");
        myEndTitle.setBounds(40, 35, 600, 325);
    }

    /**
     * Makes the title for when the user loses.
     */
    private void makeTitleLose() {
        myEndTitle.setEditable(false);
        myEndTitle.setLineWrap(true);
        myEndTitle.setOpaque(false);
        myEndTitle.setFont(new Font("Monospaced", Font.PLAIN, 50));
        myEndTitle.setForeground(Color.RED);
        myEndTitle.setText("You Died \uD83D\uDE41");
        myEndTitle.setForeground(new Color(225, 0, 0));
        myEndTitle.setBounds(160, 75, 600, 325);
    }

    /**
     * Makes the stats of the user when they win or lose.
     */
    private void makeStats() {
        myStats.setEditable(false);
        myStats.setLineWrap(true);
        myStats.setOpaque(false);
        myStats.setFont(new Font("Monospaced", Font.PLAIN, 21));
        myStats.setForeground(new Color(130, 140, 165));
        myStats.setText(DungeonAdventure.getMyHero().endScreenReadOut());
        myStats.setBounds(130, 190, 550, 260);
    }
}
