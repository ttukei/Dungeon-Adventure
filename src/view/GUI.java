package view;


import controller.DungeonAdventure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextArea myDungeonArea;

    private JTextArea myReportArea;

    private JPanel myDungeonPanel;

    private JPanel myReportPanel;

    public GUI(String theTitle, DungeonAdventure theGame){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Window closes when you press the 'x'
        setResizable(false);                                  // Window is force sized at the games resolution
        setLayout(null);
        setTitle(theTitle);

        dungeonPanel();
        reportPanel();

        setBounds(0, 0, 750, 600);
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
        myDungeonPanel.setBounds(10, 10, 400, 350);
        myDungeonPanel.setLocation(7, 0);
        myDungeonPanel.add(new JScrollPane(myDungeonArea));
        add(myDungeonPanel);
    }

    private void reportPanel() {
        Font font = new Font("Monospaced", Font.PLAIN, 2);
        myReportArea = new JTextArea(11, 100);
        myReportArea.setBackground(Color.CYAN);
        myReportArea.setEditable(false);
        myReportArea.setFont(font);
        myReportPanel = new JPanel();
        myReportPanel.setBounds(10, 10, 400, 500);
        myReportPanel.setLocation(7, 360);
        myReportPanel.add(myReportArea);
        add(myReportPanel);
        myReportPanel.add(new JScrollPane(myReportArea));
        add(myReportPanel);
    }

    public void updateDungeonPanel(String theOutput) {
        myDungeonArea.setText("");
        myDungeonArea.append(theOutput + "\n");
    }

}
