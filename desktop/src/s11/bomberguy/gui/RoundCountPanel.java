package s11.bomberguy.gui;

import s11.bomberguy.GameSetup;
import javax.swing.*;
import java.awt.*;

import static s11.bomberguy.gui.MenuPanel.FRAME_HEIGHT;
import static s11.bomberguy.gui.MenuPanel.FRAME_WIDTH;

public class RoundCountPanel extends JFrame {
    private final ButtonGroup group;
    private final JRadioButton[] buttons;
    private final JButton startButton;
    private final JLabel title;

    public RoundCountPanel() {
        // Construct buttons and title
        group = new ButtonGroup();
        buttons = new JRadioButton[3];
        startButton = new JButton("Indítás!");
        title = new JLabel("Körök száma");

        // Setup title
        title.setBounds((FRAME_WIDTH - 400) / 2, 30, 400, 100);
        title.setFont(new Font(null, Font.BOLD, 40));
        add(title);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        // Setup buttons
        for (int i = 0; i < 3; i++) {
            buttons[i] = new JRadioButton(i+1 + " kör");
            buttons[i].setBounds((FRAME_WIDTH - 200) / 2, 200 + i*60, 200, 60);
            group.add(buttons[i]);
        }
        startButton.setBounds(FRAME_WIDTH/2 - 200/2, 550, 200, 60);

        for (int i = 0; i < 3; i++) {
            add(buttons[i]);
        }
        add(startButton);

        // Button actions
        startButton.addActionListener(
                e -> {
                    for (int i = 0; i < 3; i++) {
                        if (buttons[i].isSelected()) {
                            GameSetup.setRounds(i+1);
                            break;
                        }
                    }
                    // start game here
                }
        );

        // Configure frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Bomber Guy - Körök száma");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null); // Open in center of screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
