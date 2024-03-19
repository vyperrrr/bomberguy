package s11.bomberguy.gui.controls;

import com.badlogic.gdx.Input;
import s11.bomberguy.PlayerControl;
import s11.bomberguy.gui.MenuPanel;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.getKeyText;
import static s11.bomberguy.Controls.getControls;

public class PlayerSettingPanel extends JPanel {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 60;

    private ControlButton upButton;
    private ControlButton downButton;
    private ControlButton leftButton;
    private ControlButton rightButton;
    private ControlButton bombButton;
    private ControlButton extraButton;
    private JLabel player;

    public PlayerSettingPanel(int num) {
        // Configure panel
        setSize(MenuPanel.FRAME_WIDTH / 3, MenuPanel.FRAME_HEIGHT - 200);
        setLayout(null);

        // Construct buttons
        upButton = new ControlButton("Előre: " + getKeyText(getControls().get(num-1).getUpButton()), getControls().get(num-1).getUpButton());
        downButton = new ControlButton("Hátra: " + getKeyText(getControls().get(num-1).getDownButton()), getControls().get(num-1).getDownButton());
        leftButton = new ControlButton("Balra: " + getKeyText(getControls().get(num-1).getLeftButton()), getControls().get(num-1).getLeftButton());
        rightButton = new ControlButton("Jobbra: " + getKeyText(getControls().get(num-1).getRightButton()), getControls().get(num-1).getRightButton());
        bombButton = new ControlButton("Bomba: " + getKeyText(getControls().get(num-1).getBombButton()), getControls().get(num-1).getBombButton());
        extraButton = new ControlButton("Extra: " + getKeyText(getControls().get(num-1).getExtraButton()), getControls().get(num-1).getExtraButton());

        // Position buttons
        upButton.setBounds((MenuPanel.FRAME_WIDTH / 3 - BUTTON_WIDTH) / 2, (MenuPanel.FRAME_HEIGHT / 10) * 1, BUTTON_WIDTH, BUTTON_HEIGHT);
        downButton.setBounds((MenuPanel.FRAME_WIDTH / 3 - BUTTON_WIDTH) / 2, (MenuPanel.FRAME_HEIGHT / 10) * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        leftButton.setBounds((MenuPanel.FRAME_WIDTH / 3 - BUTTON_WIDTH) / 2, (MenuPanel.FRAME_HEIGHT / 10) * 3, BUTTON_WIDTH, BUTTON_HEIGHT);
        rightButton.setBounds((MenuPanel.FRAME_WIDTH / 3 - BUTTON_WIDTH) / 2, (MenuPanel.FRAME_HEIGHT / 10) * 4, BUTTON_WIDTH, BUTTON_HEIGHT);
        bombButton.setBounds((MenuPanel.FRAME_WIDTH / 3 - BUTTON_WIDTH) / 2, (MenuPanel.FRAME_HEIGHT / 10) * 5, BUTTON_WIDTH, BUTTON_HEIGHT);
        extraButton.setBounds((MenuPanel.FRAME_WIDTH / 3 - BUTTON_WIDTH) / 2, (MenuPanel.FRAME_HEIGHT / 10) * 6, BUTTON_WIDTH, BUTTON_HEIGHT);

        // Add buttons
        add(upButton);
        add(downButton);
        add(leftButton);
        add(rightButton);
        add(bombButton);
        add(extraButton);

        // Construct label
        player = new JLabel(num + ". Játékos");

        // Position label
        player.setBounds((MenuPanel.FRAME_WIDTH / 3 - BUTTON_WIDTH) / 2, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        player.setHorizontalAlignment(SwingConstants.CENTER);

        // add label
        add(player);

        // Button actions
        upButton.addActionListener(
                e -> {
                    JDialog dialog = new JDialog();
                    dialog.add(new JLabel("Nyomja le a kívánt billentyűt!"));
                    dialog.setVisible(true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setSize(300,200);
                    dialog.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}

                        @Override
                        public void keyPressed(KeyEvent e) {
                            upButton.setKeyCode(Input.Keys.valueOf(getKeyText(e.getExtendedKeyCode())));
                            upButton.setText("Előre: " + getKeyText(e.getKeyCode()));
                            dialog.dispose();
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });
                }
        );

        downButton.addActionListener(
                e -> {
                    JDialog dialog = new JDialog();
                    dialog.add(new JLabel("Nyomja le a kívánt billentyűt!"));
                    dialog.setVisible(true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setSize(300,200);
                    dialog.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}

                        @Override
                        public void keyPressed(KeyEvent e) {
                            downButton.setKeyCode(Input.Keys.valueOf(getKeyText(e.getExtendedKeyCode())));
                            downButton.setText("Hátra: " + getKeyText(e.getKeyCode()));
                            dialog.dispose();
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });
                }
        );

        leftButton.addActionListener(
                e -> {
                    JDialog dialog = new JDialog();
                    dialog.add(new JLabel("Nyomja le a kívánt billentyűt!"));
                    dialog.setVisible(true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setSize(300,200);
                    dialog.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}

                        @Override
                        public void keyPressed(KeyEvent e) {
                            leftButton.setKeyCode(Input.Keys.valueOf(getKeyText(e.getExtendedKeyCode())));
                            leftButton.setText("Balra: " + getKeyText(e.getKeyCode()));
                            dialog.dispose();
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });
                }
        );

        rightButton.addActionListener(
                e -> {
                    JDialog dialog = new JDialog();
                    dialog.add(new JLabel("Nyomja le a kívánt billentyűt!"));
                    dialog.setVisible(true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setSize(300,200);
                    dialog.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}

                        @Override
                        public void keyPressed(KeyEvent e) {
                            rightButton.setKeyCode(Input.Keys.valueOf(getKeyText(e.getExtendedKeyCode())));
                            rightButton.setText("Jobbra: " + getKeyText(e.getKeyCode()));
                            dialog.dispose();
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });
                }
        );

        bombButton.addActionListener(
                e -> {
                    JDialog dialog = new JDialog();
                    dialog.add(new JLabel("Nyomja le a kívánt billentyűt!"));
                    dialog.setVisible(true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setSize(300,200);
                    dialog.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}

                        @Override
                        public void keyPressed(KeyEvent e) {
                            bombButton.setKeyCode(Input.Keys.valueOf(getKeyText(e.getExtendedKeyCode())));
                            bombButton.setText("Bomba: " + getKeyText(e.getKeyCode()));
                            dialog.dispose();
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });
                }
        );

        extraButton.addActionListener(
                e -> {
                    JDialog dialog = new JDialog();
                    dialog.add(new JLabel("Nyomja le a kívánt billentyűt!"));
                    dialog.setVisible(true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setSize(300,200);
                    dialog.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}

                        @Override
                        public void keyPressed(KeyEvent e) {
                            extraButton.setKeyCode(Input.Keys.valueOf(getKeyText(e.getExtendedKeyCode())));
                            extraButton.setText("Extra: " + getKeyText(e.getKeyCode()));
                            dialog.dispose();
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });
                }
        );
    }
    public PlayerControl getPlayerControls() {
        PlayerControl controls = new PlayerControl();
        controls.setUpButton(upButton.getKeyCode());
        controls.setDownButton(downButton.getKeyCode());
        controls.setLeftButton(leftButton.getKeyCode());
        controls.setRightButton(rightButton.getKeyCode());
        controls.setBombButton(bombButton.getKeyCode());
        controls.setExtraButton(extraButton.getKeyCode());
        return controls;
    }
}
