package s11.bomberguy.gui;

import javax.swing.*;
import static s11.bomberguy.gui.MenuPanel.FRAME_HEIGHT;
import static s11.bomberguy.gui.MenuPanel.FRAME_WIDTH;
public class MapPickerPanel extends JFrame {
    public MapPickerPanel() {

        // Configure frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Bomber Guy");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null); // Open in center of screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
