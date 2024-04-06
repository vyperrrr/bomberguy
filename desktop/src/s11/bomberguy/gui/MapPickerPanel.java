package s11.bomberguy.gui;

import s11.bomberguy.GameSetup;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static s11.bomberguy.gui.MenuPanel.FRAME_HEIGHT;
import static s11.bomberguy.gui.MenuPanel.FRAME_WIDTH;

public class MapPickerPanel extends JFrame {
    private final JLabel title;
    private final JButton[] mapButtons;
    private final JButton continueButton;
    private final JButton backButton;

    public MapPickerPanel() {

        // Construct buttons and title
        mapButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            mapButtons[i] = new JButton();
        }
        continueButton = new JButton("Tovább");
        backButton = new JButton("Vissza");
        title = new JLabel("Pálya kiválasztása");

        // Setup buttons
        for (int i = 0; i < 3; i++) {
            mapButtons[i].setBounds(FRAME_WIDTH/6 - 300/2 + i * (FRAME_WIDTH/3), 200,300,156);
            mapButtons[i].setBorder(BorderFactory.createEmptyBorder());
            mapButtons[i].setContentAreaFilled(false);
            try {
                BufferedImage img = ImageIO.read(new File("assets/maps/map_" + (((int)i) + 1) + "_background.png"));
                mapButtons[i].setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                mapButtons[i].setContentAreaFilled(true);
                mapButtons[i].setBackground(Color.GRAY);
                mapButtons[i].setText("Kép nem található!");
            }
        }


        mapButtons[GameSetup.getMapNum()-1].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        // Position navigation buttons
        continueButton.setBounds(400, 500, 200, 60);
        backButton.setBounds(700, 500, 200, 60);

        // Style navigation buttons
        continueButton.setBorder(BorderFactory.createRaisedBevelBorder());
        continueButton.setBackground(Color.LIGHT_GRAY);
        continueButton.setForeground(Color.BLACK);

        backButton.setBorder(BorderFactory.createRaisedBevelBorder());
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.setForeground(Color.BLACK);

        // Add buttons to frame
        for (int i = 0; i < 3; i++) {
            add(mapButtons[i]);
        }
        add(continueButton);
        add(backButton);

        // Setup title
        title.setBounds((FRAME_WIDTH - 400) / 2, 30, 400, 100);
        title.setFont(new Font(null, Font.BOLD, 40));
        add(title);
        title.setHorizontalAlignment(SwingConstants.CENTER);


        // Button actions
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            mapButtons[i].addActionListener(
                    e -> {
                        for (int j = 0; j < 3; j++) {
                            mapButtons[j].setBorder(BorderFactory.createEmptyBorder());
                        }
                        mapButtons[finalI].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                        GameSetup.setMapNum(finalI+1);
                    }
            );
        }

        continueButton.addActionListener(
                e -> {
                    setVisible(false);
                    dispose();
                    new RoundCountPanel();
                }
        );

        backButton.addActionListener(
                e -> {
                    setVisible(false);
                    dispose();
                    new PlayerCountPanel();
                }
        );


        // Configure frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("Bomber Guy - Pálya választás");
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null); // Open in center of screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
