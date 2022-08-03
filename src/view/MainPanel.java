package view;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setPreferredSize(new Dimension(1024, 640));
        setLayout(new BorderLayout());
        add(new GamePanel(), BorderLayout.CENTER);
        add(new SidePanel(), BorderLayout.EAST);
    }
}
