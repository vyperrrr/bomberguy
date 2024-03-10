package s11.bomberguy.gui;

import s11.bomberguy.gui.controls.ControlSettingsPanel;

import javax.swing.*;

public class MenuPanel extends JFrame {
    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 720;
    private static final int BUTTON_WIDTH = 600;
    private static final int BUTTON_HEIGHT = 80;

    private JButton startGameButton;
    private JButton settingsButton;
    private JButton exitButton;

    public MenuPanel() {
        // Construct buttons
        startGameButton = new JButton("Játék indítása");
        settingsButton = new JButton("Beállítások");
        exitButton = new JButton("Kilépés");

        // Add buttons to frame
        add(startGameButton);
        add(settingsButton);
        add(exitButton);

        // Position buttons
        startGameButton.setBounds((FRAME_WIDTH-BUTTON_WIDTH)/2,(FRAME_HEIGHT / 6), BUTTON_WIDTH, BUTTON_HEIGHT);
        settingsButton.setBounds((FRAME_WIDTH-BUTTON_WIDTH)/2,(FRAME_HEIGHT / 6) * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setBounds((FRAME_WIDTH-BUTTON_WIDTH)/2,(FRAME_HEIGHT / 6) * 3, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Configure frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Bomber Guy");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null); // Open in center of screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // startGame click action
        startGameButton.addActionListener(
                e -> {
                    setVisible(false);
                    dispose();
                    new PlayerCountPanel();
                }
        );

        // controlSettings click action
        settingsButton.addActionListener(
                e -> {
                    setVisible(false);
                    dispose();
                    new ControlSettingsPanel();
                }
        );

        // exitGame click action
        exitButton.addActionListener(
                e -> {
                    setVisible(false);
                    dispose();
                }
        );
    }
}
