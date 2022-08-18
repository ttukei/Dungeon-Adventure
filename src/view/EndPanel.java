package view;

import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;

public class EndPanel extends JPanel{
    private JTextArea myEndTitle;
    private JTextArea myStats;

    public EndPanel(int theReason) {
        setBackground(new Color(150, 69, 25));
        setPreferredSize(new Dimension(623, 524));
        setLayout(null);
        if (theReason == 1) {
            makeTitleWin();
        } else {
            makeTitleLose();
        }
        makeStats();
        add(myEndTitle);
        add(myStats);
    }

    private void makeTitleWin() {
        myEndTitle = new JTextArea();
        myEndTitle.setEditable(false);
        myEndTitle.setLineWrap(true);
        myEndTitle.setOpaque(false);
        myEndTitle.setFont(new Font("Monospaced", Font.PLAIN, 50));
        myEndTitle.setForeground(new Color(0, 210, 0));
        myEndTitle.setText("Congratulation!\uD83D\uDE04 \nyou found the exit");
        myEndTitle.setBounds(40, 35, 600, 325);
    }

    private void makeTitleLose() {
        myEndTitle = new JTextArea();
        myEndTitle.setEditable(false);
        myEndTitle.setLineWrap(true);
        myEndTitle.setOpaque(false);
        myEndTitle.setFont(new Font("Monospaced", Font.PLAIN, 50));
        myEndTitle.setForeground(Color.RED);
        myEndTitle.setText("You Died \uD83D\uDE41");
        myEndTitle.setForeground(new Color(225, 0, 0));
        myEndTitle.setBounds(160, 75, 600, 325);
    }

    private void makeStats() {
        myStats = new JTextArea();
        myStats.setEditable(false);
        myStats.setLineWrap(true);
        myStats.setOpaque(false);
        myStats.setFont(new Font("Monospaced", Font.PLAIN, 21));
        myStats.setText(DungeonAdventure.getMyHero().endScreenReadOut());
        myStats.setBounds(130, 190, 550, 260);
    }
}
