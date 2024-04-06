package s11.bomberguy.gui.controls;

import s11.bomberguy.PlayerControl;
import s11.bomberguy.gui.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import s11.bomberguy.Controls;

import static s11.bomberguy.gui.MenuPanel.FRAME_HEIGHT;
import static s11.bomberguy.gui.MenuPanel.FRAME_WIDTH;

public class ControlSettingsPanel extends JFrame {
    private ArrayList<PlayerSettingPanel> panels;
    private JButton saveButton;

    public ControlSettingsPanel() {
        panels = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            // Construct groups
            PlayerSettingPanel panel = new PlayerSettingPanel(i);

            // Position groups
            panel.setLocation(FRAME_WIDTH / 3 * (i - 1),0);

            // Add groups
            add(panel);
            panels.add(panel);
        }

        // Save button
        saveButton = new JButton("Mentés");
        saveButton.setBounds(FRAME_WIDTH / 2 - 100, FRAME_HEIGHT - 2 * 60, 200, 60);
        add(saveButton);

        // Style save button
        saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveButton.setBackground(Color.LIGHT_GRAY);
        saveButton.setForeground(Color.BLACK);

        // Configure frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Bomber Guy - Beállítások");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null); // Open in center of screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Save button action
        saveButton.addActionListener(
                e -> {
                    ArrayList<PlayerControl> controls = new ArrayList<>();

                    controls.add(panels.get(0).getPlayerControls());
                    controls.add(panels.get(1).getPlayerControls());
                    controls.add(panels.get(2).getPlayerControls());

                    Controls.setControls(controls);

                    setVisible(false);
                    dispose();
                    new MenuPanel();
                }
        );
    }
}
