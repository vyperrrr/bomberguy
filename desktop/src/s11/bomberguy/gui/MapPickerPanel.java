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
    public MapPickerPanel() {

        // Construct buttons and title
        mapButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            mapButtons[i] = new JButton();
        }
        continueButton = new JButton("Tovább");

        title = new JLabel("Pálya kiválasztása");


        // Setup buttons
        for (int i = 0; i < 3; i++) {
            mapButtons[i].setBounds(FRAME_WIDTH/3/2 - 300/2 + i * (FRAME_WIDTH/3), 200,300,300);
            mapButtons[i].setBorder(BorderFactory.createEmptyBorder());
            mapButtons[i].setContentAreaFilled(false);
            try {
                BufferedImage img = ImageIO.read(new File("assets/badlogic.jpg"));
                mapButtons[i].setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                System.out.println("Invalid img path!");
            }
        }
        mapButtons[0].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
        continueButton.setBounds(FRAME_WIDTH/2 - 200/2, 550, 200, 60);

        for (int i = 0; i < 3; i++) {
            add(mapButtons[i]);
        }
        add(continueButton);


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
                        mapButtons[finalI].setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
                    }
            );
            GameSetup.setMapNum(i);
        }

        continueButton.addActionListener(
                e -> {
                    setVisible(false);
                    dispose();
                    new RoundCountPanel();
                }
        );


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
