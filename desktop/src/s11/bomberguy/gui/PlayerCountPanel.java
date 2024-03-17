package s11.bomberguy.gui;

import s11.bomberguy.DesktopLauncher;
import s11.bomberguy.GameSetup;

import javax.swing.*;

import java.awt.*;

import static s11.bomberguy.gui.MenuPanel.FRAME_HEIGHT;
import static s11.bomberguy.gui.MenuPanel.FRAME_WIDTH;

public class PlayerCountPanel extends JFrame {
    private JButton twoPlayersButton;
    private  JButton threePlayersButton;
    private JButton continueButton;
    private JLabel text;
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
                }
        );

        threePlayersButton.addActionListener(
                e -> {
                    GameSetup.setPlayerNum(3);
                }
        );

        continueButton.addActionListener(
                e -> {
                    setVisible(false);
                    dispose();
                    //new MapPickerPanel();
                    //Game starts from here for now
                    DesktopLauncher.launchGame();
                }
        );
    }
}
