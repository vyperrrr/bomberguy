package s11.bomberguy.gui;

import s11.bomberguy.GameSetup;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;

import static s11.bomberguy.gui.MenuPanel.FRAME_HEIGHT;
import static s11.bomberguy.gui.MenuPanel.FRAME_WIDTH;

public class PlayerCountPanel extends JFrame {
    private final JButton twoPlayersButton;
    private final JButton threePlayersButton;
    private final JButton continueButton;
    private final JLabel text;
    public PlayerCountPanel() {
        // Construct buttons
        twoPlayersButton = new JButton("2");
        threePlayersButton = new JButton("3");
        continueButton = new JButton("Tovább");

        // Position buttons
        twoPlayersButton.setBounds(400, 300, 200, 60);
        threePlayersButton.setBounds(700, 300, 200, 60);
        continueButton.setBounds((FRAME_WIDTH - 200) / 2, 500, 200, 60);

        // Add buttons
        add(twoPlayersButton);
        add(threePlayersButton);
        add(continueButton);

        // Make twoPlayersButton appear selected by default
        twoPlayersButton.setBorder(new BevelBorder(BevelBorder.LOWERED));

        // Label
        text = new JLabel("Játékosok száma");
        text.setBounds((FRAME_WIDTH - 400) / 2, 120, 400, 100);
        text.setFont(new Font(null, Font.BOLD, 40));
        add(text);
        text.setHorizontalAlignment(SwingConstants.CENTER);

        // Configure frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Bomber Guy - Játékosok száma");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null); // Open in center of screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Button actions
        twoPlayersButton.addActionListener(
                e -> {
                    GameSetup.setPlayerNum(2);
                    twoPlayersButton.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    threePlayersButton.setBorder(null);
                }
        );

        threePlayersButton.addActionListener(
                e -> {
                    GameSetup.setPlayerNum(3);
                    threePlayersButton.setBorder(new BevelBorder(BevelBorder.LOWERED));
                    twoPlayersButton.setBorder(null);
                }
        );

        continueButton.addActionListener(
                e -> {
                    setVisible(false);
                    dispose();
                    new MapPickerPanel();
                }
        );
    }
}
