package view;


import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
    private JTextArea myDungeonArea;
    private JPanel myDungeonPanel;
    private JButton button;

    public GUI(String theTitle, DungeonAdventure theGame){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Window closes when you press the 'x'
        setResizable(false);                                  // Window is force sized at the games resolution
        setLayout(null);
        setTitle(theTitle);

        dungeonPanel();
        //reportPanel();

        setBounds(0, 0, 450, 400);
        setLocationRelativeTo(null);                          // Opens window in the middle of the monitor
        setVisible(true);
        add(theGame);
        theGame.start();
    }

    private void dungeonPanel() {
        Font font = new Font("Monospaced", Font.PLAIN, 28);
        myDungeonArea = new JTextArea();
        myDungeonArea.setBackground(Color.LIGHT_GRAY);
        myDungeonArea.setEditable(false);
        myDungeonArea.setFont(font);
        myDungeonPanel = new JPanel();
        myDungeonPanel.setBounds(10, 10, 400, 250);
        myDungeonPanel.setLocation(-70, -3);
        myDungeonPanel.add(new JScrollPane(myDungeonArea));
        add(myDungeonPanel);
    }

    private void reportPanel() {
        button = new JButton("test❤");
        button.setBounds(10, 320, 100, 25);
        add(button);
        button.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        myDungeonArea.append("hi\n");
    }

    public void updateDungeonPanel(String theOutput) {
        myDungeonArea.setText("");
        myDungeonArea.append(theOutput + "\n");
    }

}
