package view;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {

    public SidePanel() {
        setPreferredSize(new Dimension(200, 640));
        setBackground(Color.DARK_GRAY);
        add(new JLabel("Dungeon Adventure")).setForeground(Color.WHITE);
    }
}
